package com.funa2chile.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.funa2chile.app.data.local.entity.DenunciaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DenunciaDao {
    @Insert
    suspend fun insertar(denuncia: DenunciaEntity): Long

    @Query("SELECT * FROM denuncias WHERE rutDenunciado = :rut ORDER BY fechaHecho DESC")
    fun observarPorRut(rut: String): Flow<List<DenunciaEntity>>

    @Query("SELECT * FROM denuncias WHERE autorId = :autorId ORDER BY fechaHecho DESC")
    fun observarPorAutor(autorId: Long): Flow<List<DenunciaEntity>>
}
