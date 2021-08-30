package club.infolab.itmo_lock.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomAccessKey(
    @SerialName("token")
    val roomKey: String
)
