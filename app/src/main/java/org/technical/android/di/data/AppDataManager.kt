package org.technical.android.di.data

import android.content.Context
import org.technical.android.di.data.database.DatabaseManager
import org.technical.android.di.data.network.NetworkManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager
@Inject
constructor(
    override val context: Context,
    override val networkManager: NetworkManager,
    override val databaseManager: DatabaseManager
) : DataManager


