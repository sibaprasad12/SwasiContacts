package com.swasi.utility.ui.messages

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.swasi.utility.app.SwasiUtilityApp
import com.swasi.utility.databinding.FragmentMessageBinding


class MessageFragment : Fragment(), SmsBroadcastReceiver.OTPReceiveListener {

    private lateinit var binding: FragmentMessageBinding
    private val smsBroadcastReceiver: SmsBroadcastReceiver by lazy {
        SmsBroadcastReceiver()
    }
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
    }

    override fun onResume() {
        super.onResume()
        startSMSRetrieverClient()
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(smsBroadcastReceiver)
    }

    private fun loadMessages() {
        with((requireActivity().application as SwasiUtilityApp)) {
            with(getSmsReceiverInstance()) {
                getAllSms(requireActivity())?.let {
                    val groupByList = it.chunked(20)
                    setupRecyclerView(groupByList)
                   // setListener(this@MessageFragment)
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
           // loadMessages()
        }

        binding.buttonLoadMore.setOnClickListener {
            count++
            if (count < list.size) {
                adapter.submitList(list.elementAt(count))
            }
        }
    }

    private fun initializeSmsBroadcastReceiver() {
        registerSmsReceiver()
        requireActivity().registerReceiver(
            smsBroadcastReceiver,
            IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        )
        smsBroadcastReceiver.init(this)
    }

    private fun startSMSRetrieverClient() {
        val client = SmsRetriever.getClient(requireActivity())
        val retriever = client.startSmsRetriever()
        retriever.addOnSuccessListener {
            val listener = object : SmsBroadcastReceiver.OTPReceiveListener {
                override fun onOTPReceived(otp: String) {
                    Log.d("AppSignatureHelper",otp)
                    Toast.makeText(context, otp, Toast.LENGTH_SHORT).show()
                }

                override fun onOTPTimeOut() {
                    Log.d("AppSignatureHelper","Timed Out.")
                }
            }
            smsBroadcastReceiver.init(listener)
            requireActivity().registerReceiver(smsBroadcastReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
        }
        retriever.addOnFailureListener {
            Log.d("AppSignatureHelper","Problem to start listener")
            //Problem to start listener
        }


        retriever.addOnSuccessListener { aVoid: Void? -> }
        retriever.addOnFailureListener { e: Exception? -> }
    }

    private fun registerSmsReceiver() {
        ContextCompat.registerReceiver(
            requireActivity(),
            smsBroadcastReceiver,
            IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),
            SmsRetriever.SEND_PERMISSION,
            /* scheduler = */null,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    override fun onOTPReceived(otp: String) {
        otp.let {
            binding.tvRecentSms.text = it
        }
        Toast.makeText(requireActivity(), "OTP : $otp", Toast.LENGTH_LONG)
    }

    override fun onOTPTimeOut() {
        Toast.makeText(requireActivity(), "OTP time out", Toast.LENGTH_LONG)
    }
}