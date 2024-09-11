package devandroid.sofia.loginpage

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import devandroid.sofia.loginpage.ui.theme.principal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrechoScreen(navController: NavHostController) {

    // Lista de opções do projeto
    val optionsProject = listOf("Opção 1", "Opção 2")

    // Estado para controlar a opção selecionada
    var selectedOptionText by remember { mutableStateOf(optionsProject[0]) }

    // Estado para controlar se o dropdown está aberto
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFE7E7E7),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selecione o trecho desejado",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 32.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(80.dp), // Altura da Row
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            value = selectedOptionText,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Selecione uma opção") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(0.8f)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            optionsProject.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(text = selectionOption) },
                                    onClick = {
                                        selectedOptionText = selectionOption
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            navController.navigate("home/${selectedOptionText}")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = principal,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .padding(start = 16.dp)
                    ) {
                        Text(text = "Confirmar")
                    }
                }
            }
        }
    }
}
