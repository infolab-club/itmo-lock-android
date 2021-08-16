package club.infolab.itmo_lock.domain.login_reg

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginData(
    @SerializedName("email")
    @Expose(deserialize = false)
    val email: String,

    @SerializedName("password")
    @Expose(deserialize = false)
    val password: String
)