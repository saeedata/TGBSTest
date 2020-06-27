package org.technical.android.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.technical.android.App
import org.technical.android.di.modules.ActivityInjectorsModule
import org.technical.android.di.modules.AppModule
import org.technical.android.di.modules.FragmentInjectorsModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class,
        AppModule::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(context: Context): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)

}

