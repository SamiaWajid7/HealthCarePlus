package com.example.healthcareplus.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
) {

    suspend fun loginPatient(email: String, password: String): Result<String> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email.trim(), password).await()
            val uid = authResult.user?.uid
                ?: return Result.failure(Exception("Authentication failed"))

            val userDoc = firestore.collection(USERS_COLLECTION).document(uid).get().await()
            if (userDoc.getString(FIELD_ROLE) != ROLE_PATIENT) {
                auth.signOut()
                return Result.failure(Exception("This account is not registered as a patient"))
            }

            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginDoctor(medicalId: String, password: String): Result<String> {
        return try {
            val trimmedMedicalId = medicalId.trim()

            val snapshot = firestore.collection(USERS_COLLECTION)
                .whereEqualTo(FIELD_MEDICAL_ID, trimmedMedicalId)
                .whereEqualTo(FIELD_ROLE, ROLE_DOCTOR)
                .limit(1)
                .get()
                .await()

            if (snapshot.isEmpty) {
                return Result.failure(Exception("Doctor not found"))
            }

            val doctorDoc = snapshot.documents.first()
            val uid = doctorDoc.id
            val authEmail = resolveDoctorAuthEmail(trimmedMedicalId, doctorDoc)
                ?: return Result.failure(Exception("Unable to resolve doctor login credentials"))

            val authResult = auth.signInWithEmailAndPassword(authEmail, password).await()
            val signedInUid = authResult.user?.uid
                ?: return Result.failure(Exception("Authentication failed"))

            if (signedInUid != uid) {
                auth.signOut()
                return Result.failure(Exception("Account mismatch"))
            }

            Result.success(signedInUid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUpPatient(name: String, email: String, password: String): Result<String> {
        return try {
            val trimmedEmail = email.trim()
            val authResult = auth.createUserWithEmailAndPassword(trimmedEmail, password).await()
            val uid = authResult.user?.uid
                ?: return Result.failure(Exception("Sign up failed"))

            val userData = hashMapOf(
                FIELD_NAME to name.trim(),
                FIELD_EMAIL to trimmedEmail,
                FIELD_ROLE to ROLE_PATIENT,
            )

            firestore.collection(USERS_COLLECTION)
                .document(uid)
                .set(userData)
                .await()

            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun isLoggedIn(): Boolean = auth.currentUser != null

    suspend fun getCurrentUserRole(): String? {
        val uid = getCurrentUserId() ?: return null
        return try {
            firestore.collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .await()
                .getString(FIELD_ROLE)
        } catch (_: Exception) {
            null
        }
    }

    private fun resolveDoctorAuthEmail(medicalId: String, doctorDoc: DocumentSnapshot): String? {
        doctorDoc.getString(FIELD_EMAIL)?.takeIf { it.isNotBlank() }?.let { return it }
        if (medicalId.contains("@")) return medicalId
        return null
    }

    private companion object {
        const val USERS_COLLECTION = "users"
        const val FIELD_NAME = "name"
        const val FIELD_EMAIL = "email"
        const val FIELD_MEDICAL_ID = "medicalId"
        const val FIELD_ROLE = "role"
        const val ROLE_PATIENT = "patient"
        const val ROLE_DOCTOR = "doctor"
    }
}
