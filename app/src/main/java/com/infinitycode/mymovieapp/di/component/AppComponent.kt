package com.infinitumcode.mymovieapp.di.component

import com.infinitumcode.mymovieapp.App
import com.infinitumcode.mymovieapp.di.models.ActivityModule
import com.infinitumcode.mymovieapp.di.models.AppModule
import com.infinitumcode.mymovieapp.di.models.NetModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    NetModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder?

        fun build(): AppComponent
    }

    override fun inject(application: App)

}


