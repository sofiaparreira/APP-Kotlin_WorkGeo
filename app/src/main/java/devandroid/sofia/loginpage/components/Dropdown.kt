import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuComponent(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Button(
            onClick = { expanded = !expanded },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedOption,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    modifier = Modifier.padding(end = 16.dp) // Espaçamento entre o texto e o ícone
                )
            }
        }
        


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp)
                ) // Adiciona a sombra ao DropdownMenu
                .background(Color.White) // Garante que o fundo do dropdown seja branco
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(text = option) },
                    modifier = Modifier.fillMaxWidth() // Garante que cada item ocupe toda a largura do menu
                )
            }
        }
    }
}
