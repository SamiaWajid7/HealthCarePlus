package com.example.healthcareplus.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.healthcareplus.ui.screens.patient.AppointmentConfirmedScreen
import com.example.healthcareplus.ui.screens.patient.BookAppointmentScreen
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
        // BookAppointmentScreen navigates to "appointment_confirmed" internally
        composable(Routes.BookAppointment.route) {
            BookAppointmentScreen(navController = navController)
        }

        // AppointmentConfirmedScreen navigates to "patient_home" and
        // "my_appointments" internally
        composable(Routes.AppointmentConfirmed.route) {
            AppointmentConfirmedScreen(navController = navController)
        }

        // MyAppointmentsScreen hosts CancelAppointmentDialog internally.
        // Dialog onConfirm navigates to "appointment_cancelled".
        // Message button navigates to "chat/{doctorName}".
        composable(Routes.MyAppointments.route) {
            MyAppointmentsScreen(navController = navController)
        }

        // Reuse AppointmentConfirmedScreen for cancelled state,
        // or swap with your own AppointmentCancelledScreen when ready.
        composable(Routes.AppointmentCancelled.route) {
            AppointmentConfirmedScreen(navController = navController)
        }

        // ── Messages & Chat ───────────────────────────────────────────────
        composable(Routes.Messages.route) {
            MessagesListScreen(navController = navController)
        }

        // Navigated to as "chat/{doctorName}" from MyAppointmentsScreen
        composable(
            route     = Routes.Chat.route,
            arguments = listOf(navArgument(Routes.Chat.ARG) {
                type     = NavType.StringType
                nullable = true
            })
        ) { entry ->
            // Replace ChatScreen with your actual composable name if different
            // ChatScreen(
            //     navController = navController,
            //     doctorName    = entry.arguments?.getString(Routes.Chat.ARG) ?: "",
            // )
            // Placeholder until ChatScreen is created:
            MessagesListScreen(navController = navController)
        }

        // ── Notifications ─────────────────────────────────────────────────
        composable(Routes.Notifications.route) {
            NotificationsScreen(navController = navController)
        }

        // ── Profile ───────────────────────────────────────────────────────
        // ProfileScreen navigates to "edit_profile" and "security_settings" internally
        composable(Routes.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // EditProfileScreen shows ChangesSavedDialog internally
        composable(Routes.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        // SecuritySettingsScreen shows LogoutDialog internally.
        // LogoutDialog onConfirm should call:
        //   navController.navigate(Routes.PatientLogin.route) { popUpTo(0) { inclusive = true } }
        composable(Routes.SecuritySettings.route) {
            SecuritySettingsScreen(navController = navController)
        }

        composable(Routes.HelpSupport.route) {
            HelpFaqScreen(navController = navController)
        }
    }
}