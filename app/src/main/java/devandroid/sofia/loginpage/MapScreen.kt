package devandroid.sofia.loginpage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapScreen(navController: NavHostController) {
   val mapView = rememberMapViewWithLifecycle()

   AndroidView(factory = { mapView }) { mapView ->
      mapView.getMapAsync { googleMap ->
         val sydney = LatLng(-34.0, 151.0)
         googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
         googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))
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
