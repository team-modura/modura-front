package com.modura.app.di

import com.modura.app.ui.screens.detail.DetailScreenModel
import com.modura.app.ui.screens.home.HomeScreenModel
import com.modura.app.ui.screens.login.LoginScreenModel
import com.modura.app.ui.screens.map.MapScreenModel
import com.modura.app.ui.screens.mypage.MypageScreenModel
import com.modura.app.ui.screens.search.SearchScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val screenModelModule = module {
    factoryOf(::HomeScreenModel)
    factoryOf(::DetailScreenModel)
    factory{ MapScreenModel(get(), get())}
    factoryOf(::LoginScreenModel)
    factoryOf(::SearchScreenModel)
    factoryOf(::MypageScreenModel)
}