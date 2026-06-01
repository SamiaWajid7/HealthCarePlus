package com.example.healthcareplus.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ─── Font Family ─────────────────────────────────────────────────────────────
// The design uses the system default sans-serif (similar to Inter / Roboto).
// Replace with a custom font by adding .ttf files to res/font/ and
// updating the Font() references below.
//
// Example with a custom font:
//   val Inter = FontFamily(
//       Font(R.font.inter_regular, FontWeight.Normal),
//       Font(R.font.inter_medium,  FontWeight.Medium),
//       Font(R.font.inter_semibold, FontWeight.SemiBold),
//       Font(R.font.inter_bold,    FontWeight.Bold),
//   )
val AppFontFamily = FontFamily.Default   // swap with Inter (or your font) above

// ─── Typography Scale ─────────────────────────────────────────────────────────
// Derived directly from the Figma screens:
//
//  displayLarge   –  not used in current design
//  displayMedium  –  not used
//  displaySmall   –  not used
//  headlineLarge  –  "Welcome to HealthCare+" hero title              28sp Bold
//  headlineMedium –  Section headings: "Lab Reports", "My Appointments" 24sp SemiBold
//  headlineSmall  –  Card/page headings: "Complete Blood Count"         20sp SemiBold
//  titleLarge     –  Doctor name in profile header "John Doe"           20sp Medium
//  titleMedium    –  Card row titles, quick action labels               16sp Medium
//  titleSmall     –  Badge text, tab labels                             14sp Medium
//  bodyLarge      –  Primary body text, message bubbles                 16sp Normal
//  bodyMedium     –  Secondary info, descriptions                       14sp Normal
//  bodySmall      –  Tertiary text, timestamps, hints                   12sp Normal
//  labelLarge     –  Button text                                        16sp SemiBold
//  labelMedium    –  Input field labels, nav labels                     14sp Medium
//  labelSmall     –  Micro labels, ref ranges, caption text             11sp Normal

val HealthCarePlusTypography = Typography(

    // ── Headlines ──────────────────────────────────────────────────────────

    headlineLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize   = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.5).sp,
        // Used for: "Welcome to HealthCare+" onboarding hero
    ),

    headlineMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.25).sp,
        // Used for: "Lab Reports", "My Appointments", "Messages" screen titles
    ),

    headlineSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        // Used for: "Complete Blood Count", "Book Appointment", card headings
    ),

    // ── Titles ────────────────────────────────────────────────────────────

    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        // Used for: User name in home header "John Doe"
    ),

    titleMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
        // Used for: "Dr. Sarah Johnson" in list rows, quick action labels
    ),

    titleSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        // Used for: Specialty tags ("Cardiologist"), nav bar labels
    ),

    // ── Body ──────────────────────────────────────────────────────────────

    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        // Used for: Message bubble content, form input text
    ),

    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        // Used for: Card subtitles, appointment reasons, notification body
    ),

    bodySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        // Used for: Timestamps ("10:30 AM"), date labels, "2 hours ago"
    ),

    // ── Labels ────────────────────────────────────────────────────────────

    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp,
        // Used for: Primary CTA buttons "Sign In", "Book Appointment", "Sign Up"
    ),

    labelMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp,
        // Used for: Form field labels "Email", "Full Name"; section headers "TODAY"
    ),

    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.2.sp,
        // Used for: Reference range labels "Ref: 13.5-17.5", micro captions
    ),
)