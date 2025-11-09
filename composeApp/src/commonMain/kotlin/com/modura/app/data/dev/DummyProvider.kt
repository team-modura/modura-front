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
        PlaceInfo(1, "아르떼뮤지엄 강릉", "강원 강릉시 난설헌로 131", 150, 4.7, 8912, true, "arte_museum_gangneung.jpg", listOf("눈물의 여왕", "사랑의 불시착")),
        PlaceInfo(2, "주문진 해변", "강원 강릉시 주문진읍 해안로 1609", 550, 4.5, 5231, false, "jumunjin_beach.jpg", listOf("도깨비")),
        PlaceInfo(3, "낙산사", "강원 양양군 강현면 낙산사로 100", 1200, 4.8, 10588, true, "naksansa_temple.jpg", emptyList()),
        PlaceInfo(4, "이음이네 슈퍼", "인천 미추홀구 석정로 220", 25000, 4.9, 2024, false, null, listOf("선재 업고 튀어"))
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
    val address = listOf(
        States("서울특별시", listOf(City("강남구"), City("강동구"), City("강북구"), City("강서구"), City("관악구"), City("광진구"), City("구로구"), City("금천구"), City("노원구"), City("도봉구"), City("동대문구"), City("동작구"), City("마포구"), City("서대문구"), City("서초구"), City("성동구"), City("성북구"), City("송파구"), City("양천구"), City("영등포구"), City("용산구"), City("은평구"), City("종로구"), City("중구"), City("중랑구"))),
        States("경기도", listOf(City("가평군"), City("고양시"), City("과천시"), City("광명시"), City("광주시"), City("구리시"), City("군포시"), City("김포시"), City("남양주시"), City("동두천시"), City("부천시"), City("성남시"), City("수원시"), City("시흥시"), City("안산시"), City("안성시"), City("안양시"), City("양주시"), City("양평군"), City("여주시"), City("연천군"), City("오산시"), City("용인시"), City("의왕시"), City("의정부시"), City("이천시"), City("파주시"), City("평택시"), City("포천시"), City("하남시"), City("화성시"))),
        States("인천광역시", listOf(City("강화군"), City("계양구"), City("남동구"), City("동구"), City("미추홀구"), City("부평구"), City("서구"), City("연수구"), City("옹진군"), City("중구"))),
        States("강원특별자치도", listOf(City("강릉시"), City("고성군"), City("동해시"), City("삼척시"), City("속초시"), City("양구군"), City("양양군"), City("영월군"), City("원주시"), City("인제군"), City("정선군"), City("철원군"), City("춘천시"), City("태백시"), City("평창군"), City("홍천군"), City("화천군"), City("횡성군"))),
        States("충청북도", listOf(City("괴산군"), City("단양군"), City("보은군"), City("영동군"), City("옥천군"), City("음성군"), City("제천시"), City("증평군"), City("진천군"), City("청주시"), City("충주시"))),
        States("충청남도", listOf(City("계룡시"), City("공주시"), City("금산군"), City("논산시"), City("당진시"), City("보령시"), City("부여군"), City("서산시"), City("서천군"), City("아산시"), City("예산군"), City("천안시"), City("청양군"), City("태안군"), City("홍성군"))),
        States("대전광역시", listOf(City("대덕구"), City("동구"), City("서구"), City("유성구"), City("중구"))),
        States("세종특별자치시", listOf(City("세종시"))),
        States("전북특별자치도", listOf(City("고창군"), City("군산시"), City("김제시"), City("남원시"), City("무주군"), City("부안군"), City("순창군"), City("완주군"), City("익산시"), City("임실군"), City("장수군"), City("전주시"), City("정읍시"), City("진안군"))),
        States("전라남도", listOf(City("강진군"), City("고흥군"), City("곡성군"), City("광양시"), City("구례군"), City("나주시"), City("담양군"), City("목포시"), City("무안군"), City("보성군"), City("순천시"), City("신안군"), City("여수시"), City("영광군"), City("영암군"), City("완도군"), City("장성군"), City("장흥군"), City("진도군"), City("함평군"), City("해남군"), City("화순군"))),
        States("광주광역시", listOf(City("광산구"), City("남구"), City("동구"), City("북구"), City("서구"))),
        States("경상북도", listOf(City("경산시"), City("경주시"), City("고령군"), City("구미시"), City("군위군"), City("김천시"), City("문경시"), City("봉화군"), City("상주시"), City("성주군"), City("안동시"), City("영덕군"), City("영양군"), City("영주시"), City("영천시"), City("예천군"), City("울릉군"), City("울진군"), City("의성군"), City("청도군"), City("청송군"), City("칠곡군"), City("포항시"))),
        States("경상남도", listOf(City("거제시"), City("거창군"), City("고성군"), City("김해시"), City("남해군"), City("밀양시"), City("사천시"), City("산청군"), City("양산시"), City("의령군"), City("진주시"), City("창녕군"), City("창원시"), City("통영시"), City("하동군"), City("함안군"), City("함양군"), City("합천군"))),
        States("대구광역시", listOf(City("남구"), City("달서구"), City("달성군"), City("동구"), City("북구"), City("서구"), City("수성구"), City("중구"))),
        States("울산광역시", listOf(City("남구"), City("동구"), City("북구"), City("울주군"), City("중구"))),
        States("부산광역시", listOf(City("강서구"), City("금정구"), City("기장군"), City("남구"), City("동구"), City("동래구"), City("부산진구"), City("북구"), City("사상구"), City("사하구"), City("서구"), City("수영구"), City("연제구"), City("영도구"), City("중구"), City("해운대구"))),
        States("제주특별자치도", listOf(City("서귀포시"), City("제주시")))
    )
    val categories = listOf(
        Category(878,"Science Fiction","SF","👽"),
        Category(10770,"TV Movie", "TV 영화","📺"),
        Category(10751,"Family", "가족","👨‍👩‍👧‍👦"),
        Category(27, "Horror","공포","👻"),
        Category(99,"Documentary", "다큐멘터리","🌍"),
        Category(18, "Drama","드라마","🎭"),
        Category(10749,"Romance", "로맨스","❤️"),
        Category(12,"Adventure","모험","🗺️"),
        Category(9648,"Mystery","미스터리","🕵️"),
        Category(80,"Crime", "범죄","🚓"),
        Category(37,"Western", "서부","🤠"),
        Category(53,"Thriller", "스릴러","🔪"),
        Category(16, "Animation","애니메이션","🎨"),
        Category(28, "Action","액션","💥"),
        Category(36, "History","역사","🏛️"),
        Category(10402,"Music","음악","🎵"),
        Category(10752,"War", "전쟁","⚔️"),
        Category(35, "Comedy","코미디","😂"),
        Category(14, "Fantasy","판타지","🧙")
    )

    val stillcut = listOf(
        SceneInfo("광안리 해변의 일출", "https://picsum.photos/seed/1/800/600"),
        SceneInfo("감천 문화마을의 야경", "https://picsum.photos/seed/2/800/600"),
        SceneInfo("해운대 동백섬 산책로", "https://picsum.photos/seed/3/800/600"),
        SceneInfo("보수동 책방골목의 오후", "https://picsum.photos/seed/4/800/600"),
        SceneInfo("을숙도 생태공원", "https://picsum.photos/seed/5/800/600")
    )
}