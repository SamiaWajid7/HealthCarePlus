package com.example.healthcareplus.data.repository

import com.example.healthcareplus.data.model.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AppointmentRepository(
    private val auth      : FirebaseAuth      = FirebaseAuth.getInstance(),
    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance(),
) {
    private val col = firestore.collection("appointments")

    // ── Patient books a new appointment ──────────────────────────────────
    suspend fun bookAppointment(
        doctorId    : String,
        doctorName  : String,
        specialty   : String,
        reason      : String,
        date        : String,
        time        : String,
    ): Result<Unit> {
        val uid = auth.currentUser?.uid
            ?: return Result.failure(Exception("Not logged in"))
        val patientName = auth.currentUser?.displayName ?: ""
        return try {
            val doc = col.document()
            doc.set(
                mapOf(
                    "id"          to doc.id,
                    "patientId"   to uid,
                    "doctorId"    to doctorId,
                    "patientName" to patientName,
                    "doctorName"  to doctorName,
                    "specialty"   to specialty,
                    "reason"      to reason,
                    "date"        to date,
                    "time"        to time,
                    "status"      to "Pending",
                )
            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Patient: real-time list of my appointments ────────────────────────
    fun getPatientAppointments(): Flow<List<Appointment>> = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }
        val listener = col
            .whereEqualTo("patientId", uid)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, _ ->
                val list = snap?.documents?.map { d ->
                    Appointment(
                        id          = d.getString("id")          ?: "",
                        patientId   = d.getString("patientId")   ?: "",
                        doctorId    = d.getString("doctorId")    ?: "",
                        patientName = d.getString("patientName") ?: "",
                        doctorName  = d.getString("doctorName")  ?: "",
                        specialty   = d.getString("specialty")   ?: "",
                        reason      = d.getString("reason")      ?: "",
                        date        = d.getString("date")        ?: "",
                        time        = d.getString("time")        ?: "",
                        status      = d.getString("status")      ?: "Pending",
                    )
                } ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    // ── Doctor: real-time list of all appointments ────────────────────────
    fun getDoctorAppointments(): Flow<List<Appointment>> = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }
        val listener = col
            .whereEqualTo("doctorId", uid)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, _ ->
                val list = snap?.documents?.map { d ->
                    Appointment(
                        id          = d.getString("id")          ?: "",
                        patientId   = d.getString("patientId")   ?: "",
                        doctorId    = d.getString("doctorId")    ?: "",
                        patientName = d.getString("patientName") ?: "",
                        doctorName  = d.getString("doctorName")  ?: "",
                        specialty   = d.getString("specialty")   ?: "",
                        reason      = d.getString("reason")      ?: "",
                        date        = d.getString("date")        ?: "",
                        time        = d.getString("time")        ?: "",
                        status      = d.getString("status")      ?: "Pending",
                    )
                } ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    // ── Doctor: update appointment status ─────────────────────────────────
    suspend fun updateStatus(
        appointmentId : String,
        status        : String,
    ): Result<Unit> {
        return try {
            col.document(appointmentId)
                .update("status", status)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Single appointment by ID ──────────────────────────────────────────
    suspend fun getAppointmentById(appointmentId: String): Result<Appointment> {
        return try {
            val d = col.document(appointmentId).get().await()
            Result.success(
                Appointment(
                    id          = d.getString("id")          ?: "",
                    patientId   = d.getString("patientId")   ?: "",
                    doctorId    = d.getString("doctorId")    ?: "",
                    patientName = d.getString("patientName") ?: "",
                    doctorName  = d.getString("doctorName")  ?: "",
                    specialty   = d.getString("specialty")   ?: "",
                    reason      = d.getString("reason")      ?: "",
                    date        = d.getString("date")        ?: "",
                    time        = d.getString("time")        ?: "",
                    status      = d.getString("status")      ?: "Pending",
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}