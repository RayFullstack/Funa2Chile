package com.funa2chile.app.domain.validation

/** Kotlin puro: sin imports de Android ni Compose (fácil de testear). */
object RutValidator {

    /** Devuelve null si el RUT es válido, o el mensaje de error. */
    fun validate(input: String): String? {
        val rut = limpiar(input)
        if (rut.isBlank()) return "El RUT es obligatorio"
        if (rut.length < 2) return "RUT incompleto"
        val cuerpo = rut.dropLast(1)
        val dv = rut.last()
        if (!cuerpo.all { it.isDigit() }) return "El RUT solo debe contener números y K"
        if (cuerpo.length !in 7..8) return "Largo de RUT inválido"
        return if (calcularDv(cuerpo) == dv) null else "RUT inválido (dígito verificador)"
    }

    /** Quita puntos y guion, deja mayúsculas: "12.345.678-5" -> "123456785" */
    fun limpiar(input: String): String =
        input.filter { it.isDigit() || it == 'k' || it == 'K' }.uppercase()

    /** Formato visual: "123456785" -> "12.345.678-5" */
    fun formatear(input: String): String {
        val rut = limpiar(input)
        if (rut.length < 2) return rut
        val cuerpo = rut.dropLast(1).reversed().chunked(3).joinToString(".").reversed()
        return "$cuerpo-${rut.last()}"
    }

    /** Para listados: "12.345.678-5" -> "12.345.***-5" */
    fun enmascarar(input: String): String {
        val f = formatear(input)
        val i = f.lastIndexOf('.')
        return if (i == -1) f else f.substring(0, i + 1) + "***" + f.substring(f.lastIndexOf('-'))
    }

    private fun calcularDv(cuerpo: String): Char {
        var suma = 0
        var mult = 2
        for (c in cuerpo.reversed()) {
            suma += c.digitToInt() * mult
            mult = if (mult == 7) 2 else mult + 1
        }
        return when (val r = 11 - (suma % 11)) {
            11 -> '0'
            10 -> 'K'
            else -> r.digitToChar()
        }
    }
}
