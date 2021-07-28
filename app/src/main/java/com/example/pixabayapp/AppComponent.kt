package com.example.pixabayapp

import com.example.pixabayapp.search.repository.SearchRepo
import com.example.pixabayapp.search.service.HeaderInterceptor
import com.example.pixabayapp.search.service.SearchService
import com.example.pixabayapp.ui.ImageFragment
import com.example.pixabayapp.ui.SearchFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(searchFragment: SearchFragment)
    fun inject2(imageFragment: ImageFragment)
}

@Module
object AppModule {

    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Provides
    fun provideSearchRepo(searchService: SearchService): SearchRepo {
        return SearchRepo(searchService)
    }
}

@Module
object NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun provideHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    @Provides
    fun provideClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}