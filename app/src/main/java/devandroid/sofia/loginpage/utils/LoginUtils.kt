import android.os.Handler
import android.os.Looper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

fun login(username: String, password: String, onResult: (String) -> Unit) {
    val client = OkHttpClient()

    val json = JSONObject().apply {
        put("username", username)
        put("password", password)
    }

    val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

    val request = Request.Builder()
        .url("https://workgeo.com.br/teste/sofia/login.php")
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            // Usar Handler para executar no thread principal
            Handler(Looper.getMainLooper()).post {
                onResult("Falha na conexão: ${e.message}")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            val responseData = response.body?.string() ?: "Nenhuma resposta do servidor"
            if (response.isSuccessful) {
                try {
                    val jsonResponse = JSONObject(responseData)
                    val status = jsonResponse.optString("status")
                    val message = jsonResponse.optString("message")

                    // Usar Handler para executar no thread principal
                    Handler(Looper.getMainLooper()).post {
                        if (status == "success") {
                            onResult("Login bem-sucedido")
                        } else {
                            onResult("Credenciais incorretas")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Usar Handler para executar no thread principal
                    Handler(Looper.getMainLooper()).post {
                        onResult("Erro ao processar a resposta")
                    }
                }
            } else {
                // Usar Handler para executar no thread principal
                Handler(Looper.getMainLooper()).post {
                    onResult("Erro no servidor: ${response.message}")
                }
            }
        }
    })
}
