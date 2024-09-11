import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import devandroid.sofia.loginpage.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") } // Mensagem de sucesso
    var isLoading by remember { mutableStateOf(false) }

    fun validateLogin() {
        if (username.isBlank() || password.isBlank()) {
            loginError = "Usuário e Senha obrigatórios"
            successMessage = ""
            return
        }
        isLoading = true
        login(username, password) { result ->
            isLoading = false
            if (result.startsWith("Login bem-sucedido")) {
                loginError = ""
                successMessage = result // Mensagem de sucesso
                // Navega para a tela "home"
                navController.navigate("trecho")
            } else {
                successMessage = ""
                loginError = result // Mensagem de erro
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp, top = 8.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Login Icon",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Text(
            text = "Bem - Vindo ao [appname]",
            color = Color(0xFF545454),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(80.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo Workgeo",
            modifier = Modifier
                .size(156.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(72.dp))

        TextField(
            value = username,
            onValueChange = { newValue -> username = newValue },
            label = { Text(text = "Usuário") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Icone de Usuário"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(48.dp))

        TextField(
            value = password,
            onValueChange = { newValue -> password = newValue },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(text = "Senha") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Icone de Senha"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(72.dp))
        Button(
            onClick = {
                validateLogin()
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
            Text(
                text = if (isLoading) "ENTRANDO..." else "ENTRAR",
                modifier = Modifier
                    .weight(1f)

                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp),
                textAlign = TextAlign.Center
            )
        }


        Spacer(modifier = Modifier.height(16.dp))
        // Mensagem de erro
        Text(
            text = loginError,
            color = Color.Red,
            modifier = Modifier.fillMaxWidth()
        )

        // Mensagem de sucesso
        Text(
            text = successMessage,
            color = Color.Green,
            modifier = Modifier.fillMaxWidth()
        )
    }
}