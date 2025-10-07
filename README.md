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
│          ├── kotlin/com/modura/app/
│                 ├── data/
│                 ├── platform/
│                 └── util/
├── commonMain/
          ├── composeResources/ 
          │       ├── drawable/
          │       └── font/
          └── kotlin/com/modura/app/
                    ├── data/
                    │        ├── datasource/
                    │        ├── datasourceImpl/
                    │        ├── dto/
                    │        │       ├── request/
                    │        │       └── response/
                    │        ├── repositoryImpl/
                    │        ├── service/
                    ├── di/
                    ├── domain/
                    │        ├── model/
                    │        │       ├── request/
                    │        │       └── response/
                    │        └── repository/
                    ├── ui/
                    │        ├── compose/
                    │        └── screens/
                    ├── util/
                    │        ├── extension/
                    │        └── network/
                    └── res/
├── iosMain/
│          ├── kotlin/com/modura/app/
│                 ├── data/
│                 ├── platform/
│                 └── util/
└── build.gradle.kts/
```

### 2.2. 모듈별 상세 역할

| 모듈 이름 | 주요 역할 및 기능 |
| :--- | :--- |
| **`:shared`** | **핵심 비즈니스 로직을 포함합니다.** 복지 혜택을 추천하는 **AI 엔진** 로직, 모든 **데이터 모델 정의**, Ktor를 사용한 **REST API 통신 인터페이스**가 위치합니다. |
| **`commonMain`** | **플랫폼 독립적인 코드**를 작성하는 공간입니다. 복지 추천 알고리즘 및 Local API 호출 인터페이스 (`expect` 함수)가 포함됩니다. |
| **`androidMain` / `iosMain`** | 지도 출력 등 네이티브 기능이 필요할 때 `commonMain`의 `expect` 함수를 구현하는 **`actual` 코드**가 위치합니다. (각 플랫폼의 네이티브 SDK 사용) |
| **`:androidApp`** | `shared` 모듈을 종속성으로 가져와 **Jetpack Compose UI**를 구성하고 실행하는 Android 애플리케이션의 진입점입니다. (Windows 개발 환경의 주 타겟) |

### 2.3. 지도 연동 전략

| 구분 | 전략 | 역할 |
| :--- | :--- | :--- |
| **Common Code** | **Naver/Kakao Local REST API** | 복지 시설의 주소를 **GPS 좌표로 변환**하거나 시설 정보를 검색하는 데이터 처리 로직을 담당합니다. |
| **Native Code** | **Native SDK & `expect/actual`** | Common 코드에서 처리된 좌표 데이터를 받아 Android에서는 **Naver/Kakao Android SDK**를 통해 지도 화면에 시설 마커를 렌더링합니다. |