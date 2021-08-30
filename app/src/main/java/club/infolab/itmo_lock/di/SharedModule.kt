package club.infolab.itmo_lock.di

import com.tinkoffsirius.koshelok.repository.shared.AccountShared
import com.tinkoffsirius.koshelok.repository.shared.SharedPreferencesFactory
import com.tinkoffsirius.koshelok.repository.shared.SharedPreferencesFactory.Companion.USER_DATA
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedModule = module {
    single { SharedPreferencesFactory().create(androidContext(), USER_DATA) }
    single { AccountShared(get()) }
}