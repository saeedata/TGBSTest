package org.technical.android.di.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import org.technical.android.di.data.database.dao.FeedsDao
import org.technical.android.entity.database.Feed

@Database(
    entities = [Feed::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun feedsDao(): FeedsDao

}