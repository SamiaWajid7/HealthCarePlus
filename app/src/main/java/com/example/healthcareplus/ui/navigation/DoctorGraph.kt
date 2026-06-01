package com.example.healthcareplus.ui.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.healthcareplus.ui.screens.doctor.DoctorHomeScreen

fun NavGraphBuilder.doctorGraph(navController: NavHostController) {

    navigation(
        startDestination = Routes.DoctorHome.route,
        route            = Routes.DoctorGraph.route,
    ) {
        composable(Routes.DoctorHome.route) {
            DoctorHomeScreen(
                onNotifications = {},
                onAppointments  = {},
                onLabReports    = {},
                onMessages      = {},
                onProfile       = {},
                onJoinCall      = {},
            )
        }
    }
}