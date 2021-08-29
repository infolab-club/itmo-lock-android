package club.infolab.itmo_lock.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginData(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String
)