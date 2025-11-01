import org.jetbrains.kotlin.gradle.dsl.JvmTarget
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
        // androidMain 소스셋 (기존과 동일)
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)

                implementation(libs.ktor.client.android)
            }
        }

        // commonMain 소스셋 (okio 라이브러리 추가)
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)

                implementation(libs.voyager.navigator)
                implementation(libs.voyager.screenModel)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.tabNavigator)
                implementation(libs.voyager.koin)

                //상태관리
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.datastore.preferences.core)

                api(libs.okio)

                implementation(libs.kotlinx.datetime)

                // Ktor (네트워크 통신)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)

                // Kotlinx Serialization
                implementation(libs.kotlinx.serialization.json)

                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.voyager.koin)
                implementation(libs.slf4j.simple)

                implementation(compose.components.resources)

                implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
                implementation("com.russhwolf:multiplatform-settings-serialization:1.1.1")

                //implementation(libs.coil.compose)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
               // implementation(libs.ktor.client.darwin)
                implementation("io.ktor:ktor-client-darwin:3.3.1")
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
    }
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")    // 파일을 읽을 수 있는 경우에만 내용을 로드하도록 함
    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        debug {
            buildConfigField("String", "YOUTUBE_API_KEY", "\"${localProperties.getProperty("YOUTUBE_API_KEY") ?: ""}\"")
        }
        release{
            isMinifyEnabled = false
            buildConfigField("String", "YOUTUBE_API_KEY", "\"${localProperties.getProperty("YOUTUBE_API_KEY") ?: ""}\"")
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

