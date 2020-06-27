package org.technical.android.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.technical.android.ui.activity.favorites.ActivityFavorites
import org.technical.android.ui.activity.favorites.di.ActivityFavoritesModule
import org.technical.android.ui.activity.main.ActivityMain
import org.technical.android.ui.activity.main.di.ActivityMainModule


@Module
abstract class ActivityInjectorsModule {


    @ContributesAndroidInjector(modules = [ActivityMainModule::class])
    abstract fun activityMainInjector(): ActivityMain

    @ContributesAndroidInjector(modules = [ActivityFavoritesModule::class])
    abstract fun activityFavoritesInjector(): ActivityFavorites

}