package edu.com.cibertec.libromundo.ui.theme.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.com.cibertec.libromundo.model.CategoriaLibro
import edu.com.cibertec.libromundo.ui.components.BookCard
import edu.com.cibertec.libromundo.ui.components.ConfirmDialog
import edu.com.cibertec.libromundo.viewmodel.LibroViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: LibroViewModel, navController: NavController) {
    val lista = viewModel.listaLibros
    val snackState by remember { derivedStateOf { viewModel.snackbarState.value } }
    var visibleSnackbar by remember { mutableStateOf(false) }
    var snackbarColorTag by remember { mutableStateOf<String?>(null) }
    var snackbarMessage by remember { mutableStateOf("") }

    // Mostrar snackbar con retardo
    LaunchedEffect(snackState) {
        val (msg, tag) = snackState
        if (msg.isNotBlank()) {
            snackbarMessage = msg
            snackbarColorTag = tag
            visibleSnackbar = true
            delay(2500)
            visibleSnackbar = false
            // Resetear estado
            viewModel.snackbarState.value = "" to null
        }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Mostrar resumen") },
                icon = { Icon(Icons.Filled.List, contentDescription = "Resumen") },
                onClick = { navController.navigate("resumen") },
                expanded = lista.isNotEmpty()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.titulo.value,
                onValueChange = { viewModel.titulo.value = it },
                label = { Text("Título del libro") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.precio.value,
                onValueChange = { viewModel.precio.value = it },
                label = { Text("Precio (ej: 45.50)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.cantidad.value,
                onValueChange = { viewModel.cantidad.value = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Categoría:")
            CategoriaLibro.values().forEach { cat ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.categoriaSeleccionada.value == cat,
                        onClick = { viewModel.categoriaSeleccionada.value = cat }
                    )
                    Text(cat.nombre)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // centrar
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { viewModel.agregarLibro() }) {
                        Text("Agregar al carrito")
                    }

                    Button(onClick = {
                        if (lista.isEmpty()) {
                            viewModel.snackbarState.value = "Debe tener al menos 1 libro en el carrito" to "Gris"
                        } else {
                            val compra = viewModel.compraActual()
                            val (descuento, tag) = compra.calcularDescuento()
                            val mensaje = when (tag) {
                                "Gris" -> "No hay descuento aplicado"
                                "Verde" -> "¡Genial! Ahorraste S/. ${"%.2f".format(descuento)}"
                                "Azul" -> "¡Excelente! Ahorraste S/. ${"%.2f".format(descuento)}"
                                "Dorado" -> "¡Increíble! Ahorraste S/. ${"%.2f".format(descuento)}"
                                else -> "No hay descuento aplicado"
                            }
                            viewModel.snackbarState.value = mensaje to tag
                        }
                    }) {
                        Text("Calcular total")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { viewModel.solicitarLimpiarCarrito() }) {
                    Text("Limpiar carrito")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Carrito:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            if (lista.isEmpty()) {
                Text("No hay libros en el carrito")
            } else {
                LazyColumn {
                    items(lista) { libro ->
                        BookCard(libro = libro) {
                            viewModel.confirmarEliminar(libro)
                        }
                    }
                }
            }
        }

       //elim
        val (showEliminar, libroAEliminar) = viewModel.mostrarDialogoEliminar.value
        if (showEliminar && libroAEliminar != null) {
            ConfirmDialog(
                title = "Eliminar libro",
                text = "¿Eliminar '${libroAEliminar.titulo}' del carrito?",
                onConfirm = {
                    viewModel.eliminarLibro(libroAEliminar)
                    viewModel.mostrarDialogoEliminar.value = false to null
                },
                onDismiss = { viewModel.mostrarDialogoEliminar.value = false to null }
            )
        }

      //limp
        if (viewModel.mostrarDialogoLimpiar.value) {
            ConfirmDialog(
                title = "Limpiar carrito",
                text = "¿Está seguro de limpiar el carrito?",
                onConfirm = { viewModel.limpiarCarrito() },
                onDismiss = { viewModel.mostrarDialogoLimpiar.value = false }
            )
        }

        // Snackbar visible manual
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            AnimatedVisibility(
                visible = visibleSnackbar,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                println("⚡ snackbarColorTag actual: $snackbarColorTag")
                val color = when (snackbarColorTag) {
                    "Verde" -> MaterialTheme.colorScheme.primary
                    "Azul" -> MaterialTheme.colorScheme.tertiary
                    "Dorado" -> MaterialTheme.colorScheme.secondary
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }


                Snackbar(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    containerColor = color
                ) {
                    Text(snackbarMessage)
                }
            }
        }
    }
}
