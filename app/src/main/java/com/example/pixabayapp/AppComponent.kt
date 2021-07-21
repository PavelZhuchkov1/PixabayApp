package com.example.pixabayapp

import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.service.PixabayService
import com.example.pixabayapp.ui.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}

@Module
object AppModule {

    @Provides
    fun providePixabayService(): PixabayService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pexels.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PixabayService::class.java)
    }
}