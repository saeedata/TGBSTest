package org.technical.android.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import org.technical.android.di.data.AppDataManager
import org.technical.android.di.data.DataManager
import org.technical.android.di.data.database.DatabaseModule
import org.technical.android.di.data.network.NetworkModule
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class
    ]
)
open class AppModule {

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

}