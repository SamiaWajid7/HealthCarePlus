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
import com.example.healthcareplus.ui.screens.doctor.DoctorLabReportsScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorMessagesScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorPatientMessagesScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorSecuritySettingsScreen
import com.example.healthcareplus.ui.screens.doctor.DoctorVideoCallScreen
import com.example.healthcareplus.ui.screens.doctor.EditProfileScreen
import com.example.healthcareplus.ui.screens.doctor.LabReportRequestsScreen
import com.example.healthcareplus.ui.screens.doctor.ProfileContentContainer
import com.example.healthcareplus.ui.screens.patient.NotificationsScreen

fun NavGraphBuilder.doctorGraph(navController: NavHostController) {

    navigation(
        startDestination = Routes.DoctorHome.route,
        route            = Routes.DoctorGraph.route,
    ) {

        // ── Home ─────────────────────────────────────────────────────────────
        composable(Routes.DoctorHome.route) {
            DoctorHomeScreen(
                onNotifications = {
                    navController.navigate(Routes.DoctorNotifications.route)
                },
                onAppointments = {
                    navController.navigate(Routes.DoctorAppointments.route)
                },
                onLabReports = {
                    navController.navigate(Routes.DoctorLabReports.route)
                },
                onMessages = {
                    navController.navigate(Routes.DoctorMessages.route)
                },
                onProfile = {
                    navController.navigate(Routes.DoctorProfile.route)
                },
                onJoinCall = {
                    navController.navigate(
                        Routes.DoctorVideoCall.createRoute("John Doe")
                    )
                },
            )
        }

        // ── Notifications ─────────────────────────────────────────────────────
        composable(Routes.DoctorNotifications.route) {
            NotificationsScreen(navController = navController)
        }

        // ── Appointments ─────────────────────────────────────────────────────
        composable(Routes.DoctorAppointments.route) {
            DoctorAppointmentsScreen(
                onBack = { navController.popBackStack() },
                onViewDetails = { appointment ->
                    navController.navigate(
                        Routes.DoctorAppointmentDetail.createRoute(appointment.id)
                    )
                },
            )
        }

        // ── Appointment Detail ────────────────────────────────────────────────
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

        // ── Video Call ────────────────────────────────────────────────────────
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
            )
        }

        // ── Messages ──────────────────────────────────────────────────────────
        composable(Routes.DoctorMessages.route) {
            DoctorMessagesScreen(
                onOpenChat = { thread ->
                    navController.navigate(
                        Routes.DoctorChat.createRoute(thread.patientName)
                    )
                },
                onHome    = { navController.navigate(Routes.DoctorHome.route) },
                onReports = { navController.navigate(Routes.DoctorLabReports.route) },
                onProfile = { navController.navigate(Routes.DoctorProfile.route) },
            )
        }

        // ── Patient Messages ──────────────────────────────────────────────────
        composable(Routes.DoctorPatientMessages.route) {
            DoctorPatientMessagesScreen(
                onBack = { navController.popBackStack() },
                onOpenChat = { thread ->
                    navController.navigate(
                        Routes.DoctorChat.createRoute(thread.patientName)
                    )
                },
            )
        }

        // ── Chat ──────────────────────────────────────────────────────────────
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

        // ── Profile ───────────────────────────────────────────────────────────
        composable(Routes.DoctorProfile.route) {
            ProfileContentContainer(navController = navController)
        }

        // ── Edit Profile ──────────────────────────────────────────────────────
        composable(Routes.DoctorEditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        // ── Security Settings ─────────────────────────────────────────────────
        composable(Routes.DoctorSecuritySettings.route) {
            DoctorSecuritySettingsScreen(navController = navController)
        }

        // ── Lab Reports List ──────────────────────────────────────────────────
        composable(Routes.DoctorLabReports.route) {
            DoctorLabReportsScreen(
                onBack         = { navController.popBackStack() },
                onUploadReport = { _ ->
                    navController.navigate(Routes.DoctorLabReportRequests.route)
                },
                onViewReport   = { _ ->
                    navController.popBackStack()
                },
            )
        }

        // ── Lab Report Upload Form ─────────────────────────────────────────────
        composable(Routes.DoctorLabReportRequests.route) {
            LabReportRequestsScreen(
                onBack         = { navController.popBackStack() },
                onUploadReport = { navController.popBackStack() },
                onViewReport   = { navController.navigate(Routes.DoctorLabReports.route) },
            )
        }
    }
}