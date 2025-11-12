package com.modura.app.util.platform


class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
actual val BASE_URL: String = " "  //Xcode 실행 후 Info.plist에 BaseURL 추가