package com.funa2chile.app

import android.app.Application
import android.content.Context
import com.funa2chile.app.data.local.AppDatabase
import com.funa2chile.app.data.repository.DenunciaRepository
import com.funa2chile.app.data.repository.UsuarioRepository

/** Contenedor de dependencias (inyección manual, sin librerías extra). */
class AppContainer(context: Context) {
    private val db = AppDatabase.get(context)
    val usuarioRepository = UsuarioRepository(db.usuarioDao())
    val denunciaRepository = DenunciaRepository(db.denunciaDao())
}

class Funa2ChileApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
