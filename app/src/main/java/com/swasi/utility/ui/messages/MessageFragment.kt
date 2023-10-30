package com.swasi.utility.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.swasi.utility.app.SwasiUtilityApp
import com.swasi.utility.databinding.FragmentMessageBinding
import com.swasi.utility.utils.extractSixDigits

class MessageFragment : Fragment(), SmsBroadcastReceiver.SmsListener {

    private lateinit var binding: FragmentMessageBinding

    private var count = 0
    private val adapter: SmsAdapter by lazy {
        SmsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Messages"
        loadMessages()
    }

    private fun loadMessages() {
        with((requireActivity().application as SwasiUtilityApp)) {
            with(getSmsReceiverInstance()) {
                getAllSms(requireActivity())?.let {
                    val groupByList = it.chunked(20)
                    setupRecyclerView(groupByList)
                    setListener(this@MessageFragment)
                }

            }
        }
    }

    private fun setupRecyclerView(list: Collection<List<SmsData>>) {
        count = 0
        binding.recyclerviewMessages.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerviewMessages.adapter = adapter
        list.takeIf { list.isNotEmpty() }.let {
            adapter.submitList(list.elementAt(count)) //sub
        }

        binding.buttonRefresh.setOnClickListener {
            loadMessages()
        }

        binding.buttonLoadMore.setOnClickListener {
            count++
            if (count < list.size) {
                adapter.submitList(list.elementAt(count))
            }
        }
    }

    override fun onTextReceived(text: String?) {
        text?.let {
           // binding.tvRecentSms.text = it
        }
    }

}