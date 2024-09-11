package devandroid.sofia.loginpage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import devandroid.sofia.loginpage.data.Chamado

@Composable
fun CardDetailScreen(
    navController: NavController,
    onEditClick: () -> Unit, // Função para navegar para a tela de edição
    chamado: Chamado // Objeto Chamado que contém todos os detalhes
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Exibir detalhes do cartão usando as propriedades do objeto Chamado
        Text("Nome do Projeto: ${chamado.name}", fontSize = 20.sp)
        Text("Status: ${chamado.status}", fontSize = 16.sp)
        Text("Prioridade: ${chamado.priority}", fontSize = 16.sp)
        Text("Tipo: ${chamado.type}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de edição
        Button(
            onClick = onEditClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF6A52B), contentColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Editar", fontSize = 16.sp)
        }
    }
}
