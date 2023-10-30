package com.swasi.utility.ui.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.swasi.utility.databinding.FragmentBottomsheetBinding

/**
 * Created by Sibaprasad Mohanty on 16/04/2023.
 * siba.x.prasad@gmail.com
 */

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mListener: OnBottomSheetItemClickListener
    private lateinit var binding: FragmentBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCamera.setOnClickListener { mListener.onCameraClicked() }
        binding.buttonCancel.setOnClickListener { mListener.onCancelClicked() }
        binding.buttonGallery.setOnClickListener { mListener.onGalleryClicked() }
    }

    fun setListener(listener: OnBottomSheetItemClickListener){
        this.mListener = listener
    }
}

interface OnBottomSheetItemClickListener{
    fun onGalleryClicked()
    fun onCameraClicked()
    fun onCancelClicked()
}