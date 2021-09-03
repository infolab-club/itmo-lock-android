package club.infolab.itmo_lock.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLocks(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("locks")
    val locks: List<Long>
)
