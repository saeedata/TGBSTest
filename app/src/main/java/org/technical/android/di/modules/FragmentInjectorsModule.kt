package org.technical.android.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.technical.android.ui.fragment.feedDetails.FragmentFeedDetails
import org.technical.android.ui.fragment.feedDetails.di.FragmentFeedDetailsModule
import org.technical.android.ui.fragment.feedList.FragmentFeedList
import org.technical.android.ui.fragment.feedList.di.FragmentFeedListModule


@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector(modules = [FragmentFeedListModule::class])
    abstract fun fragmentFeedListInjector(): FragmentFeedList

    @ContributesAndroidInjector(modules = [FragmentFeedDetailsModule::class])
    abstract fun fragmentFeedDetailsInjector(): FragmentFeedDetails


}