package com.example.healthcareplus.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.healthcareplus.ui.screens.doctor.AppointmentDetailScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorAppointmentsScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorChatScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorHomeScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorMessagesScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorPatientMessagesScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorVideoCallScreen
import com.example.healthcareplus.ui.screens.patient.EditProfileScreen
import com.example.healthcareplus.ui.screens.patient.ProfileScreen

fun NavGraphBuilder.doctorGraph(navController: NavHostController) {

    navigation(
        startDestination = Routes.DoctorHome.route,
        route            = Routes.DoctorGraph.route,
    ) {

        // ── Home ──────────────────────────────────────────────────────────
        composable(Routes.DoctorHome.route) {
            DoctorHomeScreen(
                onNotifications = {},
                onAppointments  = { navController.navigate(Routes.DoctorAppointments.route) },
                onLabReports    = {},
                onMessages      = { navController.navigate(Routes.DoctorMessages.route) },
                onProfile       = { navController.navigate(Routes.DoctorProfile.route) },
                onJoinCall      = {
                    navController.navigate(Routes.DoctorVideoCall.createRoute("John Doe"))
                },
            )
        }

        // ── Appointments list ─────────────────────────────────────────────
        composable(Routes.DoctorAppointments.route) {
            DoctorAppointmentsScreen(
                onBack        = { navController.popBackStack() },
                onViewDetails = { appointment ->
                    navController.navigate(
                        Routes.DoctorAppointmentDetail.createRoute(appointment.id)
                    )
                },
            )
        }

        // ── Appointment detail ────────────────────────────────────────────
        composable(
            route     = Routes.DoctorAppointmentDetail.route,
            arguments = listOf(navArgument(Routes.DoctorAppointmentDetail.ARG) {
                type     = NavType.StringType
                nullable = true
            }),
        ) {
            AppointmentDetailScreen(
                onBack    = { navController.popBackStack() },
                onApprove = { navController.popBackStack() },
                onCancel  = { navController.popBackStack() },
            )
        }

        // ── Video call ────────────────────────────────────────────────────
        composable(
            route     = Routes.DoctorVideoCall.route,
            arguments = listOf(navArgument(Routes.DoctorVideoCall.ARG) {
                type     = NavType.StringType
                nullable = true
            }),
        ) { entry ->
            DoctorVideoCallScreen(
                patientName = entry.arguments?.getString(Routes.DoctorVideoCall.ARG) ?: "Patient",
                onEndCall   = { navController.popBackStack() },
                onChat      = { navController.navigate(Routes.DoctorMessages.route) },
                onNotes     = {},
            )
        }

        // ── Messages (bottom-nav Chat tab) ────────────────────────────────
        composable(Routes.DoctorMessages.route) {
            DoctorMessagesScreen(
                onOpenChat = { thread ->
                    navController.navigate(Routes.DoctorChat.createRoute(thread.patientName))
                },
                onHome    = { navController.navigate(Routes.DoctorHome.route) },
                onReports = {},
                onProfile = { navController.navigate(Routes.DoctorProfile.route) },
            )
        }

        // ── Patient Messages inbox (All / Unread / Urgent) ────────────────
        composable(Routes.DoctorPatientMessages.route) {
            DoctorPatientMessagesScreen(
                onBack     = { navController.popBackStack() },
                onOpenChat = { thread ->
                    navController.navigate(Routes.DoctorChat.createRoute(thread.patientName))
                },
            )
        }

        // ── Individual chat with a patient ────────────────────────────────
        composable(
            route     = Routes.DoctorChat.route,
            arguments = listOf(navArgument(Routes.DoctorChat.ARG) {
                type     = NavType.StringType
                nullable = true
            }),
        ) { entry ->
            DoctorChatScreen(
                patientName = entry.arguments?.getString(Routes.DoctorChat.ARG) ?: "Patient",
                onBack      = { navController.popBackStack() },
            )
        }

        // ── Doctor Profile ────────────────────────────────────────────────
        composable(Routes.DoctorProfile.route) {
            ProfileScreen(navController = navController)
        }

        // ── Doctor Edit Profile ───────────────────────────────────────────
        // ChangesSavedDialog shows internally inside EditProfileScreen
        // onDismiss → navController.popBackStack() → back to DoctorProfile
        composable(Routes.DoctorEditProfile.route) {
            EditProfileScreen(navController = navController)
        }

    }
}