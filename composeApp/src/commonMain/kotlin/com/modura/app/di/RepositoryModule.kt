package com.modura.app.di

import com.modura.app.data.repositoryImpl.*
import com.modura.app.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<ListRepository> { ListRepositoryImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<YoutubeRepository> { YoutubeRepositoryImpl(get()) }
}