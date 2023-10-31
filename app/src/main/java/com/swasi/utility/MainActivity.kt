package com.swasi.utility

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.swasi.utility.ui.messages.SmsBroadcastReceiver
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 100
    private val CALL_PERMISSION_CODE = 101
    private val STORAGE_PERMISSION_CODE = 102
    private val SMS_PERMISSION_CODE = 103
    private val permissionArray = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS
    )

    private var intentFilter: IntentFilter? = null
    private var smsReceiver: SmsBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNavigation()

        checkPermission(
            permissionArray,
            PERMISSION_REQUEST_CODE
        )

        initBroadCast()
    }

    private fun setUpBottomNavigation() {
        val navController = this.findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        navView.setupWithNavController(navController)
    }

    private fun checkPermission(permission: Array<String>, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission[0]
            ) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission[1]
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, permission, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "Call Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@MainActivity, "Call Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this@MainActivity,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            SMS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "SMS Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@MainActivity, "SMS Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun initBroadCast() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsReceiver = SmsBroadcastReceiver()
        smsReceiver?.init(object : SmsBroadcastReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String) {
//                showToast("OTP Received: $otp")
            }

            override fun onOTPTimeOut() {
//                showToast("OTP TIME OUT")
            }
        })
    }
}