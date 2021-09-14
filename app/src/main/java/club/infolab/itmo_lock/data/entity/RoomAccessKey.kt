package club.infolab.itmo_lock.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomAccessKey(
    @SerialName("mac")
    val mac: String,
    @SerialName("token")
    val roomKey: String
)
