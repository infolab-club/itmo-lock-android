package com.tinkoffsirius.koshelok.repository.shared

import android.content.SharedPreferences
import club.infolab.itmo_lock.data.entity.LoginData
import club.infolab.itmo_lock.data.entity.UserInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AccountShared(
    private val sharedPreferences: SharedPreferences
) {

    fun saveLoginData(loginData: LoginData): Completable = Completable.fromCallable {
        val data = Json.encodeToString(loginData)
        sharedPreferences.edit()
            .putString(USER_LOGIN, data)
            .apply()
    }

    fun getLoginData(): Single<LoginData> = Single.fromCallable {
        val data = sharedPreferences.getString(USER_LOGIN, null)
        if (data != null) {
            Json.decodeFromString(LoginData.serializer(), data)
        } else {
            LoginData("", "")
        }
    }

    fun saveUserInfo(userInfo: UserInfo): Completable = Completable.fromCallable {
        val data = Json.encodeToString(userInfo)
        sharedPreferences.edit()
            .putString(USER_INFO, data)
            .apply()
    }

    fun getUserInfo(): Single<UserInfo> = Single.fromCallable {
        val data = sharedPreferences.getString(USER_INFO, null)
        if (data != null) {
            Json.decodeFromString(UserInfo.serializer(), data)
        } else {
            UserInfo(-1, "", "", "", false)
        }
    }

    fun removeAllData(): Completable = Completable.fromCallable {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val USER_LOGIN = "user_login"
        const val USER_INFO = "user_info"
    }
}
