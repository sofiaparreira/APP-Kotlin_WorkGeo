package devandroid.sofia.loginpage

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapScreen(navController: NavHostController) {
   val mapView = rememberMapViewWithLifecycle()
   val context = LocalContext.current

   AndroidView(factory = { mapView }) { mapView ->
      mapView.getMapAsync { googleMap ->
         // Verificar permissões de localização
         if (ActivityCompat.checkSelfPermission(
               context,
               Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
               context,
               Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
         ) {
            // Habilitar localização
            googleMap.isMyLocationEnabled = true

            // Definir o local inicial no mapa (Sydney como exemplo)
            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))

            // Alternativa: Mover para a localização atual do usuário
            googleMap.setOnMyLocationButtonClickListener {
               val myLocation = googleMap.myLocation
               myLocation?.let {
                  val myLatLng = LatLng(myLocation.latitude, myLocation.longitude)
                  googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 15f))
               }
               true
            }
         } else {
            // Solicitar permissões de localização se não estiverem concedidas
            ActivityCompat.requestPermissions(
               context as androidx.activity.ComponentActivity,
               arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
               100
            )
         }
      }
   }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
   val context = LocalContext.current
   val mapView = remember { MapView(context) }
   val lifecycleOwner = LocalLifecycleOwner.current

   DisposableEffect(lifecycleOwner) {
      val lifecycle = lifecycleOwner.lifecycle

      // Initialize MapView
      mapView.onCreate(null)
      mapView.onResume()

      val observer = object : DefaultLifecycleObserver {
         override fun onPause(owner: LifecycleOwner) {
            mapView.onPause()
         }

         override fun onStop(owner: LifecycleOwner) {
            mapView.onStop()
         }

         override fun onDestroy(owner: LifecycleOwner) {
            mapView.onDestroy()
         }
      }

      lifecycle.addObserver(observer)

      onDispose {
         lifecycle.removeObserver(observer)
         mapView.onDestroy()
      }
   }

   return mapView
}
