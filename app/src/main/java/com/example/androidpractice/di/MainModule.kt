package com.example.androidpractice.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.MainViewModel
import com.example.androidpractice.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(viewModel: MainViewModel): ViewModel
}
