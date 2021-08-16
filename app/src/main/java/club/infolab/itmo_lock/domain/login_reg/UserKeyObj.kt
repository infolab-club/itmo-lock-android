package club.infolab.itmo_lock.domain.login_reg

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserKeyObj(
    @SerializedName("token")
    @Expose
    val userToken: String
)