package org.technical.android.di.data

import android.content.Context
import org.technical.android.di.data.database.DatabaseManager
import org.technical.android.di.data.network.NetworkManager


interface DataManager {

    val context: Context

    val networkManager: NetworkManager

    val databaseManager: DatabaseManager

}
