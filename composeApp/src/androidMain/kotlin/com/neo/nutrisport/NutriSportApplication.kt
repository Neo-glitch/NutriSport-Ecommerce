package com.neo.nutrisport

import android.app.Application
import com.neo.di.initializeKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext

class NutriSportApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@NutriSportApplication)
            }
        )
        Firebase.initialize(this)
    }
}