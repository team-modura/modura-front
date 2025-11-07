package com.modura.app.data.dev

import com.modura.app.data.dto.response.list.MediaResponseDto
import modura.composeapp.generated.resources.*

object DummyProvider {

    val dummyMedia = listOf(
        MediaResponseDto(id = 1, rank = "1", title = "기묘한 이야기", bookmark = true, ott = listOf("netflix", "watcha"), image ="기묘한 이야기"),
        MediaResponseDto(id = 2, rank = "2", title = "더 글로리", bookmark = false, ott = listOf("disney", "coupang"), image = "더 글로리"),
        MediaResponseDto(id = 3, rank = "3", title = "카지노", bookmark = true, ott = listOf(), image = "카지노"),
        MediaResponseDto(id = 4, rank = "4", title = "환승연애", bookmark = false, ott = listOf("wave", "tving"), image = "환승연애"),
        MediaResponseDto(5, "5", "선재 업고 튀어", true, listOf("tving"), "선재 업고 튀어"),
        MediaResponseDto(6, "6", "눈물의 여왕", false, listOf("netflix", "tving"), "눈물의 여왕"),
        MediaResponseDto(7, "7", "무빙", true, listOf("disney"), "무빙"),
        MediaResponseDto(8, "8", "살인자ㅇ난감", false, listOf("netflix"), "살인자ㅇ난감"),
        MediaResponseDto(9, "9", "최애의 아이", true, listOf("netflix"), "최애의 아이"),
        MediaResponseDto(10, "10", "주술회전", false, listOf("netflix", "tving", "watcha"), "주술회전"),
        MediaResponseDto(11, "11", "나는 솔로", true, listOf("netflix", "coupang"), "나는 솔로"),
        MediaResponseDto(12, "12", "크라임씬 리턴즈", false, listOf("tving"), "크라임씬 리턴즈"),
        MediaResponseDto(13, "13", "삼체", true, listOf("netflix"), "삼체"),
        MediaResponseDto(14, "14", "피라미드 게임", false, listOf("tving"), "피라미드 게임"),
        MediaResponseDto(15, "15", "파묘", true, listOf("coupang", "wave"), "파묘"),
        MediaResponseDto(16, "16", "범죄도시4", false, listOf("disney"), "범죄도시4"),
        MediaResponseDto(17, "17", "하이라키", true, listOf("netflix"), "하이라키"),
        MediaResponseDto(18, "18", "The 8 Show (더 에이트 쇼)", false, listOf("netflix"), "The 8 Show (더 에이트 쇼)"),
        MediaResponseDto(19, "19", "돌풍", true, listOf("netflix"), "돌풍"),
        MediaResponseDto(20, "20", "플레이어 2: 꾼들의 전쟁", false, listOf("tving", "disney"), "플레이어 2: 꾼들의 전쟁")

    )
    val dummyMediaDetail = dummyMedia[0]

    val dummyPlaces = listOf(
        PlaceInfo(
            id = 1,
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
            id = 2,
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
            id = 3,
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
            id = 4,
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
    val dummyStillCuts = listOf(
        Res.drawable.img_stillcut_example,
        Res.drawable.img_stillcut_example2,
        Res.drawable.img_stillcut_example3,
        Res.drawable.img_stillcut_example4,
        Res.drawable.img_stillcut_example5,
        Res.drawable.img_stillcut_example6,
        Res.drawable.img_stillcut_example7,
        Res.drawable.img_stillcut_example8,
        Res.drawable.img_stillcut_example9,
        Res.drawable.img_stillcut_example5,
        )
    val dummyReviews = listOf(
        MypageReview(1, "시리즈", "선재 업고 튀어", name="김모두", score=4.5f, date="2024.06.12", text="인생 최고의 드라마... 선재야..."),
        MypageReview(2, "장소", "수원 행궁", location="화성행궁", region="경기", name="박모두", score=5.0f, date="2024.06.11", text="너무 예쁘고 산책하기 좋아요!"),
        MypageReview(3, "영화", "파묘", name="최모두", score=4.0f, date="2024.06.10", text="한국 오컬트의 정점! 몰입감 최고."),
        MypageReview(4, "장소", "낙산공원", location="낙산공원", region="서울", name="이모두", score=3.5f, date="2024.06.09", text="야경이 정말 멋진 곳이지만 사람이 너무 많아요."),
        MypageReview(5, "시리즈", "눈물의 여왕", name="정모두", score=5.0f, date="2024.06.08", text="김수현, 김지원 배우의 연기력에 감탄하며 봤습니다."),
        MypageReview(6, "영화", "범죄도시4", name="강모두", score=3.0f, date="2024.06.07", text="역시 마동석! 시원한 액션이 일품입니다."),
    )
}