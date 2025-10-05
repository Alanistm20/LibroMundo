package edu.com.cibertec.libromundo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.com.cibertec.libromundo.model.Libro
import edu.com.cibertec.libromundo.utils.formatoSoles

@Composable
fun BookCard(libro: Libro, onEliminar: () -> Unit) {
    Card(modifier = Modifier
        .padding(vertical = 6.dp)
        .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Categoría: ${libro.categoria.nombre}")
            Text(text = "Precio unitario: ${libro.precio.formatoSoles()}")
            Text(text = "Cantidad: ${libro.cantidad}")
            Text(text = "Subtotal: ${libro.calcularSubtotal().formatoSoles()}")
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                TextButton(onClick = onEliminar) {
                    Text("Eliminar")
                }
            }
        }
    }
}
