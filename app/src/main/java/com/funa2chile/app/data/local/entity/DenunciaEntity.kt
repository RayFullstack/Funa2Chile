package com.funa2chile.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.funa2chile.app.domain.model.CategoriaDenuncia
import com.funa2chile.app.domain.model.Denuncia
import com.funa2chile.app.domain.model.EstadoDenuncia

@Entity(tableName = "denuncias")
data class DenunciaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val rutDenunciado: String,
    val categoria: String,
    val descripcion: String,
    val monto: Long?,
    val fechaHecho: Long,
    val evidenciaUri: String?,
    val latitud: Double?,
    val longitud: Double?,
    val estado: String,
    val autorId: Long
)

fun DenunciaEntity.toDomain() = Denuncia(
    id, rutDenunciado, CategoriaDenuncia.valueOf(categoria), descripcion, monto,
    fechaHecho, evidenciaUri, latitud, longitud, EstadoDenuncia.valueOf(estado), autorId
)

fun Denuncia.toEntity() = DenunciaEntity(
    id, rutDenunciado, categoria.name, descripcion, monto,
    fechaHecho, evidenciaUri, latitud, longitud, estado.name, autorId
)
