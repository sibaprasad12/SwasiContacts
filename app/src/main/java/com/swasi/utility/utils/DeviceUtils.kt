package com.swasi.utility.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.media.AudioManager
import android.os.BatteryManager
import android.os.Build
import android.provider.AlarmClock
import android.provider.MediaStore
import android.provider.Settings
import java.util.*


object DeviceUtils {

    fun openVideoCamera(context: Context) {
        val intent = Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA)
        context.startActivity(intent)
    }

    fun openCamera(context: Context) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        context.startActivity(intent)
    }

    fun setAlarm(context: Context) {
        val cal = GregorianCalendar()
        cal.timeInMillis = System.currentTimeMillis()
        val day = cal.get(Calendar.DAY_OF_WEEK)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val i = Intent(AlarmClock.ACTION_SET_ALARM)
        i.putExtra(AlarmClock.EXTRA_HOUR, hour + 0)
        i.putExtra(AlarmClock.EXTRA_MINUTES, minute + 2)
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        i.putExtra(AlarmClock.ACTION_SNOOZE_ALARM, false)
        context.startActivity(i)
    }

    fun setUpNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && !notificationManager.isNotificationPolicyAccessGranted
        ) {
            val intent = Intent(
                Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
            )
            context.startActivity(intent)
        }
    }

    fun setRingerMode(context: Context, isRinger: Boolean) {
        val am: AudioManager? =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        am?.ringerMode =
            if (isRinger) AudioManager.RINGER_MODE_NORMAL else AudioManager.RINGER_MODE_SILENT
    }

    fun getBatteryLevel(context: Context): Int {
        val bm = context.getSystemService(BATTERY_SERVICE) as BatteryManager
        val batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return batLevel
    }

}