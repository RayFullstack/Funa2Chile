package com.funa2chile.app.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.funa2chile.app.Funa2ChileApp
import com.funa2chile.app.ui.screens.login.LoginViewModel

/** Fábrica central: aquí se le entregan los repositorios a cada ViewModel. */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { LoginViewModel(app().container.usuarioRepository) }
        // initializer { RegistroViewModel(app().container.usuarioRepository) }
        // initializer { NuevaDenunciaViewModel(app().container.denunciaRepository) }
    }
}

private fun CreationExtras.app(): Funa2ChileApp =
    this[APPLICATION_KEY] as Funa2ChileApp
