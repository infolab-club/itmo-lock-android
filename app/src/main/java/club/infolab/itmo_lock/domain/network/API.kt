package club.infolab.itmo_lock.domain.network

import club.infolab.itmo_lock.domain.login_reg.LoginData
import club.infolab.itmo_lock.domain.login_reg.RegistrationData
import club.infolab.itmo_lock.domain.login_reg.UserKeyObj
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("/v1/auth/login")
    suspend fun login(@Body data: LoginData): UserKeyObj

    @POST("/v1/auth/registration")
    suspend fun register(@Body data: RegistrationData): UserKeyObj
}
