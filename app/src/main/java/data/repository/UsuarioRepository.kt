package com.funa2chile.app.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.funa2chile.app.data.local.dao.UsuarioDao
import com.funa2chile.app.data.local.entity.UsuarioEntity
import com.funa2chile.app.data.local.entity.toDomain
import com.funa2chile.app.domain.model.Usuario
import java.security.MessageDigest

class UsuarioRepository(private val dao: UsuarioDao) {

    suspend fun registrar(nombre: String, correo: String, rut: String, password: String): Result<Long> =
        try {
            Result.success(
                dao.insertar(
                    UsuarioEntity(
                        nombre = nombre, correo = correo.trim().lowercase(),
                        rut = rut, passwordHash = hash(password)
                    )
                )
            )
        } catch (e: SQLiteConstraintException) {
            Result.failure(IllegalStateException("Ese correo ya está registrado"))
        }

    /** Retorna el usuario si las credenciales son correctas, o null. */
    suspend fun login(correo: String, password: String): Usuario? {
        val u = dao.buscarPorCorreo(correo.trim().lowercase()) ?: return null
        return if (u.passwordHash == hash(password)) u.toDomain() else null
    }

    // Para el avance basta SHA-256; en producción usar bcrypt/argon2 con salt.
    private fun hash(s: String): String =
        MessageDigest.getInstance("SHA-256").digest(s.toByteArray())
            .joinToString("") { "%02x".format(it) }
}
