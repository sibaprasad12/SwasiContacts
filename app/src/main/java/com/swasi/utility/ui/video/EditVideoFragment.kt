package com.swasi.utility.ui.video

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swasi.utility.R
import com.swasi.utility.databinding.FragmentEditVideoBinding
import com.swasi.utility.model.VideoEntity

class EditVideoFragment : Fragment() {

    //Registry to register for activity result
    private lateinit var binding: FragmentEditVideoBinding
    private var videoEntity: VideoEntity? = null
    private val viewModel = VideoViewModel()
    private var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditVideoBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        activity?.title = getString(R.string.add_or_edit_video)
        binding.buttonCancel.setOnClickListener {
            activity?.title = getString(R.string.tab_title_home)
            requireActivity().onBackPressed()
        }
        videoEntity = arguments?.getParcelable("videoEntity")
        if (videoEntity != null) {
            binding.video = videoEntity
        }
        binding.buttonSave.setOnClickListener {
            saveContact(videoEntity != null)
        }
        viewModel.getVideoCount(requireContext())?.observe(viewLifecycleOwner) {
            count = it!!
        }
    }

    private fun saveContact(isUpdate: Boolean) {
        if (!isUpdate) {
            videoEntity = VideoEntity(count + 1)
        }

        videoEntity?.let {
            it.videoName = binding.etVideoName.text.toString()
            val videoUrl = binding.etVideoUrl.text.toString()
            it.videoUrl = videoUrl
            it.videoThumbnailUrl = getVideoThumbNail(videoUrl)
        }

        if (isUpdate) {
            viewModel.updateVideo(requireContext(), videoEntity!!)
        } else {
            viewModel.insertVideo(requireContext(), videoEntity!!)
        }
        val action =
            EditVideoFragmentDirections.actionEditVideoFragmentToVideoFragment()
        findNavController().navigate(action)
    }

    private fun getVideoThumbNail(videoUrl: String?): String{
//        https://img.youtube.com/vi/HnXkv_ozPQw/0.jpg

        var thumbnail = ""
        videoUrl?.let {
            val result: String = it.substring(it.lastIndexOf('/') + 1).trim()
            thumbnail = "https://img.youtube.com/vi/${result}/0.jpg"
        }
        return thumbnail
    }
}