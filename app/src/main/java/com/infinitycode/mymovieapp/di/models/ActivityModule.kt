package com.infinitumcode.mymovieapp.di.models


import com.infinitumcode.mymovieapp.view.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeHomeActivity(): HomeActivity
}