package com.funa2chile.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.funa2chile.app.domain.model.Usuario

@Entity(tableName = "usuarios", indices = [Index(value = ["correo"], unique = true)])
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val correo: String,
    val rut: String,
    val passwordHash: String
)

fun UsuarioEntity.toDomain() = Usuario(id, nombre, correo, rut)
