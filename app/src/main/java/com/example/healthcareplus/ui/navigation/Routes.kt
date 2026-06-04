package com.example.healthcareplus.ui.navigation

sealed class Routes(val route: String) {

    // ── Splash / Onboarding ───────────────────────────────────────────────
    object Splash : Routes("splash")
    object RoleSelection : Routes("role_selection")

    // ── Auth ──────────────────────────────────────────────────────────────
    object PatientLogin : Routes("patient_login")
    object PatientSignup : Routes("patient_signup")
    object ForgotPassword : Routes("forgot_password")
    object DoctorLogin : Routes("doctor_login")

    // ── Email Verification ────────────────────────────────────────────────
    object VerifyEmail : Routes("verify_email/{email}") {
        fun createRoute(email: String) = "verify_email/$email"
        const val ARG = "email"
    }

    // ── Graph roots ───────────────────────────────────────────────────────
    object PatientGraph : Routes("patient_graph")
    object DoctorGraph : Routes("doctor_graph")

    // ── Doctor screens ────────────────────────────────────────────────────
    object DoctorHome : Routes("doctor_home")
    object DoctorAppointments : Routes("doctor_appointments")
    object DoctorLabReports : Routes("doctor_lab_reports")

    object DoctorVideoCall : Routes("doctor_video_call/{patientName}") {
        fun createRoute(patientName: String) = "doctor_video_call/$patientName"
        const val ARG = "patientName"
    }

    object DoctorAppointmentDetail : Routes("doctor_appointment_detail/{appointmentId}") {
        fun createRoute(appointmentId: String) = "doctor_appointment_detail/$appointmentId"
        const val ARG = "appointmentId"
    }

    object DoctorMessages : Routes("doctor_messages")
    object DoctorPatientMessages : Routes("doctor_patient_messages")

    object DoctorChat : Routes("doctor_chat/{patientName}") {
        fun createRoute(patientName: String) = "doctor_chat/$patientName"
        const val ARG = "patientName"
    }

    object DoctorProfile : Routes("doctor_profile")
    object DoctorEditProfile : Routes("doctor_edit_profile")
    object DoctorSecuritySettings : Routes("doctor_security_settings")
    object DoctorLabReportRequests : Routes("doctor_lab_report_requests")
    object DoctorNotifications : Routes("doctor_notifications")

    // ── Patient screens ───────────────────────────────────────────────────
    object PatientHome : Routes("patient_home")

    object LabReports : Routes("lab_reports")

    object LabReportDetail : Routes("lab_report_detail/{reportId}") {
        fun createRoute(reportId: String) = "lab_report_detail/$reportId"
        const val ARG = "reportId"
    }

    object BookAppointment : Routes("book_appointment")
    object AppointmentConfirmed : Routes("appointment_confirmed")
    object MyAppointments : Routes("my_appointments")
    object AppointmentCancelled : Routes("appointment_cancelled")

    object Messages : Routes("messages_list")

    object Chat : Routes("chat/{doctorName}") {
        fun createRoute(doctorName: String) = "chat/$doctorName"
        const val ARG = "doctorName"
    }

    object Notifications : Routes("notifications")
    object Profile : Routes("profile")
    object EditProfile : Routes("edit_profile")
    object SecuritySettings : Routes("security_settings")
    object HelpSupport : Routes("help_faq")

    // ── Support ───────────────────────────────────────────────────────────
    object ContactSupport : Routes("contact_support")
}