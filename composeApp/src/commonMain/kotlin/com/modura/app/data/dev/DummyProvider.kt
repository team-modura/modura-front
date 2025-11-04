package com.modura.app.data.dev

import com.modura.app.data.dto.response.list.MediaResponseDto

object DummyProvider {

    val dummyMedia = listOf(
        MediaResponseDto(
            id = 1,
            rank = "1",
            title = "기묘한 이야기",
            bookmark = true,
            ott = listOf("netflix", "watcha"),
            image ="기묘한 이야기"
        ),
        MediaResponseDto(
            id = 2,
            rank = "2",
            title = "더 글로리",
            bookmark = false,
            ott = listOf("disney", "coupang"),
            image = "더 글로리"
        ),
        MediaResponseDto(
            id = 3,
            rank = "3",
            title = "카지노",
            bookmark = true,
            ott = listOf(),
            image = "카지노"
        ),
        MediaResponseDto(
            id = 4,
            rank = "4",
            title = "환승연애",
            bookmark = false,
            ott = listOf("wave", "tving"),
            image = "환승연애"
        )
    )
    val dummyMediaDetail = dummyMedia[0]

    val dummyPlaces = listOf(
        PlaceInfo(
            id = "p001",
            name = "아르떼뮤지엄 강릉",
            address = "강원 강릉시 난설헌로 131",
            distance = 150,
            rating = 4.7,
            reviewCount = 8912,
            bookmark = true,
            photoUrl = "arte_museum_gangneung.jpg", // 예시 이미지 파일 이름
            filmedContent = listOf("눈물의 여왕", "사랑의 불시착")
        ),
        PlaceInfo(
            id = "p002",
            name = "주문진 해변",
            address = "강원 강릉시 주문진읍 해안로 1609",
            distance = 550,
            rating = 4.5,
            reviewCount = 5231,
            bookmark = false,
            photoUrl = "jumunjin_beach.jpg",
            filmedContent = listOf("도깨비")
        ),
        PlaceInfo(
            id = "p003",
            name = "낙산사",
            address = "강원 양양군 강현면 낙산사로 100",
            distance = 1200, // 1.2km
            rating = 4.8,
            reviewCount = 10588,
            bookmark = true,
            photoUrl = "naksansa_temple.jpg",
            filmedContent = emptyList() // 촬영 정보가 없는 경우
        ),
        PlaceInfo(
            id = "p004",
            name = "이음이네 슈퍼",
            address = "인천 미추홀구 석정로 220",
            distance = 25000, // 25km
            rating = 4.9,
            reviewCount = 2024,
            bookmark = false,
            photoUrl = null, // 사진이 없는 경우
            filmedContent = listOf("선재 업고 튀어")
        )
    )
}