package com.swasi.utility.app

import android.app.Application
import android.content.IntentFilter
import android.provider.Telephony
import com.swasi.utility.ui.messages.SmsBroadcastReceiver

/**
 * Created by Sibaprasad Mohanty on 12/02/2022.
 * Spm Limited
 * sp.dobest@gmail.com
 */

class SwasiUtilityApp : Application() {
    private lateinit var smsBroadcastReceiver: SmsBroadcastReceiver
    override fun onCreate() {
        super.onCreate()
        smsBroadcastReceiver =
            SmsBroadcastReceiver(/*BuildConfig.SERVICE_NUMBER, BuildConfig.SERVICE_CONDITION*/)
        registerReceiver(
            smsBroadcastReceiver,
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

    fun setListener(listener: SmsBroadcastReceiver.SmsListener) {
        smsBroadcastReceiver.setListener(listener)
    }

    fun getSmsReceiverInstance() = smsBroadcastReceiver

    override fun onTerminate() {
        unregisterReceiver(smsBroadcastReceiver)
        super.onTerminate()
    }
}