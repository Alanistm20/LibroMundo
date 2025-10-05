package edu.com.cibertec.libromundo.viewmodel
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import edu.com.cibertec.libromundo.model.*

class LibroViewModel : ViewModel() {
    // lista reactiva de libros del carrito
    val listaLibros = mutableStateListOf<Libro>()

    // inputs
    val titulo = mutableStateOf("")
    val precio = mutableStateOf("")
    val cantidad = mutableStateOf("")
    val categoriaSeleccionada = mutableStateOf<CategoriaLibro?>(null)

    // estado para mensajes y diálogos
    val mostrarDialogoEliminar = mutableStateOf<Pair<Boolean, Libro?>>(false to null)
    val mostrarDialogoLimpiar = mutableStateOf(false)

    // snackbar (mensaje y colorTag: "Gris","Verde","Azul","Dorado")
    val snackbarState = mutableStateOf<Pair<String, String?>>( "" to null )

    // helpers
    private fun limpiarCampos() {
        titulo.value = ""
        precio.value = ""
        cantidad.value = ""
        // mantener categoria seleccionada (según requerimiento)
    }

    fun agregarLibro(): Boolean {
        val t = titulo.value.trim()
        val p = precio.value.toDoubleOrNull() ?: -1.0
        val c = cantidad.value.toIntOrNull() ?: -1

        if (t.isEmpty()) {
            snackbarState.value = "Ingrese el título del libro" to "Gris"
            return false
        }
        if (p <= 0.0 || c <= 0) {
            snackbarState.value = "Precio y cantidad deben ser mayores a 0" to "Gris"
            return false
        }
        val cat = categoriaSeleccionada.value
        if (cat == null) {
            snackbarState.value = "Seleccione una categoría" to "Gris"
            return false
        }

        val libro = Libro(t, cat, p, c)
        listaLibros.add(libro)
        limpiarCampos()
        snackbarState.value = "Libro agregado al carrito" to "Verde"
        return true
    }

    fun confirmarEliminar(libro: Libro) {
        mostrarDialogoEliminar.value = true to libro
    }

    fun eliminarLibro(libro: Libro) {
        listaLibros.remove(libro)
        snackbarState.value = "Libro eliminado del carrito" to "Gris"
    }

    fun solicitarLimpiarCarrito() {
        mostrarDialogoLimpiar.value = true
    }

    fun limpiarCarrito() {
        listaLibros.clear()
        snackbarState.value = "Carrito limpiado" to "Gris"
        mostrarDialogoLimpiar.value = false
    }

    // cálculos con la compra actual
    fun compraActual(): CompraLibros = CompraLibros(listaLibros.toMutableList())
}