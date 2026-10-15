package com.funa2chile.app.ui.screens.registro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.funa2chile.app.ui.components.CampoRut
import com.funa2chile.app.ui.components.CampoTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit,
    viewModel: RegistroViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.registroExitoso) {
        if (state.registroExitoso) onRegistroExitoso()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            CampoTexto(
                valor = state.nombre, onValorChange = viewModel::onNombreChange,
                etiqueta = "Nombre completo", iconoInicial = Icons.Default.Person,
                error = state.nombreError, modifier = Modifier.fillMaxWidth()
            )
            CampoTexto(
                valor = state.correo, onValorChange = viewModel::onCorreoChange,
                etiqueta = "Correo", iconoInicial = Icons.Default.Email,
                error = state.correoError, tipoTeclado = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
            CampoRut(
                valor = state.rut, onValorChange = viewModel::onRutChange,
                error = state.rutError, modifier = Modifier.fillMaxWidth()

            )
            CampoTexto(
                valor = state.password, onValorChange = viewModel::onPasswordChange,
                etiqueta = "Contraseña", iconoInicial = Icons.Default.Lock,
                error = state.passwordError, esPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            CampoTexto(
                valor = state.confirmPassword, onValorChange = viewModel::onConfirmChange,
                etiqueta = "Confirmar contraseña", iconoInicial = Icons.Default.Lock,
                error = state.confirmError, esPassword = true,
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(visible = state.errorGeneral != null) {
                Text(state.errorGeneral.orEmpty(), color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(12.dp))
            Button(
                onClick = viewModel::onRegistrarClick,
                enabled = state.formularioValido && !state.cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.cargando) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                else Text("Registrarme")
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
