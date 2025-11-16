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
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.modura.app.R
import com.kakao.vectormap.LatLng as KakaoLatLng
import com.modura.app.data.dev.LatLng
import com.modura.app.domain.Location
import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.ui.screens.map.MapScreenModel
import org.koin.compose.koinInject

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
actual fun KakaoMapView(
    modifier: Modifier,
    places: List<PlaceResponseModel>,
    currentLocation: Location?,
    cameraEvent: MapScreenModel.CameraEvent?,
    onCameraEventConsumed: () -> Unit,
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    var kakaoMap by remember { mutableStateOf<KakaoMap?>(null) }
    var labelLayer by remember { mutableStateOf<LabelLayer?>(null) }

    var isMapInitialized by remember { mutableStateOf(false) }

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )

    Box(modifier = modifier) {
        if (locationPermissionsState.allPermissionsGranted) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LaunchedEffect(Unit) {
                locationPermissionsState.launchMultiplePermissionRequest()
            }
            Text("지도를 표시하려면 위치 권한이 필요합니다.", modifier = Modifier.align(Alignment.Center))
        }
    }
    LaunchedEffect(places, kakaoMap) {
        val map = kakaoMap ?: return@LaunchedEffect
        val layer = labelLayer ?: map.labelManager?.layer ?: return@LaunchedEffect

        layer.removeAll()

        places.forEach { place ->
            val position = KakaoLatLng.from(place.longitude, place.latitude)
            Log.d("pin", "정상 핀 위치: $position")
            val options = LabelOptions.from(position)
                .setStyles(LabelStyles.from(LabelStyle.from(R.drawable.img_pin)))
            layer.addLabel(options)
        }

        places.firstOrNull()?.let { firstPlace ->
            val position = KakaoLatLng.from(firstPlace.longitude, firstPlace.latitude)
            map.moveCamera(
                com.kakao.vectormap.camera.CameraUpdateFactory.newCenterPosition(position, 15)
            )
            Log.d("KakaoMapView", "핀 목록 업데이트됨. 첫 번째 핀 위치로 카메라 이동: $position")
        }
    }
    LaunchedEffect(cameraEvent, kakaoMap) {
        val map = kakaoMap ?: return@LaunchedEffect
        when (cameraEvent) {
            is MapScreenModel.CameraEvent.MoveTo -> {
                val position = KakaoLatLng.from(cameraEvent.lat, cameraEvent.lon)
                map.moveCamera(
                    com.kakao.vectormap.camera.CameraUpdateFactory.newCenterPosition(position, 15)
                )
                Log.d("KakaoMapView", "카메라 이벤트 수신: 내 위치로 이동 -> $position")
                onCameraEventConsumed() // 이벤트 소비 완료 알림
            }
            null -> {
                // 이벤트가 없으면 아무것도 하지 않음
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    mapView.start(object : MapLifeCycleCallback() {
                        override fun onMapDestroy() { }
                        override fun onMapError(error: Exception) { Log.e("KakaoMapView", "Map Error", error) }
                    }, object : KakaoMapReadyCallback() {
                        override fun onMapReady(map: KakaoMap) {

                            kakaoMap = map
                            labelLayer = map.labelManager?.layer
                            Log.d("KakaoMapView", "onMapReady: kakaoMap 상태 업데이트됨. 핀 찍기 준비 완료.")

                            if (isMapInitialized) return

                            currentLocation?.let {
                                val position = KakaoLatLng.from(it.latitude, it.longitude)
                                map.moveCamera(com.kakao.vectormap.camera.CameraUpdateFactory.newCenterPosition(position, 15))
                                Log.d("KakaoMapView", "지도 준비 및 초기 위치 이동 완료: $position")

                                isMapInitialized = true
                            }   
                        }
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