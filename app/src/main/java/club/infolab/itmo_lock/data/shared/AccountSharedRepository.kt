package com.tinkoffsirius.koshelok.repository.shared

import android.content.SharedPreferences
import club.infolab.itmo_lock.data.entity.LoginData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AccountSharedRepository(
    private val sharedPreferences: SharedPreferences
) {

    fun saveLoginData(loginData: LoginData): Completable = Completable.fromCallable {
        val data = Json.encodeToString(loginData)
        sharedPreferences.edit()
            .putString(USER, data)
            .apply()
    }

    fun getLoginData(): Single<LoginData> = Single.fromCallable {
        val data = sharedPreferences.getString(USER, null)
        if (data != null) {
            Json.decodeFromString(LoginData.serializer(), data)
        } else {
            LoginData("", "")
        }
    }

    companion object {
        const val USER = "user_info"
    }
}
