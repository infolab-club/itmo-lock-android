package club.infolab.itmo_lock.data.network

import club.infolab.itmo_lock.data.entity.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ItmoLockService {
    @POST("/v1/auth/login")
    fun login(@Body data: LoginData): Single<UserKeyObj>

    @POST("/v1/auth/registration")
    fun register(@Body data: RegistrationData): Single<UserKeyObj>

    @GET("/v1/users/info")
    fun getUserInfo(@Header("authorization") token: String): Single<UserInfo>

    @GET("/v1/locks")
    fun getAccessibleRooms(@Header("authorization") token: String): Single<RoomsData>

    @GET("/v1/locks/{id}/token")
    fun getRoomUnlockToken(
        @Path("id") idRoom: Long,
        @Header("authorization") userToken: String
    ): Single<String>

    @GET("/v1/locks/{id}")
    fun getRoomAccess(
        @Path("id") idRoom: Long,
        @Header("authorization") userToken: String
    ): Single<RoomAccessKey>

    @GET("/v1/users")
    fun getUsers(@Header("authorization") token: String): Single<Users>

    @POST("/v1/locks/{id}/add_user")
    fun addUser(
        @Path("id") idRoom: Long,
        @Body idUser: IdUserBody,
        @Header("authorization") token: String
    ): Completable

    @POST("/v1/locks/{id}/remove_user")
    fun removeUser(
        @Path("id") idRoom: Long,
        @Body idUser: IdUserBody,
        @Header("authorization") token: String
    ): Completable
}
