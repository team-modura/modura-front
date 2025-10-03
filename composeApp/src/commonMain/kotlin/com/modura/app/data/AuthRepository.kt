package com.modura.app.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

/**
* 앱 전역에서 사용될 DataStore 인스턴스를 생성하는 함수.
* 각 플랫폼(Android, iOS)에서 앱 시작 시 호출됩니다.
*/
fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )
}

/**
 * 앱의 DataStore 인스턴스를 보관하는 싱글톤 객체.
 * 초기화된 후에는 앱 어디서든 이 객체를 통해 DataStore에 접근할 수 있습니다.
 */
object AppDataStore {
    lateinit var dataStore: DataStore<Preferences>
}

/**
 * 인증(로그인) 관련 데이터를 처리하는 클래스(Repository).
 */
class AuthRepository(private val dataStore: DataStore<Preferences>) {

    // DataStore에 데이터를 저장할 때 사용할 키
    private val authTokenKey = stringPreferencesKey("auth_token")

    /**
     * 저장된 인증 토큰을 Flow 형태로 제공합니다.
     * 이 Flow를 구독하고 있으면 토큰 값이 변경될 때마다 실시간으로 알 수 있습니다.
     */
    val authTokenStream: Flow<String?> = dataStore.data.map { preferences ->
        preferences[authTokenKey]
    }

    /**
     * 로그인 성공 시 인증 토큰을 DataStore에 저장합니다.
     */
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[authTokenKey] = token
        }
    }

    /**
     * 로그아웃 시 저장된 인증 토큰을 삭제합니다.
     */
    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(authTokenKey)
        }
    }
}