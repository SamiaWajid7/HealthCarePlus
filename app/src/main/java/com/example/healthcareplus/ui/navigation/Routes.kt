package com.example.healthcareplus.ui.navigation

sealed class Routes(val route: String) {

    // ── Splash / Onboarding ───────────────────────────────────────────────
    object Splash         : Routes("splash")
    object RoleSelection  : Routes("role_selection")

    // ── Auth ──────────────────────────────────────────────────────────────
    object PatientLogin   : Routes("patient_login")
    object PatientSignup  : Routes("patient_signup")
    object ForgotPassword : Routes("forgot_password")
    object DoctorLogin    : Routes("doctor_login")

    // ── Graph roots ───────────────────────────────────────────────────────
    object PatientGraph   : Routes("patient_graph")
    object DoctorGraph    : Routes("doctor_graph")

    // ── Doctor screens ────────────────────────────────────────────────────
    object DoctorHome     : Routes("doctor_home")

    // ── Patient screens ───────────────────────────────────────────────────
    object PatientHome    : Routes("patient_home")
    object LabReports     : Routes("lab_reports")

    object LabReportDetail : Routes("lab_report_detail/{reportId}") {
        fun createRoute(reportId: String) = "lab_report_detail/$reportId"
        const val ARG = "reportId"
    }

    object BookAppointment     : Routes("book_appointment")
    object AppointmentConfirmed: Routes("appointment_confirmed")
    object MyAppointments      : Routes("my_appointments")
    object AppointmentCancelled: Routes("appointment_cancelled")

    object Messages       : Routes("messages_list")

    object Chat : Routes("chat/{doctorName}") {
        fun createRoute(doctorName: String) = "chat/$doctorName"
        const val ARG = "doctorName"
    }

    object Notifications    : Routes("notifications")
    object Profile          : Routes("profile")
    object EditProfile      : Routes("edit_profile")
    object SecuritySettings : Routes("security_settings")
    object HelpSupport      : Routes("help_faq")
}