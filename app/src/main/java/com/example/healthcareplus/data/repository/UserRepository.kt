package com.example.healthcareplus.data.repository

import com.example.healthcareplus.data.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth      : FirebaseAuth      = FirebaseAuth.getInstance(),
    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance(),
) {
    suspend fun getCurrentUserProfile(): Result<UserProfile> {
        val uid = auth.currentUser?.uid
            ?: return Result.failure(Exception("Not logged in"))
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            Result.success(
                UserProfile(
                    uid         = uid,
                    name        = doc.getString("name")        ?: "",
                    email       = doc.getString("email")       ?: "",
                    role        = doc.getString("role")        ?: "",
                    phone       = doc.getString("phone")       ?: "",
                    dateOfBirth = doc.getString("dateOfBirth") ?: "",
                    bloodGroup  = doc.getString("bloodGroup")  ?: "",
                    medicalId   = doc.getString("medicalId")   ?: "",
                    specialty   = doc.getString("specialty")   ?: "",
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfile(
        name        : String,
        phone       : String,
        dateOfBirth : String,
        bloodGroup  : String,
    ): Result<Unit> {
        val uid = auth.currentUser?.uid
            ?: return Result.failure(Exception("Not logged in"))
        return try {
            firestore.collection("users").document(uid)
                .update(
                    mapOf(
                        "name"        to name.trim(),
                        "phone"       to phone.trim(),
                        "dateOfBirth" to dateOfBirth.trim(),
                        "bloodGroup"  to bloodGroup.trim(),
                    )
                ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}