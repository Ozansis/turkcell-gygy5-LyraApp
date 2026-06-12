package com.turkcell.lyraapp.ui.screens.register

sealed interface RegisterEffect {
    data object NavigateToHome : RegisterEffect
    data object NavigateToLogin : RegisterEffect
    data object NavigateUp : RegisterEffect
}
