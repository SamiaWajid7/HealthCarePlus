package com.example.healthcareplus.data.model

data class UserProfile(
    val uid         : String = "",
    val name        : String = "",
    val email       : String = "",
    val role        : String = "",
    val phone       : String = "",
    val dateOfBirth : String = "",
    val bloodGroup  : String = "",
    val medicalId   : String = "",
    val specialty   : String = "",
)