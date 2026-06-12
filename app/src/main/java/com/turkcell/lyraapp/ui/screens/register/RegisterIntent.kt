package com.turkcell.lyraapp.ui.screens.register

sealed interface RegisterIntent {
    data class FirstNameChanged(val value: String) : RegisterIntent
    data class LastNameChanged(val value: String) : RegisterIntent
    data class PhoneNumberChanged(val value: String) : RegisterIntent
    data class PasswordChanged(val value: String) : RegisterIntent
    data object PasswordVisibilityToggled : RegisterIntent
    data object TermsAcceptanceToggled : RegisterIntent
    data object RegisterClicked : RegisterIntent
    data object NavigateToLoginClicked : RegisterIntent
    data object BackClicked : RegisterIntent
}
