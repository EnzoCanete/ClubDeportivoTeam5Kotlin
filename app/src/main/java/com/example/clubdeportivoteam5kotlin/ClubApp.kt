package com.example.clubdeportivoteam5kotlin

import android.app.Application

class ClubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SocioRepository.init(this)
    }
}
