package club.infolab.itmo_lock

import club.infolab.itmo_lock.data.repository.AuthRepository
import club.infolab.itmo_lock.data.repository.AuthRepositoryImpl
import club.infolab.itmo_lock.data.repository.RoomsRepository
import club.infolab.itmo_lock.data.repository.RoomsRepositoryImpl
import club.infolab.itmo_lock.presentation.ui.auth.AuthViewModel
import club.infolab.itmo_lock.presentation.ui.lock.LockViewModel
import club.infolab.itmo_lock.presentation.ui.members.MembersViewModel
import club.infolab.itmo_lock.presentation.ui.rooms.RoomsViewModel
import com.tinkoffsirius.koshelok.repository.shared.AccountSharedRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<RoomsRepository> { RoomsRepositoryImpl() }
    single<AuthRepository> { AuthRepositoryImpl() }

    viewModel { LockViewModel() }
    viewModel { RoomsViewModel(get()) }
    viewModel { AuthViewModel(get(), get(AccountSharedRepository::class.java)) }
    viewModel { MembersViewModel() }
}