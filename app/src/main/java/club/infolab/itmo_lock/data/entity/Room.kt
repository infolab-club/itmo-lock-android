package club.infolab.itmo_lock.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Room(
    @SerialName("id")
    var id: Int,
    @SerialName("number")
    var number: String,
    @SerialName("about")
    var description: String,
    @SerialName("preview")
    var picture: String
)
