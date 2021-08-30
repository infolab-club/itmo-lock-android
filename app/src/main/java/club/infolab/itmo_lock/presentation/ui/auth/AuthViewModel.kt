package club.infolab.itmo_lock.presentation.ui.auth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.data.entity.KeyObj
import club.infolab.itmo_lock.data.entity.LoginData
import club.infolab.itmo_lock.data.entity.RegistrationData
import club.infolab.itmo_lock.data.repository.AuthRepository
import com.tinkoffsirius.koshelok.repository.shared.AccountShared
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val accountShared: AccountShared
) : ViewModel() {
    val loginStatus: MutableLiveData<LoadStatus?> = MutableLiveData()
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun loginLastData() {
        loginStatus.postValue(LoadStatus.Loading)
        disposable += getAccount()
            .flatMapCompletable { loginRequest(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    loginStatus.postValue(LoadStatus.Success())
                },
                onError = {
                    loginStatus.postValue(LoadStatus.InputWaiting)
                    Log.e(TAG, "loginLastData: ${it.message}")
                }
            )
    }

    fun login(email: String, password: List<Char>) {
        val data = LoginData(email = email, password = password.joinToString("", "", ""))
        disposable += loginRequest(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    loginStatus.postValue(LoadStatus.Success())
                },
                onError = { loginStatus.postValue(LoadStatus.Error(it)) }
            )
    }

    private fun loginRequest(data: LoginData) =
        authRepository.login(data)
            .flatMap { keyObj ->
                KeyObj.token = keyObj.userToken
                authRepository.getUserInfo(keyObj.userToken)
            }
            .flatMapCompletable {
                accountShared.saveUserInfo(it)
            }
            .andThen(accountShared.saveLoginData(data))

    private fun getAccount(): Single<LoginData> {
        return accountShared.getLoginData()
    }

    fun register(email: String, name: String, surname: String, password: List<Char>) {
        val data = RegistrationData(
            email = email,
            name = name,
            surname = surname,
            password = password.joinToString("", "", "")
        )
        disposable += authRepository.register(data)
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

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
