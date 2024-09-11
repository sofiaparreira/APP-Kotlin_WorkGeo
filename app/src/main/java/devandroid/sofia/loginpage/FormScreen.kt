import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import devandroid.sofia.loginpage.R
import devandroid.sofia.loginpage.data.Chamado
import okhttp3.Route
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    navController: NavController,
    addCard: (Chamado) -> Unit
) {
    var editableName by remember { mutableStateOf("") }
    var editableStatus by remember { mutableStateOf("") }
    var editablePriority by remember { mutableStateOf("") }
    var editableType by remember { mutableStateOf("") }

    var expandedPriority by remember { mutableStateOf(false) }
    var expandedStatus by remember { mutableStateOf(false) }
    var expandedType by remember { mutableStateOf(false) }

    val priorityOptions = listOf("Baixa", "Média", "Alta", "Normal")
    val statusOptions = listOf("Aprovado", "Recusado", "Incompleto", "Pendente")
    val typeOptions = listOf("Estrutural", "Ambiental", "Fundiário")

    Column(modifier = Modifier.fillMaxSize()) {
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

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { navController.navigate("map") },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Gray
                ),
                shape = RoundedCornerShape(2.dp)) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location icon")
                Text(text = "Mapa")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = editableName,
                onValueChange = { editableName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nome do Projeto") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedPriority,
                onExpandedChange = { expandedPriority = !expandedPriority },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = editablePriority,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Prioridade") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPriority)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedPriority,
                    onDismissRequest = { expandedPriority = false }
                ) {
                    priorityOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                editablePriority = option
                                expandedPriority = false
                            }
                        )
                    }
                }
            } // end dropdown priority

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedStatus,
                onExpandedChange = { expandedStatus = !expandedStatus },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = editableStatus,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Status") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedStatus,
                    onDismissRequest = { expandedStatus = false }
                ) {
                    statusOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                editableStatus = option
                                expandedStatus = false
                            }
                        )
                    }
                }
            } // end dropdown status

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedType,
                onExpandedChange = { expandedType = !expandedType },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = editableType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedType)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedType,
                    onDismissRequest = { expandedType = false }
                ) {
                    typeOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                editableType = option
                                expandedType = false
                            }
                        )
                    }
                }
            } // end dropdown type

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Generate a unique ID for the new Chamado
                    val uniqueId = UUID.randomUUID().toString()
                    val cardData = Chamado(uniqueId, editableName, editableStatus, editablePriority, editableType)
                    addCard(cardData)
                    navController.navigateUp()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF6A52B),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Confirmar")
            }
        }
    }
}
