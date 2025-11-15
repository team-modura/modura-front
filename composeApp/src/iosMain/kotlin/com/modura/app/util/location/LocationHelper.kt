package com.modura.app.util.location

import platform.CoreLocation.CLLocationManager
import kotlin.coroutines.resume

actual class LocationHelper : LocationTracker {

    private val locationManager = CLLocationManager()

    override suspend fun getCurrentLocation(): Location? {
        if (!hasLocationPermission()) {
            println("### iOS 위치 권한이 없습니다! ###")
            // locationManager.requestWhenInUseAuthorization()
            return null
        }

        return suspendCancellableCoroutine { continuation ->
            val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    (didUpdateLocations.firstOrNull() as? CLLocation)?.let {
                        continuation.resume(Location(it.coordinate.latitude, it.coordinate.longitude))
                    } ?: continuation.resume(null)
                    manager.stopUpdatingLocation()
                }

                override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                    println("### iOS 위치 가져오기 실패: ${didFailWithError.localizedDescription} ###")
                    continuation.resume(null)
                    manager.stopUpdatingLocation()
                }
            }

            locationManager.delegate = delegate
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.startUpdatingLocation() // 위치 업데이트 시작

            continuation.onCancellation {
                locationManager.stopUpdatingLocation() // 코루틴이 취소되면 위치 업데이트 중단
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        // 앱의 위치 서비스 사용 가능 여부와 권한 상태를 확인합니다.
        return CLLocationManager.locationServicesEnabled() &&
                (CLLocationManager.authorizationStatus() == kCLAuthorizationStatusAuthorizedWhenInUse ||
                        CLLocationManager.authorizationStatus() == kCLAuthorizationStatusAuthorizedAlways)
    }
}