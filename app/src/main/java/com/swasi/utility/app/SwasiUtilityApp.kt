package com.swasi.utility.app

import android.app.Application
import android.content.IntentFilter
import android.provider.Telephony
import com.swasi.utility.ui.messages.MySmsBroadcastReceiver

/**
 * Created by Sibaprasad Mohanty on 12/02/2022.
 * Spm Limited
 * sp.dobest@gmail.com
 */

class SwasiUtilityApp : Application() {
    private lateinit var mySmsBroadcastReceiver: MySmsBroadcastReceiver
    override fun onCreate() {
        super.onCreate()
        mySmsBroadcastReceiver =
            MySmsBroadcastReceiver(/*BuildConfig.SERVICE_NUMBER, BuildConfig.SERVICE_CONDITION*/)
        registerReceiver(
            mySmsBroadcastReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
    }

    companion object {
        private var application: SwasiUtilityApp? = null
        fun getInstance(): SwasiUtilityApp? {
            if (application == null)
                application = SwasiUtilityApp()
            return application
        }
    }

    fun setListener(listener: MySmsBroadcastReceiver.SmsListener) {
        mySmsBroadcastReceiver.setListener(listener)
    }

    fun getSmsReceiverInstance() = mySmsBroadcastReceiver

    override fun onTerminate() {
        unregisterReceiver(mySmsBroadcastReceiver)
        super.onTerminate()
    }
}