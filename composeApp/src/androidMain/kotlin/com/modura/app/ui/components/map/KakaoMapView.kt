package com.modura.app.ui.components.map

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.LatLng as KakaoLatLng
import com.modura.app.data.dev.LatLng
import com.modura.app.domain.Location
import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.ui.screens.map.MapScreenModel
import org.koin.compose.koinInject

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
actual fun KakaoMapView(modifier: Modifier, places: List<PlaceResponseModel>,
                        currentLocation: Location?) {
    val context = LocalContext.current
    val locationClient = remember {LocationServices.getFusedLocationProviderClient(context) }
    val screenModel: MapScreenModel = koinInject()

    val mapView = remember {
        MapView(context)
    }

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )

    var currentLatLng by remember { mutableStateOf<KakaoLatLng?>(null) }
    var locationError by remember { mutableStateOf<String?>(null) }

    Box(modifier = modifier) {
        if (locationPermissionsState.allPermissionsGranted) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // 권한이 없으면 권한을 요청합니다.
            LaunchedEffect(Unit) {
                locationPermissionsState.launchMultiplePermissionRequest()
            }
            Text("지도를 표시하려면 위치 권한이 필요합니다.", modifier = Modifier.align(Alignment.Center))
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    mapView.start(object : MapLifeCycleCallback() {
                        override fun onMapDestroy() { /* 지도 API가 더 이상 사용되지 않을 때 호출 */ }
                        override fun onMapError(error: Exception) { Log.e("KakaoMapView", "Map Error", error) }
                    }, object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) { /* 지도 로딩 완료 */ }
                    })
                }
                Lifecycle.Event.ON_RESUME -> mapView.resume()
                Lifecycle.Event.ON_PAUSE -> mapView.pause()
                Lifecycle.Event.ON_DESTROY -> mapView.finish()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}