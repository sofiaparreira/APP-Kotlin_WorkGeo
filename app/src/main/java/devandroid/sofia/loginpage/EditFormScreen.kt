import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import devandroid.sofia.loginpage.R
import devandroid.sofia.loginpage.data.Chamado

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun EditFormScreen(
    navController: NavController,
    chamado: Chamado,
    updateCard: (Chamado) -> Unit
) {
    var newNameProject by remember { mutableStateOf(chamado.name) }
    var newEditablePriority by remember { mutableStateOf(chamado.priority) }
    val newPriorityOptions = listOf("Baixa", "Média", "Alta", "Normal")

    var editableName by remember { mutableStateOf("") }
    var editableStatus by remember { mutableStateOf("") }

    var editableType by remember { mutableStateOf("") }

    var newExpandedPriority by remember { mutableStateOf(false) }
    var expandedStatus by remember { mutableStateOf(false) }
    var expandedType by remember { mutableStateOf(false) }


    val statusOptions = listOf("Aprovado", "Recusado", "Incompleto", "Pendente")
    val typeOptions = listOf("Estrutural", "Ambiental", "Fundiário")

    Column {

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

        TextField(
            value = newNameProject,
            onValueChange = { newNameProject = it },
            label = { Text("Nome do Projeto") }
        )

        ExposedDropdownMenuBox(
            expanded = newExpandedPriority,
            onExpandedChange = { newExpandedPriority = !newExpandedPriority },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = newEditablePriority,
                onValueChange = {},
                readOnly = true,
                label = { Text("Prioridade") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = newExpandedPriority)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = newExpandedPriority,
                onDismissRequest = { newExpandedPriority = false }
            ) {
                newPriorityOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            newEditablePriority = option
                            newExpandedPriority = false
                        }
                    )
                }
            }
        } // end dropdown priority

        Button(
            onClick = {
                // Atualize o objeto Chamado com o novo nome e prioridade
                val updatedChamado = chamado.copy(name = newNameProject, priority = newEditablePriority)

                // Save new values
                navController.previousBackStackEntry?.savedStateHandle?.set("updatedProjectName", newNameProject)
                navController.previousBackStackEntry?.savedStateHandle?.set("updatedProjectPriority", newEditablePriority)

                // back screen
                navController.popBackStack()
            },) {
            Text("Salvar")
        }
    }
    }
