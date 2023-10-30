package com.swasi.utility.ui.flashLight


import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.slider.Slider
import com.swasi.utility.R
import com.swasi.utility.ui.adapters.ImageAdapter
import com.swasi.utility.databinding.FragmentUtilityBinding
import com.swasi.utility.utils.ContactManager
import com.swasi.utility.utils.DeviceUtils


class UtilityFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentUtilityBinding

    private var cameraManager: CameraManager? = null
    private var getCameraID: String? = null
    private var flashStatus = false

    private val adapter: ImageAdapter by lazy {
        ImageAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUtilityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.tab_title_utility)
        cameraManager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            // O means back camera unit,
            // 1 means front camera unit
            getCameraID = cameraManager?.cameraIdList?.get(0)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        setUpUI()
    }

    private fun setUpUI() {
        binding.imageViewRinger.setOnClickListener(this)
        binding.imageViewCamera.setOnClickListener(this)
        binding.imageViewVideo.setOnClickListener(this)
        binding.imageViewTorch.setOnClickListener(this)
        binding.imageViewAlarm.setOnClickListener(this)
        binding.progressBarBattery.progress = DeviceUtils.getBatteryLevel(requireContext())
        binding.slider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            // blinkFlash(value)
            Toast.makeText(requireActivity(), "Value is $value", Toast.LENGTH_SHORT).show()
        })
//        DeviceUtils.setUpNotification(requireContext())
        setupRecyclerView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewTorch -> {
                setFlashLight()
            }
            R.id.imageViewRinger -> {
                DeviceUtils.setRingerMode(requireContext(), true)
            }
            R.id.imageViewCamera -> {
                DeviceUtils.openCamera(requireContext())
            }
            R.id.imageViewVideo -> {
                DeviceUtils.openVideoCamera(requireContext())
            }
            R.id.imageViewAlarm -> {
                DeviceUtils.setAlarm(requireContext())
            }
        }
    }

    private fun setFlashLight() {
        flashStatus = !flashStatus
        try {
            getCameraID?.let { cameraManager?.setTorchMode(it, flashStatus) }
            val message = if (flashStatus) "Flashlight is turned ON" else "Flashlight is turned OFF"
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
        } catch (e: CameraAccessException) {
            // prints stack trace on standard error
            // output error stream
            e.printStackTrace()
        }
        val drawable = if (flashStatus) R.drawable.ic_flash_off else R.drawable.ic_flash_on
//        imageViewOnOff.setImageDrawable(ContextCompat.getDrawable(requireContext(), drawable))
    }

    private fun blinkFlash(step: Float) {
        val myString = "0101010101010101"
        val blinkDelay: Long = when (step.toString()) {
            "10.0" -> {
                300
            }
            "20.0" -> {
                200
            }
            "30.0" -> {
                150
            }
            "40.0" -> {
                100
            }
            "50.0" -> {
                50
            }
            else -> {
                0
            }
        }

        for (element in myString) {

            try {
                val cameraId = cameraManager?.cameraIdList?.get(0)
                if (cameraId != null) {
                    cameraManager?.setTorchMode(cameraId, element == '0')
                }
            } catch (e: CameraAccessException) {
            }
            /* Handler().postDelayed({

             }, blinkDelay)*/

            try {
                Thread.sleep(blinkDelay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerviewImage.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        adapter.imageList = ContactManager.getImageList(requireContext())
        binding.recyclerviewImage.adapter = adapter
    }

}