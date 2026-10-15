package com.funa2chile.app.ui.screens.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funa2chile.app.data.repository.UsuarioRepository
import com.funa2chile.app.domain.validation.ConfirmPasswordValidator
import com.funa2chile.app.domain.validation.EmailValidator
import com.funa2chile.app.domain.validation.NombreValidator
import com.funa2chile.app.domain.validation.PasswordValidator
import com.funa2chile.app.domain.validation.RutValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(private val repo: UsuarioRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun onNombreChange(v: String) = _uiState.update {
        it.copy(nombre = v, nombreError = NombreValidator.validate(v), errorGeneral = null)
    }

    fun onCorreoChange(v: String) = _uiState.update {
        it.copy(correo = v, correoError = EmailValidator.validate(v), errorGeneral = null)
    }

    /** Guarda solo el RUT limpio (dígitos y K); el formato visual lo maneja CampoRut. */
    fun onRutChange(v: String) {
        val limpio = RutValidator.limpiar(v).take(9)
        _uiState.update {
            it.copy(rut = limpio, rutError = RutValidator.validate(limpio), errorGeneral = null)
        }
    }

    fun onPasswordChange(v: String) = _uiState.update {
        it.copy(
            password = v,
            passwordError = PasswordValidator.validate(v),
            // si ya escribió la confirmación, se revalida contra la nueva clave
            confirmError = if (it.confirmPassword.isNotEmpty())
                ConfirmPasswordValidator.validate(v, it.confirmPassword) else it.confirmError,
            errorGeneral = null
        )
    }

    fun onConfirmChange(v: String) = _uiState.update {
        it.copy(
            confirmPassword = v,
            confirmError = ConfirmPasswordValidator.validate(it.password, v),
            errorGeneral = null
        )
    }

    fun onRegistrarClick() {
        val s = _uiState.value
        // Revalida todo al enviar
        val nombreErr = NombreValidator.validate(s.nombre)
        val correoErr = EmailValidator.validate(s.correo)
        val rutErr = RutValidator.validate(s.rut)
        val passErr = PasswordValidator.validate(s.password)
        val confErr = ConfirmPasswordValidator.validate(s.password, s.confirmPassword)
        if (listOf(nombreErr, correoErr, rutErr, passErr, confErr).any { it != null }) {
            _uiState.update {
                it.copy(
                    nombreError = nombreErr, correoError = correoErr, rutError = rutErr,
                    passwordError = passErr, confirmError = confErr
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, errorGeneral = null) }
            val resultado = repo.registrar(
                nombre = s.nombre.trim(),
                correo = s.correo,
                rut = RutValidator.limpiar(s.rut),
                password = s.password
            )
            _uiState.update {
                resultado.fold(
                    onSuccess = { _ -> it.copy(cargando = false, registroExitoso = true) },
                    onFailure = { e -> it.copy(cargando = false, errorGeneral = e.message ?: "No se pudo registrar") }
                )
            }
        }
    }
}
