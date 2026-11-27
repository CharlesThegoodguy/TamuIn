package com.uts.tamuin166

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class TamuInApp166 : Application() {
    override fun onCreate() {
        super.onCreate()
        // Mengikuti pengaturan tema sistem HP (Mode Terang / Mode Gelap secara otomatis)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}
