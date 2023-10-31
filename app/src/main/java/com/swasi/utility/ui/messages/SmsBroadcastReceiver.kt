package com.swasi.utility.ui.messages

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Matcher
import java.util.regex.Pattern


class SmsBroadcastReceiver : BroadcastReceiver() {

    private var otpReceiveListener: OTPReceiveListener? = null

    fun SmsBroadcastReceiver() {}

    fun init(otpReceiveListener: OTPReceiveListener?) {
        this.otpReceiveListener = otpReceiveListener
    }

    override fun onReceive(context: Context?, intent: Intent) {
        getMessage(intent)
        if (intent.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras = intent.extras
            val status = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            val sms = extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
            Log.i("Message", "sms $sms status $status extras $extras")
            when (status!!.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    Log.i("Message", "$sms")
                    sms?.let {
                        // val p = Pattern.compile("[0-9]+") check a pattern with only digit
                        val p = Pattern.compile("\\d+")
                        val m = p.matcher(it)
                        if (m.find()) {
                            val otp = m.group()
                        }
                        if (otpReceiveListener != null) {
                            otpReceiveListener?.onOTPReceived(it)
                        }
                    }
                }
            }
        }
    }

    private fun getMessage(intent: Intent){
        if (intent.action != SmsRetriever.SMS_RETRIEVED_ACTION) return
        val extras = intent.extras ?: return
        val status: Status? = extras.getParcelable(SmsRetriever.EXTRA_STATUS)

        if (status?.statusCode != CommonStatusCodes.SUCCESS) return

        extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)?.let {
            Log.i("message", "$it")
//            otpReceiveListener?.onOTPReceived(it.)
        }
    }

    interface OTPReceiveListener {
        fun onOTPReceived(otp: String)
        fun onOTPTimeOut()
    }
}