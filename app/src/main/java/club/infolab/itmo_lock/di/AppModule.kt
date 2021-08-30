package club.infolab.itmo_lock

import club.infolab.itmo_lock.data.repository.*
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
    single<InfoRoomRepository> { InfoRoomRepositoryImpl() }

    viewModel { LockViewModel() }
    viewModel { RoomsViewModel(get(RoomsRepository::class.java)) }
    viewModel {
        AuthViewModel(
            get(AuthRepository::class.java),
            get(AccountSharedRepository::class.java)
        )
    }
    viewModel { MembersViewModel() }
}
