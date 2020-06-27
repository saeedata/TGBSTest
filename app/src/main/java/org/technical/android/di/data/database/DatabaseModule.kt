package org.technical.android.di.data.database


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): DatabaseManager {
        return Room.databaseBuilder(context, DatabaseManager::class.java, "app_db")
                .allowMainThreadQueries()
                .build()

    }
}
