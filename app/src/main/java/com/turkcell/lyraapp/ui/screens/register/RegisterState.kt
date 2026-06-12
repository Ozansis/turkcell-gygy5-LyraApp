package com.turkcell.lyraapp.ui.screens.register

data class RegisterState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val termsAccepted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)
