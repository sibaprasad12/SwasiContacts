package com.swasi.utility.video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.swasi.utility.R
import com.swasi.utility.adapters.VideoAdapter
import com.swasi.utility.databinding.FragmentVideoBinding
import com.swasi.utility.listeners.VideoClickListener
import com.swasi.utility.model.ContactEntity
import com.swasi.utility.model.VideoEntity
import com.swasi.utility.utils.ContactManager

class VideoFragment : Fragment(), VideoClickListener {

    private lateinit var binding: FragmentVideoBinding

    private val videoViewModel = VideoViewModel()

    private val adapter: VideoAdapter by lazy {
        VideoAdapter(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        activity?.title = getString(R.string.tab_title_videos)
        binding.videoRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        videoViewModel.getAllVideos(requireContext())
        videoViewModel.favouriteVideos.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                adapter.setVideos(ContactManager.getMyFavouriteVideoList(requireContext()))
            } else {
                adapter.setVideos(it as MutableList<VideoEntity>)
            }
        }
        binding.videoRecyclerView.adapter = adapter

        binding.fabVideo.setOnClickListener {
            addVideos()
        }
    }

    private fun addVideos(){
        val action =
            VideoFragmentDirections.actionVideoFragmentToEditVideoFragment(null)
        findNavController().navigate(action)
    }

    override fun onVideoClick(video: VideoEntity) {
        val uri = Uri.parse(video.videoUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onVideoEdit(videoEntity: VideoEntity) {

    }

    override fun onVideoDelete(videoEntity: VideoEntity) {
        videoViewModel.deleteVideo(requireContext(), videoEntity)
    }
    private fun insertAllVideos() {
        videoViewModel.insertAllVideos(
            requireContext(),
            ContactManager.getMyFavouriteVideoList(requireContext())
        )
    }
}