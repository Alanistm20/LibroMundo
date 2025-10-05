package edu.com.cibertec.libromundo.model

data class CompraLibros (
    val listaLibros: MutableList<Libro> = mutableListOf()
) {
    fun totalLibros(): Int = listaLibros.sumOf { it.cantidad }
    fun subtotal(): Double = listaLibros.sumOf { it.calcularSubtotal() }

    // devuelve Pair(descuentoEnSoles, etiquetaColor)
    fun calcularDescuento(): Pair<Double, String> {
        val cantidad = totalLibros()
        val sub = subtotal()
        return when {
            cantidad in 1..2 -> 0.0 to "Gris"
            cantidad in 3..5 -> sub * 0.10 to "Verde"
            cantidad in 6..10 -> sub * 0.15 to "Azul"
            cantidad >= 11 -> sub * 0.20 to "Dorado"
            else -> 0.0 to "Gris"
        }
    }

    fun totalFinal(): Double {
        val (descuento, _) = calcularDescuento()
        return subtotal() - descuento
    }
}