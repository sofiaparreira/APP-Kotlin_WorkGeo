package devandroid.sofia.loginpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import devandroid.sofia.loginpage.data.Chamado

@Composable
fun CardDetailScreen(
    navController: NavController,
    onEditClick: () -> Unit,
    chamado: Chamado
) {
    // Variáveis de estado para o nome do projeto e a prioridade
    var projectName by remember { mutableStateOf(chamado.name) }
    var projectPriority by remember { mutableStateOf(chamado.priority) }

    // Recuperar o savedStateHandle para verificar se o nome ou a prioridade foram atualizados
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    // Verificar se há um nome atualizado salvo no savedStateHandle
    savedStateHandle?.get<String>("updatedProjectName")?.let { updatedName ->
        projectName = updatedName
        // Remover o valor do savedStateHandle para evitar reutilização
        savedStateHandle.remove<String>("updatedProjectName")
    }

    // Verificar se há uma prioridade atualizada salva no savedStateHandle
    savedStateHandle?.get<String>("updatedProjectPriority")?.let { updatedPriority ->
        projectPriority = updatedPriority
        // Remover o valor do savedStateHandle para evitar reutilização
        savedStateHandle.remove<String>("updatedProjectPriority")
    }

    // Layout da tela
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .shadow(4.dp, spotColor = Color.LightGray),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                            .clickable { navController.navigateUp() }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        } // FIM DO NAV BAR


        // Exibir detalhes do cartão usando as propriedades do objeto Chamado
        Text("Nome do Projeto: $projectName", fontSize = 20.sp)
        Text("Status: ${chamado.status}", fontSize = 16.sp)
        Text("Prioridade: $projectPriority", fontSize = 16.sp) // Mostrando a prioridade atualizada
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
