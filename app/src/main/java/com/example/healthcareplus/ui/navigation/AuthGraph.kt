package com.example.healthcareplus.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.healthcareplus.ui.screens.auth.DoctorLoginScreen
import com.example.healthcareplus.ui.screens.auth.ForgotPasswordScreen
import com.example.healthcareplus.ui.screens.auth.PatientLoginScreen
import com.example.healthcareplus.ui.screens.auth.PatientSignUpScreen
import com.example.healthcareplus.ui.screens.auth.RoleSelectionScreen
import com.example.healthcareplus.ui.screens.auth.UserRole
import com.example.healthcareplus.ui.screens.patient.VerifyEmailScreen
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
                // Navigate to OTP verification after signup
                navController.navigate(
                    Routes.VerifyEmail.createRoute("john.doe@email.com")
                ) {
                    popUpTo(Routes.PatientSignup.route) { inclusive = false }
                }
            },
            onLoginClick = { navController.popBackStack() }
        )
    }

    // ── OTP / Email Verification ──────────────────────────────────────────
    composable(
        route     = Routes.VerifyEmail.route,
        arguments = listOf(navArgument(Routes.VerifyEmail.ARG) {
            type     = NavType.StringType
            nullable = true
        }),
    ) { entry ->
        VerifyEmailScreen(
            navController = navController,
            email         = entry.arguments?.getString(Routes.VerifyEmail.ARG) ?: "your email",
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