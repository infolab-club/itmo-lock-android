package club.infolab.itmo_lock.data.network

import club.infolab.itmo_lock.data.entity.LoginData
import club.infolab.itmo_lock.data.entity.RegistrationData
import club.infolab.itmo_lock.data.entity.RoomsData
import club.infolab.itmo_lock.data.entity.UserKeyObj
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ItmoLockService {
    @POST("/v1/auth/login")
    fun login(@Body data: LoginData): Single<UserKeyObj>

    @POST("/v1/auth/registration")
    fun register(@Body data: RegistrationData): Single<UserKeyObj>

    @GET("/v1/locks")
    fun getAccessibleRooms(@Header("authorization") token: String): Single<RoomsData>
}
