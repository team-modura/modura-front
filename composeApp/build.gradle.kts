import com.android.build.gradle.internal.tasks.AarMetadataReader.Companion.load
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.kakao.map)
                implementation("com.kakao.sdk:v2-user:2.19.0")
                implementation(libs.maps.compose)
                implementation(libs.maps.compose.utils)
                implementation(libs.google.play.services.location)
                implementation(libs.accompanist.permissions)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.coil.network)
                implementation(libs.ktor.client.android)
                implementation(libs.androidx.datastore.preferences.core)
                implementation(libs.multiplatform.settings.datastore)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.screenModel)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.tabNavigator)
                implementation(libs.voyager.koin)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.datastore.preferences.core)
                api(libs.okio)
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.voyager.koin)
                implementation(libs.slf4j.simple)
                implementation(compose.components.resources)
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
                implementation("com.russhwolf:multiplatform-settings-serialization:1.1.1")
                implementation("dev.icerock.moko:resources-compose:0.25.1")
                implementation("dev.icerock.moko:media-compose:0.11.1")
                implementation(libs.coil.compose)
                implementation(libs.coil.core)
                implementation(libs.multiplatform.settings)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val iosMain by creating {
            dependencies {
                implementation(libs.multiplatform.settings.nsuserdefaults)
            }
        }

        val iosArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.modura.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(FileInputStream(localPropertiesFile))
    }

    val debugBaseUrl = properties.getProperty("BASE_URL") ?: "LOCAL_PROPERTIES_에서_못찾음"
    println(">>> build.gradle.kts: BASE_URL from local.properties = '$debugBaseUrl'")

    // 3. composeResources 경로 설정 추가
    sourceSets["main"].apply {
        resources.srcDirs("src/commonMain/composeResources")
    }
    defaultConfig {
        applicationId = "com.modura.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        buildFeatures {
            buildConfig = true
        }
        resValue(
            "string",
            "kakao_native_app_key",
            "\"${properties.getProperty("KAKAO_NATIVE_APP_KEY") ?: ""}\""
        )
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] =
            properties.getProperty("KAKAO_NATIVE_APP_KEY") ?: ""
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        debug {
            buildConfigField(
                "String",
                "YOUTUBE_API_KEY",
                "\"${properties.getProperty("YOUTUBE_API_KEY") ?: ""}\""
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${properties.getProperty("BASE_URL") ?: ""}\""
            )
        }
        release {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "YOUTUBE_API_KEY",
                "\"${properties.getProperty("YOUTUBE_API_KEY") ?: ""}\""
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${properties.getProperty("BASE_URL") ?: ""}\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
/*cocoapods {
    version = "1.15.2"
    summary = "Compose application for multiplatform"
    homepage = "Link to the Shared Module homepage"
    license = "Shared Module License"
    framework {
        baseName = "ComposeApp"
        isStatic = true
    }
    // iOS용 Kakao SDK pod 추가
    pod("KakaoSDKUser") {
        version = "~> 2.19.0" // 안드로이드와 버전 맞추기
    }

    // pod install 스크립트 추가
    // Xcode 프로젝트를 열기 전에 터미널에서 ./gradlew podInstall 을 실행해야 합니다.
    // pod install을 실행해야 cocoapods에서 내려받은 라이브러리를 xcode 프로젝트에서 사용할 수 있습니다.
    // `iosApp` 폴더의 `Podfile`에 `pod 'KakaoSDKUser', '~> 2.19.0'`이 추가됩니다.
}*/
