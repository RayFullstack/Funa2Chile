package com.funa2chile.app.ui.screens.login

/** Todo lo que la pantalla necesita para dibujarse. La UI solo lee esto. */
data class LoginUiState(
    val correo: String = "",
    val password: String = "",
    val correoError: String? = null,
    val passwordError: String? = null,
    val cargando: Boolean = false,
    val errorGeneral: String? = null,
    val loginExitoso: Boolean = false
) {
    val formularioValido: Boolean
        get() = correo.isNotBlank() && password.isNotBlank() &&
                correoError == null && passwordError == null
}
