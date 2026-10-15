package com.funa2chile.app.domain.model

data class Usuario(
    val id: Long = 0,
    val nombre: String,
    val correo: String,
    val rut: String
)
