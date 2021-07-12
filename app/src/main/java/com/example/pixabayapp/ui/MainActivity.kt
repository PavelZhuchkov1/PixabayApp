package com.example.pixabayapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pixabayapp.R
import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.service.PixabayService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pixabayService = PixabayService.instance
        val pixabayRepo = PixabayRepo(pixabayService)

        GlobalScope.launch {
            val results = pixabayRepo.searchByTerm("Yellow Flowers")
            Log.i(TAG, "Results = ${results.body()}")
        }
    }
}