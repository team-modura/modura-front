package com.modura.app.di

import com.modura.app.data.datasource.ListDataSource
import com.modura.app.data.datasource.LoginDataSource
import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.data.datasource.MapDataSource
import com.modura.app.data.datasource.MypageDataSource
import com.modura.app.data.datasource.SearchDataSource
import com.modura.app.data.datasourceImpl.ListDataSourceImpl
import com.modura.app.data.datasourceImpl.LoginDataSourceImpl
import com.modura.app.data.datasourceImpl.DetailDataSourceImpl
import com.modura.app.data.datasourceImpl.MapDataSourceImpl
import com.modura.app.data.datasourceImpl.MypageDataSourceImpl
import com.modura.app.data.datasourceImpl.SearchDataSourceImpl
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto
import com.modura.app.data.service.YoutubeService
import com.modura.app.data.service.YoutubeServiceImpl
import com.modura.app.util.platform.getYoutubeApiKey
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {
    single<YoutubeService> { YoutubeServiceImpl( get(named(NetworkQualifiers.YOUTUBE_HTTP_CLIENT))) }
    single<LoginDataSource> { LoginDataSourceImpl(get(named(NetworkQualifiers.MODURA_HTTP_CLIENT))) }
    single<ListDataSource> { ListDataSourceImpl(get(named(NetworkQualifiers.MODURA_HTTP_CLIENT))) }
    single<DetailDataSource> { DetailDataSourceImpl(get(named(NetworkQualifiers.MODURA_HTTP_CLIENT)), get() )}
    single<SearchDataSource> { SearchDataSourceImpl(get(named(NetworkQualifiers.MODURA_HTTP_CLIENT))) }
    single<MypageDataSource> { MypageDataSourceImpl(get(named(NetworkQualifiers.MODURA_HTTP_CLIENT))) }
    single<MapDataSource>{ MapDataSourceImpl(get(named(NetworkQualifiers.MODURA_HTTP_CLIENT))) }
}