import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import devandroid.sofia.loginpage.R
import devandroid.sofia.loginpage.ui.theme.principal
import devandroid.sofia.loginpage.data.Chamado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    cards: MutableState<List<Chamado>>,
    selectedOption: String
) {
    var searchQuery by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("") }

    val filteredCards = cards.value.filter { card ->
        card.name.contains(searchQuery, ignoreCase = true) &&
                (selectedStatus.isEmpty() || card.status == selectedStatus)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF8F8FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Navegação e logo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .shadow(4.dp, spotColor = Color.LightGray),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Trecho Selecionado
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = principal, shape = RoundedCornerShape(8.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Trecho Selecionado: $selectedOption")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Barra de pesquisa
            TextField(
                value = searchQuery,
                onValueChange = { newQuery -> searchQuery = newQuery },
                placeholder = { Text("Pesquisar por nome do projeto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Filtro de Status
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                Button(
                    onClick = { isDropdownExpanded = !isDropdownExpanded },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.DarkGray
                    ),
                    modifier = Modifier.width(200.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (selectedStatus.isEmpty()) "Selecione o Status" else selectedStatus,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (isDropdownExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Toggle Dropdown",
                            tint = Color.DarkGray
                        )
                    }
                }
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .shadow(elevation = 0.dp, shape = RoundedCornerShape(0.dp))
                ) {
                    DropdownMenuItem(
                        text = { Text("Aprovado", color = Color.DarkGray) },
                        onClick = {
                            selectedStatus = "Aprovado"
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Recusado", color = Color.DarkGray) },
                        onClick = {
                            selectedStatus = "Recusado"
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Pendente", color = Color.DarkGray) },
                        onClick = {
                            selectedStatus = "Pendente"
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Incompleto", color = Color.DarkGray) },
                        onClick = {
                            selectedStatus = "Incompleto"
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Todos", color = Color.DarkGray) },
                        onClick = {
                            selectedStatus = ""
                            isDropdownExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botão para adicionar novo cadastro
            Button(
                onClick = { navController.navigate("form") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = principal,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Novo Cadastro", modifier = Modifier.padding(end = 8.dp))
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de cartões filtrados
            filteredCards.forEach { card ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .shadow(
                            8.dp,
                            shape = RoundedCornerShape(10.dp),
                            spotColor = Color.LightGray
                        )
                        .clickable {
                            navController.navigate("card_detail/${card.id}/${card.status}/${card.priority}/${card.type}/${card.name}")
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = card.name)
                        Box(
                            modifier = Modifier
                                .background(
                                    color =
                                    when (card.status) {
                                        "Aprovado" -> Color(0xFFC7F1C0)
                                        "Recusado" -> Color(0xFFFFA9A9)
                                        "Pendente" -> Color(0xFFFFDEAA)
                                        "Incompleto" -> Color(0xFFC0D4F1)
                                        else -> Color.White
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                        ) {
                            Text(
                                text = card.status,
                                color = when (card.status) {
                                    "Aprovado" -> Color(0xFF6CAA62)
                                    "Recusado" -> Color(0xFFFF5C5C)
                                    "Pendente" -> Color(0xFFBC8635)
                                    "Incompleto" -> Color(0xFF6272AA)
                                    else -> Color.Black
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
