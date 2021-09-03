package club.infolab.itmo_lock.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class IdUserBody(
    @SerialName("id")
    val idUser: Long
)
