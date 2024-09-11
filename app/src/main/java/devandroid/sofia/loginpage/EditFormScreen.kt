import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import devandroid.sofia.loginpage.data.Chamado

@Composable
fun EditFormScreen(
    navController: NavController,
    chamado: Chamado,
    updateCard: (Chamado) -> Unit
) {
    var newNameProject by remember { mutableStateOf(chamado.name) }

    Column {
        TextField(
            value = newNameProject,
            onValueChange = { newNameProject = it },
            label = { Text("Nome do Projeto") }
        )

        Button(onClick = {
            val updatedChamado = chamado.copy(name = newNameProject)
            updateCard(updatedChamado)
            navController.popBackStack() // Voltar para a tela anterior
        }) {
            Text("Salvar")
        }
    }
}
