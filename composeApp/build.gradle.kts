import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
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

                //상태관리
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.datastore.preferences.core)

                api(libs.okio)

                implementation(libs.kotlinx.datetime)

                //이미지 로딩 라이브러리 Coil
                implementation(libs.coil.compose)
                //implementation(libs.coil.network.ktor)
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
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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

