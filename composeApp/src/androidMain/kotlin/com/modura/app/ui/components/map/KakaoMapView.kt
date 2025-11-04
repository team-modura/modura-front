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

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
actual fun KakaoMapView(modifier: Modifier) {
    val context = LocalContext.current
    val locationClient = remember {LocationServices.getFusedLocationProviderClient(context) }

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

    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
        if (locationPermissionsState.allPermissionsGranted) {
            val cancellationTokenSource = CancellationTokenSource()
            locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                location?.let {
                    currentLatLng = KakaoLatLng.from(it.latitude, it.longitude)
                    Log.d("KakaoMapView", "Location Success: $currentLatLng")
                } ?: run {
                    locationError = "위치 정보를 찾을 수 없습니다."
                    Log.e("KakaoMapView", "Location is null")
                }
            }.addOnFailureListener { e ->
                locationError = "위치 획득에 실패했습니다: ${e.message}"
                Log.e("KakaoMapView", "Location Failure", e)
            }
        }
    }

    Box(modifier = modifier) {
        when {
            locationPermissionsState.allPermissionsGranted -> {
                when {
                    currentLatLng != null -> {
                        KakaoMapViewInternal(
                            modifier = Modifier.fillMaxSize(),
                            kakaoLatLng = currentLatLng!!
                        )
                    }
                    locationError != null -> {
                        Text(locationError!!, modifier = Modifier.align(Alignment.Center))
                    }
                    else -> {
                        Text("현재 위치를 가져오는 중입니다...", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            locationPermissionsState.shouldShowRationale -> {
                Text("지도를 보려면 위치 권한이 필요합니다.", modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                LaunchedEffect(Unit) {
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
                Text("위치 권한을 요청합니다...", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}