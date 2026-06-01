package com.example.healthcareplus.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.healthcareplus.ui.screens.auth.DoctorLoginScreen
import com.example.healthcareplus.ui.screens.auth.ForgotPasswordScreen
import com.example.healthcareplus.ui.screens.auth.PatientLoginScreen
import com.example.healthcareplus.ui.screens.auth.PatientSignUpScreen
import com.example.healthcareplus.ui.screens.auth.RoleSelectionScreen
import com.example.healthcareplus.ui.screens.auth.UserRole
import com.example.healthcareplus.ui.screens.splash.SplashScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {

    composable(Routes.Splash.route) {
        SplashScreen(
            onSplashFinished = {
                navController.navigate(Routes.RoleSelection.route) {
                    popUpTo(Routes.Splash.route) { inclusive = true }
                }
            }
        )
    }

    composable(Routes.RoleSelection.route) {
        RoleSelectionScreen(
            onRoleSelected = { role ->
                navController.navigate(
                    if (role == UserRole.PATIENT) Routes.PatientLogin.route
                    else Routes.DoctorLogin.route
                )
            }
        )
    }

    composable(Routes.PatientLogin.route) {
        PatientLoginScreen(
            onLoginSuccess = {
                navController.navigate(Routes.PatientGraph.route) {
                    popUpTo(Routes.PatientLogin.route) { inclusive = true }
                }
            },
            onSignUpClick    = { navController.navigate(Routes.PatientSignup.route) },
            onForgotPassword = { navController.navigate(Routes.ForgotPassword.route) }
        )
    }

    composable(Routes.PatientSignup.route) {
        PatientSignUpScreen(
            onSignUpSuccess = {
                navController.navigate(Routes.PatientGraph.route) {
                    popUpTo(Routes.RoleSelection.route) { inclusive = false }
                }
            },
            onLoginClick = { navController.popBackStack() }
        )
    }

    composable(Routes.ForgotPassword.route) {
        ForgotPasswordScreen(
            onResetSent   = { navController.popBackStack() },
            onBackToLogin = { navController.popBackStack() }
        )
    }

    composable(Routes.DoctorLogin.route) {
        DoctorLoginScreen(
            onLoginSuccess = {
                navController.navigate(Routes.DoctorGraph.route) {
                    popUpTo(Routes.DoctorLogin.route) { inclusive = true }
                }
            },
            onForgotPassword = { navController.navigate(Routes.ForgotPassword.route) }
        )
    }
}