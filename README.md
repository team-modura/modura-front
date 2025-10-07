# 🛠️ 기술 스택 및 프로젝트 구성

## 1. 기술 스택 (Tech Stack)

본 프로젝트는 코드 공유 및 성능 최적화를 위해 **Kotlin Multiplatform**을 기반으로 구축되었습니다.

| 구분 | 기술/도구 | 역할 |
| :--- | :--- | :--- |
| **Multiplatform Core** | **Kotlin Multiplatform (KMP)** | Android 및 iOS 플랫폼 간 비즈니스 로직 공유 |
| **Android UI** | Jetpack Compose | Android 애플리케이션의 네이티브 UI 구성 |
| **Networking** | **Ktor Client** | 서버 통신 및 Naver/Kakao Local REST API 호출 |
| **Data Processing** | **`kotlinx.serialization`** | JSON 데이터의 안전하고 효율적인 직렬화 및 역직렬화 |
| **Concurrency** | **Kotlin Coroutines & Flow** | 비동기 처리 및 데이터 스트림 기반의 상태 관리 |

---

## 2. 파일 구조 및 모듈 설명

### 2.1. 프로젝트 구조
```
composeApp/
├── androidMain/
│          └── kotlin/com/modura/app/
│                 ├── data/
│                 ├── platform/
│                 └── util/
├── commonMain/
│         ├── composeResources/ 
│         │       ├── drawable/
│         │       └── font/
│         └── kotlin/com/modura/app/
│                 ├── data/
│                 │        ├── datasource/
│                 │        ├── datasourceImpl/
│                 │        ├── dto/
│                 │        │       ├── request/
│                 │        │       └── response/
│                 │        ├── repositoryImpl/
│                 │        ├── service/
│                 ├── di/
│                 ├── domain/
│                 │        ├── model/
│                 │        │       ├── request/
│                 │        │       └── response/
│                 │        └── repository/
│                 ├── ui/
│                 │        ├── components/
│                 │        ├── navigation/
│                 │        ├── screens/                    
│                 │        └── theme/
│                 └── util/
│                            ├── extension/
│                            ├── platform/
│                            └── network/
└── iosMain/
           └── kotlin/com/modura/app/
                  ├── data/
                  ├── platform/
                  └── util/

```

### 2.2. 모듈별 상세 역할

| 경로 (Path) | 주요 역할 및 기능 |
| :--- | :--- |
| commonMain | 모든 플랫폼(Android, iOS)에서 공유되는 핵심 로직과 UI의 집합입니다.<br/>• data: Ktor를 이용한 네트워크 통신, 데이터 모델(DTO), Repository 구현 등 데이터 소스와 관련된 모든 로직을 포함합니다.<br/>• domain: 순수 비즈니스 로직, 데이터 모델, 그리고 데이터 계층과 UI 계층을 연결하는 Repository 인터페이스를 정의합니다.<br/>• ui: Jetpack Compose로 작성된 공통 UI 로직입니다. 재사용 가능한 컴포넌트(components), 화면 구성(screens), 내비게이션(navigation), 앱 테마(theme)를 관리합니다.<br/>• di: Koin 등을 사용한 의존성 주입 설정을 담당합니다.<br/>• util: 날짜 포맷팅, 확장 함수 등 프로젝트 전반에서 사용되는 공통 유틸리티 코드를 포함합니다. |
| androidMain | Android 플랫폼에만 특화된 코드를 작성하는 공간입니다.<br/>• platform: commonMain에 선언된 expect 기능을 Android 네이티브 API로 실제 구현(actual)하는 곳입니다. (예: 지도 SDK 연동, 알림, 센서 접근 등)<br/>• data: Android 고유의 데이터 저장소(Room, SharedPreferences, DataStore)를 사용해야 할 경우 이곳에 구현합니다.<br/>• util: Android Context가 필요한 유틸리티나 Android 전용 로직을 포함합니다. |
| iosMain | iOS 플랫폼에만 특화된 코드를 작성하는 공간입니다.<br/>• platform: commonMain의 expect 기능을 iOS 네이티브 프레임워크(UIKit, MapKit, CoreLocation 등)로 실제 구현(actual)하는 곳입니다.<br/>• data: iOS 고유의 데이터 저장소(CoreData, Keychain)를 사용해야 할 경우 이곳에 구현합니다.<br/>• util: iOS 전용 유틸리티나 로직을 포함합니다. |
| composeResources | 모든 플랫폼에서 공통으로 사용하는 리소스 폴더입니다.<br/>• drawable: 아이콘, 로고 등 이미지 파일을 저장합니다.<br/>• font: 앱에서 사용할 커스텀 폰트 파일을 관리합니다. | | (프로젝트 루트)/iosApp | iOS 앱을 빌드하고 실행하기 위한 Xcode 프로젝트입니다.<br/>• iOS 앱의 진입점(AppDelegate, iOSApp.swift) 역할을 하며, commonMain에 작성된 공통 UI를 화면에 띄우는 초기화 코드를 포함합니다.<br/>• 앱 아이콘, Info.plist 설정, 코드 서명 등 iOS 배포에 필요한 모든 설정을 관리합니다. |
| (프로젝트 루트)/androidApp | Android 앱을 빌드하고 실행하기 위한 Android 애플리케이션 모듈입니다.<br/>• Android 앱의 진입점(Activity) 역할을 하며, commonMain의 공통 UI를 로드합니다.<br/>• AndroidManifest.xml, 앱 테마, 권한 설정 등 Android 앱 실행에 필요한 모든 설정을 포함합니다. |
