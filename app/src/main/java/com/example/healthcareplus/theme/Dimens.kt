package com.example.healthcareplus.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// ─── Spacing / Padding ────────────────────────────────────────────────────────
object Dimens {

    // Base grid unit
    val SpacingXXS  = 2.dp    // Micro gaps (badge inner padding)
    val SpacingXS   = 4.dp    // Tight gaps (icon-to-label, badge padding)
    val SpacingS    = 8.dp    // Small gaps (between icon and text, row spacing)
    val SpacingM    = 12.dp   // Medium gaps (between card elements)
    val SpacingL    = 16.dp   // Standard padding (card horizontal padding, screen edges)
    val SpacingXL   = 20.dp   // Loose padding (section spacing)
    val SpacingXXL  = 24.dp   // Large section gaps
    val SpacingXXXL = 32.dp   // Hero/header bottom padding

    // ── Screen / Container ────────────────────────────────────────────────
    val ScreenPaddingHorizontal = 16.dp   // Left/right screen margin
    val ScreenPaddingVertical   = 16.dp   // Top/bottom screen margin
    val ContentMaxWidth         = 480.dp  // Max content width for single-column layouts

    // ── Cards ─────────────────────────────────────────────────────────────
    val CardPaddingHorizontal   = 16.dp
    val CardPaddingVertical     = 16.dp
    val CardCornerRadius        = 16.dp   // All cards use 16dp rounded corners
    val CardElevation           = 0.dp    // Flat cards with border, no shadow
    val CardBorderWidth         = 1.dp

    // ── Header / Banner ───────────────────────────────────────────────────
    val HeaderPaddingHorizontal = 20.dp
    val HeaderPaddingVertical   = 24.dp
    val HeaderCornerRadius      = 20.dp   // Bottom corners of header banner

    // ── Buttons ───────────────────────────────────────────────────────────
    val ButtonHeight            = 52.dp   // All primary CTA buttons
    val ButtonCornerRadius      = 12.dp   // Rounded buttons
    val ButtonPaddingHorizontal = 24.dp
    val ButtonBorderWidth       = 1.5.dp  // Outline buttons

    // ── Input Fields ──────────────────────────────────────────────────────
    val InputHeight             = 52.dp   // Standard text field height
    val InputCornerRadius       = 12.dp
    val InputPaddingHorizontal  = 16.dp
    val InputPaddingVertical    = 14.dp
    val InputIconSize           = 20.dp
    val InputBorderWidth        = 1.dp

    // ── Icons ─────────────────────────────────────────────────────────────
    val IconSizeXS              = 16.dp   // Inline micro icons
    val IconSizeS               = 20.dp   // Row/list icons (calendar, clock)
    val IconSizeM               = 24.dp   // Standard icons
    val IconSizeL               = 32.dp   // Quick action grid icons
    val IconSizeXL              = 48.dp   // Feature/hero icons (onboarding)

    // ── Icon Containers (circular bg behind icon) ──────────────────────────
    val IconContainerSizeS      = 36.dp   // Small icon background circle
    val IconContainerSizeM      = 48.dp   // Medium (quick actions)
    val IconContainerSizeL      = 64.dp   // Large (onboarding role selection)
    val IconContainerSizeXL     = 80.dp   // Hero icon (appointment confirmed check)

    // ── Avatar / Profile Image ────────────────────────────────────────────
    val AvatarSizeS             = 36.dp   // List row avatar
    val AvatarSizeM             = 48.dp   // Appointment card avatar
    val AvatarSizeL             = 64.dp   // Profile header avatar
    val AvatarSizeXL            = 80.dp   // Large profile banner avatar

    // ── Bottom Navigation Bar ──────────────────────────────────────────────
    val BottomNavHeight         = 64.dp
    val BottomNavIconSize       = 24.dp
    val BottomNavLabelSize      = 12.dp   // (as sp in Typography, kept here for reference)

    // ── List / Row Items ──────────────────────────────────────────────────
    val ListItemPaddingVertical   = 14.dp  // Lab report rows, message list rows
    val ListItemPaddingHorizontal = 16.dp
    val ListItemDividerThickness  = 0.5.dp
    val ListRowMinHeight          = 64.dp

    // ── Time Slot Chips ───────────────────────────────────────────────────
    val TimeSlotChipHeight      = 44.dp
    val TimeSlotChipCorner      = 10.dp
    val TimeSlotChipMinWidth    = 88.dp

    // ── Badge / Status Pills ──────────────────────────────────────────────
    val BadgePaddingHorizontal  = 10.dp
    val BadgePaddingVertical    = 4.dp
    val BadgeCornerRadius       = 20.dp   // Fully rounded pills

    // ── Notification / OTP Digit Box ──────────────────────────────────────
    val OtpBoxSize              = 44.dp
    val OtpBoxCornerRadius      = 10.dp
    val OtpBoxSpacing           = 8.dp

    // ── Call Screen ───────────────────────────────────────────────────────
    val CallControlButtonSize   = 56.dp   // Mic / End / Video buttons
    val CallControlCornerRadius = 28.dp   // Fully circular

    // ── Message Bubble ────────────────────────────────────────────────────
    val BubbleCornerRadius      = 16.dp
    val BubbleCornerSmall       = 4.dp    // Corner on the "tail" side
    val BubbleMaxWidthFraction  = 0.75f   // 75% of screen width
    val BubblePaddingHorizontal = 14.dp
    val BubblePaddingVertical   = 10.dp

    // ── Quick Action Grid ─────────────────────────────────────────────────
    val QuickActionCardSize     = 100.dp  // Square card
    val QuickActionGridSpacing  = 12.dp

    // ── Doctor List Card ──────────────────────────────────────────────────
    val DoctorCardCornerRadius  = 16.dp
    val DoctorCardPadding       = 16.dp
    val StarIconSize            = 14.dp

    // ── Appointment Confirmed / Modal Dialogs ────────────────────────────
    val ModalCornerRadius       = 24.dp
    val ModalPaddingHorizontal  = 24.dp
    val ModalPaddingVertical    = 32.dp
    val ModalIconContainerSize  = 72.dp
}

// ─── Shape Scheme ─────────────────────────────────────────────────────────────
// Used by MaterialTheme.shapes across the app
val HealthCarePlusShapes = Shapes(
    // ExtraSmall: badges, chips, OTP boxes
    extraSmall = RoundedCornerShape(Dimens.BadgeCornerRadius),

    // Small: input fields, time slot chips
    small      = RoundedCornerShape(Dimens.InputCornerRadius),

    // Medium: buttons, quick action cards
    medium     = RoundedCornerShape(Dimens.ButtonCornerRadius),

    // Large: content cards, doctor cards
    large      = RoundedCornerShape(Dimens.CardCornerRadius),

    // ExtraLarge: modals / bottom sheets / header banners
    extraLarge = RoundedCornerShape(Dimens.ModalCornerRadius),
)