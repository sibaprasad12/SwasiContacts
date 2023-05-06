package com.swasi.utility.app

import android.app.Application


/**
 * Created by Sibaprasad Mohanty on 12/02/2022.
 * Spm Limited
 * sp.dobest@gmail.com
 */

class SwasiUtilityApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        private var application: SwasiUtilityApp? = null
        fun getInstance(): SwasiUtilityApp? {
            if (application == null)
                application = SwasiUtilityApp()
            return application
        }
    }
}