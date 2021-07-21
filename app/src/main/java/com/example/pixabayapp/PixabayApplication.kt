package com.example.pixabayapp

import android.app.Application
import android.content.Context

class PixabayApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent:AppComponent
get() = when (this) {
    is PixabayApplication -> appComponent
    else -> this.applicationContext.appComponent
}