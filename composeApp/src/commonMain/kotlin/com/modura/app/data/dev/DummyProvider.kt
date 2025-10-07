package com.modura.app.data.dev

import com.modura.app.data.dto.response.list.AnnouncementResponseDto

object DummyProvider {

    val dummyAnnouncements = listOf(
        AnnouncementResponseDto(
            id = 1,
            region = "서울",
            subsidy = 1000000,
            eligibility = "청년",
            startDate = "2024-01-01",
            endDate = "2024-12-31",
            criteria = "소득 기준 충족",
            frequency = "매월",
            application = "온라인 신청",
            benefitType = "현금",
            contact = "02-123-4567",
            website = "https://seoul.go.kr",
            description = "서울시 청년 월세 지원 사업입니다. 주거비 부담을 덜어드립니다.",
            thumbnail = "https://example.com/thumbnail1.jpg"
        ),
        AnnouncementResponseDto(
            id = 2,
            region = "부산",
            subsidy = 500000,
            eligibility = "신혼부부",
            startDate = "2024-03-01",
            endDate = "2024-08-31",
            criteria = "무주택자",
            frequency = "분기별",
            application = "방문 신청",
            benefitType = "바우처",
            contact = "051-987-6543",
            website = "https://busan.go.kr",
            description = "부산시 신혼부부 주택 임차보증금 이자 지원 안내입니다.",
            thumbnail = "https://example.com/thumbnail2.jpg"
        ),
        AnnouncementResponseDto(
            id = 3,
            region = "경기",
            subsidy = 300000,
            eligibility = "대학생",
            startDate = "2024-09-01",
            endDate = "2024-11-30",
            criteria = "기숙사 미입주자",
            frequency = "1회",
            application = "학교 통해 신청",
            benefitType = "현금",
            contact = "031-111-2222",
            website = "https://gg.go.kr",
            description = "경기도 대학생 교통비 지원 사업. 통학 부담을 줄여보세요.",
            thumbnail = "https://example.com/thumbnail3.jpg"
        )
    )

    val dummyAnnouncementDetail = dummyAnnouncements[0]
}