package edu.com.cibertec.libromundo.utils


import java.text.NumberFormat
import java.util.*

fun Double.formatoSoles(): String {
    val nf = NumberFormat.getCurrencyInstance(Locale("es","PE"))
    return nf.format(this)
}

fun Double.a2Dec(): String = String.format(Locale.US, "%.2f", this)
