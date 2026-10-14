package com.funa2chile.app.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.funa2chile.app.ui.AppViewModelProvider
import com.funa2chile.app.ui.components.CampoTexto

@Composable
fun LoginScreen(
    onLoginExitoso: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.loginExitoso) {
        if (state.loginExitoso) onLoginExitoso()
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(24.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Funa2Chile", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(24.dp))

            CampoTexto(
                valor = state.correo, onValorChange = viewModel::onCorreoChange,
                etiqueta = "Correo", iconoInicial = Icons.Default.Email,
                error = state.correoError, tipoTeclado = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
            CampoTexto(
                valor = state.password, onValorChange = viewModel::onPasswordChange,
                etiqueta = "Contraseña", iconoInicial = Icons.Default.Lock,
                error = state.passwordError, esPassword = true,
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(visible = state.errorGeneral != null) {
                Text(state.errorGeneral.orEmpty(), color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = viewModel::onLoginClick,
                enabled = state.formularioValido && !state.cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.cargando) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                else Text("Ingresar")
            }
        }
    }
}
