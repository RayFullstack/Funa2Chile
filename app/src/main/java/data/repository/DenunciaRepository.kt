package com.funa2chile.app.data.repository

import com.funa2chile.app.data.local.dao.DenunciaDao
import com.funa2chile.app.data.local.entity.toDomain
import com.funa2chile.app.data.local.entity.toEntity
import com.funa2chile.app.domain.model.Denuncia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DenunciaRepository(private val dao: DenunciaDao) {

    suspend fun crear(denuncia: Denuncia): Long = dao.insertar(denuncia.toEntity())

    fun observarPorRut(rut: String): Flow<List<Denuncia>> =
        dao.observarPorRut(rut).map { lista -> lista.map { it.toDomain() } }

    fun observarPorAutor(autorId: Long): Flow<List<Denuncia>> =
        dao.observarPorAutor(autorId).map { lista -> lista.map { it.toDomain() } }
}
