package com.funa2chile.app.domain.model

enum class CategoriaDenuncia(val etiqueta: String) {
    ESTAFA("Estafa"),
    FRAUDE_ONLINE("Fraude online"),
    MAL_ARRENDATARIO("Mal arrendatario"),
    INCUMPLIMIENTO_PAGO("Incumplimiento de pago"),
    OTRO("Otro")
}

/** Las denuncias NO son hechos comprobados: se muestran con su estado. */
enum class EstadoDenuncia(val etiqueta: String) {
    PENDIENTE("Pendiente"),
    EN_REVISION("En revisión"),
    RESPALDADA("Respaldada")
}

data class Denuncia(
    val id: Long = 0,
    val rutDenunciado: String,
    val categoria: CategoriaDenuncia,
    val descripcion: String,
    val monto: Long? = null,
    val fechaHecho: Long,
    val evidenciaUri: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null,
    val estado: EstadoDenuncia = EstadoDenuncia.PENDIENTE,
    val autorId: Long
)
