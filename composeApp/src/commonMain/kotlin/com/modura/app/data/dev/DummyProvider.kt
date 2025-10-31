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
            ott = listOf("disney", "coopang"),
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
}