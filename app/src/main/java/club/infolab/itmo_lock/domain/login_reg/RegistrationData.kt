package club.infolab.itmo_lock.domain.login_reg

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegistrationData(
    @SerializedName("email")
    @Expose(deserialize = false)
    val email: String,

    @SerializedName("name")
    @Expose(deserialize = false)
    val name: String,

    @SerializedName("surname")
    @Expose(deserialize = false)
    val surname: String,

    @SerializedName("password")
    @Expose(deserialize = false)
    val password: String
)
