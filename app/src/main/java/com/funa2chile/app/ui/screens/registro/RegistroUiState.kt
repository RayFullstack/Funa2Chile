package com.funa2chile.app.ui.screens.registro

data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val rut: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nombreError: String? = null,
    val correoError: String? = null,
    val rutError: String? = null,
    val passwordError: String? = null,
    val confirmError: String? = null,
    val cargando: Boolean = false,
    val errorGeneral: String? = null,
    val registroExitoso: Boolean = false
) {
    val formularioValido: Boolean
        get() = nombre.isNotBlank() && correo.isNotBlank() && rut.isNotBlank() &&
                password.isNotBlank() && confirmPassword.isNotBlank() &&
                nombreError == null && correoError == null && rutError == null &&
                passwordError == null && confirmError == null
}
