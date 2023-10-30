package com.swasi.utility.ui.flashLight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.swasi.utility.R


class FlashLightActivity : AppCompatActivity(), View.OnClickListener {

    private var cameraManager: CameraManager? = null
    private var getCameraID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_light)

        findViewById<AppCompatButton>(R.id.buttonOn).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.buttonOff).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.buttonSpeed1).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.buttonSpeed2).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.buttonSpeed3).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.buttonSpeed4).setOnClickListener(this)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        // Exception is handled, because to check whether
        // the camera resource is being used by another
        // service or not.

        // Exception is handled, because to check whether
        // the camera resource is being used by another
        // service or not.
        try {
            // O means back camera unit,
            // 1 means front camera unit
            getCameraID = cameraManager?.cameraIdList?.get(0)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOn -> {
                setFlashLight(true)
            }
            R.id.buttonOff -> {
                setFlashLight(false)
            }
            R.id.buttonSpeed1 -> {
                blinkFlash1()
            }
            R.id.buttonSpeed2 -> {
                blinkFlash2()
            }
            R.id.buttonSpeed3 -> {
                blinkFlash3()
            }
            R.id.buttonSpeed4 -> {
                blinkFlash4()
            }
        }
    }

    private fun setFlashLight(isEnable: Boolean) {
        try {
            // true sets the torch in ON mode
            getCameraID?.let { cameraManager?.setTorchMode(it, isEnable) }
            // Inform the user about the flashlight
            // status using Toast message
            Toast.makeText(this, "Flashlight is turned ON", Toast.LENGTH_SHORT).show()
        } catch (e: CameraAccessException) {
            // prints stack trace on standard error
            // output error stream
            e.printStackTrace()
        }
    }

    private fun blinkFlash1() {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val myString = "0101010101"
        val blinkDelay: Long = 50 //Delay in ms
        for (element in myString) {
            if (element == '0') {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, true)
                } catch (e: CameraAccessException) {
                }
            } else {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, false)
                } catch (e: CameraAccessException) {
                }
            }
            try {
                Thread.sleep(blinkDelay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun blinkFlash2() {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val myString = "0101010101"
        val blinkDelay: Long = 100 //Delay in ms
        for (element in myString) {
            if (element == '0') {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, true)
                } catch (e: CameraAccessException) {
                }
            } else {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, false)
                } catch (e: CameraAccessException) {
                }
            }
            try {
                Thread.sleep(blinkDelay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun blinkFlash3() {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val myString = "0101010101"
        val blinkDelay: Long = 150 //Delay in ms
        for (element in myString) {
            if (element == '0') {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, true)
                } catch (e: CameraAccessException) {
                }
            } else {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, false)
                } catch (e: CameraAccessException) {
                }
            }
            try {
                Thread.sleep(blinkDelay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun blinkFlash4() {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val myString = "0101010101"
        val blinkDelay: Long = 300 //Delay in ms
        for (element in myString) {
            if (element == '0') {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, true)
                } catch (e: CameraAccessException) {
                }
            } else {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, false)
                } catch (e: CameraAccessException) {
                }
            }
            try {
                Thread.sleep(blinkDelay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun blinkFlash5() {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val myString = "0101010101"
        val blinkDelay: Long = 50 //Delay in ms
        for (element in myString) {
            if (element == '0') {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, true)
                } catch (e: CameraAccessException) {
                }
            } else {
                try {
                    val cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId, false)
                } catch (e: CameraAccessException) {
                }
            }
            try {
                Thread.sleep(blinkDelay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}