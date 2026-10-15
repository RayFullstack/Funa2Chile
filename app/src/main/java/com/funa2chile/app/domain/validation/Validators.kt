package com.funa2chile.app.domain.validation

/** Cada validador retorna null si es válido, o el mensaje de error. */
object EmailValidator {
    private val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    fun validate(v: String): String? = when {
        v.isBlank() -> "El correo es obligatorio"
        !regex.matches(v.trim()) -> "Formato de correo inválido"
        else -> null
    }
}

object PasswordValidator {
    fun validate(v: String): String? = when {
        v.isBlank() -> "La contraseña es obligatoria"
        v.length < 8 -> "Mínimo 8 caracteres"
        !v.any { it.isUpperCase() } -> "Debe incluir una mayúscula"
        !v.any { it.isDigit() } -> "Debe incluir un número"
        else -> null
    }
}

object NombreValidator {
    fun validate(v: String): String? = when {
        v.isBlank() -> "El nombre es obligatorio"
        v.trim().length < 3 -> "Mínimo 3 caracteres"
        !v.all { it.isLetter() || it == ' ' } -> "Solo letras y espacios"
        else -> null
    }
}

object ConfirmPasswordValidator {
    fun validate(password: String, confirmacion: String): String? = when {
        confirmacion.isBlank() -> "Confirma tu contraseña"
        password != confirmacion -> "Las contraseñas no coinciden"
        else -> null
    }
}
