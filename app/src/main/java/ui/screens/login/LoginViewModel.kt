package com.funa2chile.app.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funa2chile.app.data.repository.UsuarioRepository
import com.funa2chile.app.domain.validation.EmailValidator
import com.funa2chile.app.domain.validation.PasswordValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: UsuarioRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onCorreoChange(valor: String) = _uiState.update {
        it.copy(correo = valor, correoError = EmailValidator.validate(valor), errorGeneral = null)
    }

    fun onPasswordChange(valor: String) = _uiState.update {
        it.copy(password = valor, passwordError = PasswordValidator.validate(valor), errorGeneral = null)
    }

    fun onLoginClick() {
        val s = _uiState.value
        // Revalida todo al enviar (por si el usuario no tocó algún campo)
        val correoErr = EmailValidator.validate(s.correo)
        val passErr = PasswordValidator.validate(s.password)
        if (correoErr != null || passErr != null) {
            _uiState.update { it.copy(correoError = correoErr, passwordError = passErr) }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, errorGeneral = null) }
            val usuario = repo.login(s.correo, s.password)
            _uiState.update {
                if (usuario != null) it.copy(cargando = false, loginExitoso = true)
                else it.copy(cargando = false, errorGeneral = "Correo o contraseña incorrectos")
            }
        }
    }
}
