package edu.com.cibertec.libromundo.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.com.cibertec.libromundo.utils.formatoSoles
import edu.com.cibertec.libromundo.viewmodel.LibroViewModel

@Composable
fun ResumenScreen(viewModel: LibroViewModel, navController: NavController) {
    val compra = viewModel.compraActual()
    val subtotal = compra.subtotal()
    val (descuento, tag) = compra.calcularDescuento()
    val total = compra.totalFinal()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text("Resumen de compra", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Subtotal: ", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(6.dp))
            Text(subtotal.formatoSoles())
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Descuento: ", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(6.dp))
            Text("${descuento.formatoSoles()} (${when(tag){
                "Verde"->"10%-15%-20% según tabla"
                "Azul"->"15%"
                "Dorado"->"20%"
                else->"0%"
            }})")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Total a pagar: ", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(6.dp))
            Text(total.formatoSoles())
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Badge con color según descuento
        val badgeColor = when (tag) {
            "Verde" -> MaterialTheme.colorScheme.primary
            "Azul" -> MaterialTheme.colorScheme.tertiary
            "Dorado" -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.surfaceVariant
        }
        Badge(
            containerColor = badgeColor,
            modifier = Modifier.size(64.dp)
        ) {
            Text(text = "${compra.totalLibros()}")
        }
    }
}
