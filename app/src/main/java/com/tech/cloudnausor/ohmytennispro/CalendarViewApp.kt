package com.tech.cloudnausor.ohmytennispro

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen


class CalendarViewApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize ThreeTenABP library
        AndroidThreeTen.init(this)
    }

}