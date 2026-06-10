package com.example.healthcareplus.data.model

data class Appointment(
    val id          : String = "",
    val patientId   : String = "",
    val doctorId    : String = "",
    val patientName : String = "",
    val doctorName  : String = "",
    val specialty   : String = "",
    val reason      : String = "",
    val date        : String = "",
    val time        : String = "",
    val status      : String = "Pending",  // Pending | Upcoming | Completed | Cancelled
)