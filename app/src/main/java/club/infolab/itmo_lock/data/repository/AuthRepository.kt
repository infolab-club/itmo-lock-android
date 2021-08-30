package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.LoginData
import club.infolab.itmo_lock.data.entity.RegistrationData
import club.infolab.itmo_lock.data.entity.UserInfo
import club.infolab.itmo_lock.data.entity.UserKeyObj
import io.reactivex.rxjava3.core.Single

interface AuthRepository {

    fun login(loginData: LoginData): Single<UserKeyObj>

    fun register(registrationData: RegistrationData): Single<UserKeyObj>

    fun getUserInfo(token: String): Single<UserInfo>
}