package com.modura.app.ui.components.map

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.kakao.vectormap.MapView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.LatLng as KakaoLatLng


@Composable
fun KakaoMapViewInternal(modifier: Modifier, kakaoLatLng: KakaoLatLng) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val mapView = remember { MapView(context) }

    DisposableEffect(lifecycleOwner, mapView) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.resume()
                Lifecycle.Event.ON_PAUSE -> mapView.pause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    AndroidView(factory = { mapView }, modifier = modifier) { view ->
        view.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {}
            override fun onMapError(error: Exception) { Log.e("KakaoMap", "onMapError: ", error) }
        }, object : KakaoMapReadyCallback() {
            override fun getPosition(): KakaoLatLng = kakaoLatLng
            override fun onMapReady(kakaoMap: KakaoMap) {
                Log.d("KakaoMap", "KakaoMap is ready at $kakaoLatLng")
            }
        })
    }
}