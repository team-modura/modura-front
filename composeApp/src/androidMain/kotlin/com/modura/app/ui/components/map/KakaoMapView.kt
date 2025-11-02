package com.modura.app.ui.components.map

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView

@Composable
actual fun KakaoMapView(
    modifier: Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val mapView = MapView(context)

            mapView.start(object : MapLifeCycleCallback() {
                override fun onMapDestroy() {

                }

                override fun onMapError(error: Exception) {
                    Log.e("KakaoMap", "onMapError: ", error)
                }
            }, object : KakaoMapReadyCallback() {
                override fun getPosition(): LatLng {
                    return LatLng.from(37.5666805, 126.9784147)
                }

                override fun onMapReady(kakaoMap: KakaoMap) {
                    Log.d("KakaoMap", "KakaoMap is ready!")
                }
            })

            mapView
        },
        update = { mapView ->
            // Composable이 리컴포지션될 때 MapView를 업데이트해야 하는 경우 사용
        }
    )
}