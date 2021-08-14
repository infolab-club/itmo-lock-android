package club.infolab.itmo_lock

import club.infolab.itmo_lock.data.repository.Repository
import club.infolab.itmo_lock.data.repository.RepositoryImpl
import club.infolab.itmo_lock.presentation.ui.lock.LockViewModel
import club.infolab.itmo_lock.presentation.ui.rooms.RoomsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }

    viewModel { LockViewModel() }
    viewModel { RoomsViewModel(get()) }
}
