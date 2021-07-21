package com.example.pixabayapp

import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.service.PixabayService
import com.example.pixabayapp.ui.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}

@Module
object AppModule {

    @Provides
    fun providePixabayService(): PixabayService {
        return PixabayService.instance
    }
}