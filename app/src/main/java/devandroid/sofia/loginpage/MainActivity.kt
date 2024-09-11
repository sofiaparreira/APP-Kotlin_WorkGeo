package devandroid.sofia.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import devandroid.sofia.loginpage.utils.NavigationController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationController()
        }
    }
}

