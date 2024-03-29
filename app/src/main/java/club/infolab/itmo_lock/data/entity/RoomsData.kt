package club.infolab.itmo_lock.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RoomsData(
    @SerialName("locks")
    val listRooms: List<Room>
)
