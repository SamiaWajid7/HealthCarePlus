package com.example.healthcareplus.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.healthcareplus.ui.screens.patient.AppointmentConfirmedScreen
import com.example.healthcareplus.ui.screens.patient.BookAppointmentScreen
import com.example.healthcareplus.ui.screens.patient.ContactSupportScreen
import com.example.healthcareplus.ui.screens.patient.EditProfileScreen
import com.example.healthcareplus.ui.screens.patient.HelpFaqScreen
import com.example.healthcareplus.ui.screens.patient.LabReportDetailScreen
import com.example.healthcareplus.ui.screens.patient.LabReportsScreen
import com.example.healthcareplus.ui.screens.patient.MessagesListScreen
import com.example.healthcareplus.ui.screens.patient.MyAppointmentsScreen
import com.example.healthcareplus.ui.screens.patient.NotificationsScreen
import com.example.healthcareplus.ui.screens.patient.PatientHomeScreen
import com.example.healthcareplus.ui.screens.patient.ProfileScreen
import com.example.healthcareplus.ui.screens.patient.SecuritySettingsScreen

fun NavGraphBuilder.patientGraph(navController: NavHostController) {

    navigation(
        startDestination = Routes.PatientHome.route,
        route            = Routes.PatientGraph.route,
    ) {

        // ── Home ──────────────────────────────────────────────────────────
        composable(Routes.PatientHome.route) {
            PatientHomeScreen(
                onNotifications = { navController.navigate(Routes.Notifications.route) },
                onLabReports    = { navController.navigate(Routes.LabReports.route) },
                onBookVisit     = { navController.navigate(Routes.BookAppointment.route) },
                onChat          = { navController.navigate(Routes.Messages.route) },
                onProfile       = { navController.navigate(Routes.Profile.route) },
            )
        }

        // ── Lab Reports ───────────────────────────────────────────────────
        composable(Routes.LabReports.route) {
            LabReportsScreen(navController = navController)
        }

        composable(
            route     = Routes.LabReportDetail.route,
            arguments = listOf(navArgument(Routes.LabReportDetail.ARG) {
                type     = NavType.StringType
                nullable = true
            })
        ) { entry ->
            LabReportDetailScreen(
                navController = navController,
                reportId      = entry.arguments?.getString(Routes.LabReportDetail.ARG),
            )
        }

        // ── Appointments ──────────────────────────────────────────────────
        composable(Routes.BookAppointment.route) {
            BookAppointmentScreen(navController = navController)
        }

        composable(Routes.AppointmentConfirmed.route) {
            AppointmentConfirmedScreen(navController = navController)
        }

        composable(Routes.MyAppointments.route) {
            MyAppointmentsScreen(navController = navController)
        }

        composable(Routes.AppointmentCancelled.route) {
            AppointmentConfirmedScreen(navController = navController)
        }

        // ── Messages & Chat ───────────────────────────────────────────────
        composable(Routes.Messages.route) {
            MessagesListScreen(navController = navController)
        }

        composable(
            route     = Routes.Chat.route,
            arguments = listOf(navArgument(Routes.Chat.ARG) {
                type     = NavType.StringType
                nullable = true
            })
        ) {
            MessagesListScreen(navController = navController)
        }

        // ── Notifications ─────────────────────────────────────────────────
        composable(Routes.Notifications.route) {
            NotificationsScreen(navController = navController)
        }

        // ── Profile ───────────────────────────────────────────────────────
        composable(Routes.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(Routes.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        composable(Routes.SecuritySettings.route) {
            SecuritySettingsScreen(navController = navController)
        }

        // ── Help & Support ────────────────────────────────────────────────
        composable(Routes.HelpSupport.route) {
            HelpFaqScreen(navController = navController)
        }

        // ── Contact Support ───────────────────────────────────────────────
        composable(Routes.ContactSupport.route) {
            ContactSupportScreen(navController = navController)
        }
    }
}