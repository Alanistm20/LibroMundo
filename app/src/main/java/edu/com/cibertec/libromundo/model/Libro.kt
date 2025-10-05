package edu.com.cibertec.libromundo.model

data class Libro (

        val titulo: String,
        val categoria: CategoriaLibro,
        val precio: Double,
        val cantidad: Int
    ) {
        fun calcularSubtotal(): Double = precio * cantidad
    }

