package com.swasi.utility.ui.messages

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.Telephony
import android.text.format.DateFormat
import com.swasi.utility.utils.extractEightDigits
import com.swasi.utility.utils.extractFourDigits
import com.swasi.utility.utils.extractSixDigits


class MySmsBroadcastReceiver(
    private val serviceProviderNumber: String = "",
    private val serviceProviderSmsCondition: String = ""
) :
    BroadcastReceiver() {
    private var smsListener: SmsListener? = null
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            var smsSender = ""
            var smsBody = ""
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsSender = smsMessage.displayOriginatingAddress
                smsBody += smsMessage.messageBody
            }

            smsListener?.let {
                it.onTextReceived(smsBody)
                if (smsSender == serviceProviderNumber && smsBody.startsWith(
                        serviceProviderSmsCondition
                    )
                ) {
                    it.onTextReceived(smsBody)
                }
            }
        }
    }

    fun setListener(smsListener: SmsListener?) {
        this.smsListener = smsListener
    }

    interface SmsListener {
        fun onTextReceived(text: String?)
    }

    companion object {
        private const val TAG = "SmsBroadcastReceiver"
    }


    @SuppressLint("NewApi")
    fun getAllSms(context: Context): List<SmsData>? {
        val lstSms: MutableList<SmsData> = ArrayList()
        val cr: ContentResolver = context.contentResolver
        val c: Cursor? = cr.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.Sms.Inbox.BODY,
                Telephony.Sms.Inbox.ADDRESS,  // Select body text
                Telephony.Sms.Inbox.DATE_SENT),  // Select body text
            null, null, Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        ) // Default
        // sort
        // order);
        val totalSMS = c!!.count
        if (c.moveToFirst()) {
            for (i in 0 until totalSMS) {
                lstSms.add(getSmsDataFromCursor(c))
                c.moveToNext()
            }
        } else {
            throw RuntimeException("You have no SMS in Inbox")
        }
        c.close()
        return lstSms
    }

    private fun getSmsDataFromCursor(cursor: Cursor): SmsData {
       val content = cursor.getString(0)
       val number = cursor.getString(1)
       val date = convertDate(cursor.getString(2), "dd/MMM/yyyy hh:mm:ss")?:""
       val otpSixDigit = content.extractSixDigits()?:""
       val otpEightDigit = content.extractEightDigits() ?:""
       val otpFourDigit = content.extractFourDigits()?:""
        return SmsData(number, content, otpSixDigit, date)
    }

    private fun convertDate(dateInMilliseconds: String, dateFormat: String?): String? {
        return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
    }
}