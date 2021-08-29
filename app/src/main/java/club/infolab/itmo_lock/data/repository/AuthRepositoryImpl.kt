package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.LoginData
import club.infolab.itmo_lock.data.entity.RegistrationData
import club.infolab.itmo_lock.data.entity.UserKeyObj
import club.infolab.itmo_lock.data.network.ItmoLockService
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.get

class AuthRepositoryImpl : AuthRepository {

    private val itmoLockService: ItmoLockService = get(ItmoLockService::class.java)

    override fun login(loginData: LoginData): Single<UserKeyObj> {
        return itmoLockService.login(data = loginData)
    }

    override fun register(registrationData: RegistrationData): Single<UserKeyObj> {
        return itmoLockService.register(data = registrationData)
    }
}