package com.swasi.utility.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.swasi.utility.R
import com.swasi.utility.adapters.ContactAdapter
import com.swasi.utility.databinding.FragmentContactBinding
import com.swasi.utility.imagepicker.MyLifecycleObserver
import com.swasi.utility.listeners.ContactClicklistener
import com.swasi.utility.model.ContactEntity
import com.swasi.utility.utils.ContactManager
import com.swasi.utility.video.VideoViewModel


class ContactFragment : Fragment(), ContactClicklistener {

    private lateinit var observer: MyLifecycleObserver
    private lateinit var bindingContact: FragmentContactBinding
    //Registry to register for activity result
    private val mRegistry: ActivityResultRegistry? = null
    private var launcher: ActivityResultLauncher<Intent>? = null

    private val contactViewModel: ContactViewModel = ContactViewModel()
    private val videoViewModel: VideoViewModel = VideoViewModel()

    private val adapter: ContactAdapter by lazy {
        ContactAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = MyLifecycleObserver(requireActivity().activityResultRegistry)
        lifecycle.addObserver(observer)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingContact = FragmentContactBinding.inflate(inflater, container, false)
        return bindingContact.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        activity?.title = getString(R.string.tab_title_home)
        bindingContact.contactRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        val app_preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val counter = app_preferences.getInt("isInstalled", 0)
        if (counter <= 0) {
            contactViewModel.insertAll(
                requireContext(),
                ContactManager.getContactList(requireContext())
            )
            insertAllFavouriteVideos()
            val edit = app_preferences.edit()
            edit.putInt("isInstalled", 1)
            edit.commit()
        }

        contactViewModel.getAllContacts(requireContext())
        contactViewModel.favouriteContactList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                adapter.setContactList(ContactManager.getContactList(requireContext()))
            } else {
                adapter.setContactList(it as MutableList<ContactEntity>)
            }
        }

        bindingContact.contactRecyclerView.adapter = adapter
        bindingContact.fabAddContact.setOnClickListener {
//            observer.captureImage(requireContext())
            val action =
                ContactFragmentDirections.actionContactFragmentToEditContactFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onWhatsUpClick(contactEntity: ContactEntity) {
        val url = "https://api.whatsapp.com/send?phone=${contactEntity.mobileNumber}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    override fun onTelegramClick(contactEntity: ContactEntity) {
        var intent: Intent? = null
        try {
            try {
                context?.packageManager?.getPackageInfo("org.telegram.messenger", 0)//Check for Telegram Messenger App
            } catch (e : Exception){
                context?.packageManager?.getPackageInfo("org.thunderdog.challegram", 0)//Check for Telegram X App
            }
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=${contactEntity.mobileNumber}"))
        }catch (e : Exception){ //App not found open in browser
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.telegram.me/$contactEntity.mobileNumber"))
        }
        startActivity(intent)
    }


    override fun onCallClick(contactEntity: ContactEntity) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${contactEntity.mobileNumber}")
        startActivity(intent)
    }

    override fun onEditClick(contactEntity: ContactEntity?) {
        Toast.makeText(requireContext(), "Edit " + contactEntity?.name, Toast.LENGTH_SHORT).show()
        val action =
            ContactFragmentDirections.actionContactFragmentToEditContactFragment(contactEntity)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(contactEntity: ContactEntity?) {
        contactViewModel.deleteContact(requireContext(), contactEntity!!)
        contactViewModel.getAllContacts(requireContext())
    }

    private fun insertAllFavouriteVideos(){
        videoViewModel.insertAllVideos(
            requireContext(),
            ContactManager.getMyFavouriteVideoList(requireContext())
        )
    }
}