package club.infolab.itmo_lock.presentation.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.data.entity.KeyObj
import club.infolab.itmo_lock.data.entity.LoginData
import club.infolab.itmo_lock.data.entity.RegistrationData
import club.infolab.itmo_lock.data.repository.AuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val loginStatus: MutableLiveData<LoadStatus?> = MutableLiveData()

    fun login(email: String, password: List<Char>) {
        val data = LoginData(email = email, password = password.joinToString("", "", ""))
        authRepository.login(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    Log.d("LOGIN", "login: ${it.userToken}")
                    KeyObj.token = it.userToken
                    loginStatus.postValue(LoadStatus.Success(it))
                },
                onError = { loginStatus.postValue(LoadStatus.Error(it)) }
            )
    }

    fun register(email: String, name: String, surname: String, password: List<Char>) {
        val data = RegistrationData(
            email = email,
            name = name,
            surname = surname,
            password = password.joinToString("", "", "")
        )
        authRepository.register(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    Log.d("LOGIN", "login: ${it.userToken}")
                    KeyObj.token = it.userToken
                    loginStatus.postValue(LoadStatus.Success(it))
                },
                onError = { loginStatus.postValue(LoadStatus.Error(it)) }
            )
    }
}
