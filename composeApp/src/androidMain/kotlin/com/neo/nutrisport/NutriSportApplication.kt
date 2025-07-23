package com.neo.nutrisport

import android.app.Application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class NutriSportApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
    }
}