package com.swasi.utility.contact

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory.decodeFile
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.swasi.utility.R
import com.swasi.utility.bottomsheet.BottomSheetFragment
import com.swasi.utility.bottomsheet.OnBottomSheetItemClickListener
import com.swasi.utility.database.SwasiContactRoomDatabase
import com.swasi.utility.databinding.FragmentEditContactBinding
import com.swasi.utility.model.ContactEntity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditContactFragment : Fragment(), OnBottomSheetItemClickListener {

    //Registry to register for activity result
    private val mRegistry: ActivityResultRegistry? = null
    private var launcher: ActivityResultLauncher<Intent>? = null
    private lateinit var binding: FragmentEditContactBinding
    private var contactEntity: ContactEntity? = null
    private val viewModel = ContactViewModel()
    private var count: Int = 0;

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_GALLERY_IMAGE = 2
    private var currentPhotoPath: String = ""


    private lateinit var bottomSheet: BottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_contact, container, false
        )
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        activity?.title = getString(R.string.add_or_edit_contact)
        bottomSheet = BottomSheetFragment()
        bottomSheet.setListener(this)
        binding.buttonCancel.setOnClickListener {
            activity?.title = getString(R.string.tab_title_home)
            requireActivity().onBackPressed()
        }
        contactEntity = arguments?.getParcelable("contactEntity")
        if (contactEntity != null) {
            binding.contact = contactEntity
            setImageDrawable(binding.imageViewProfile, contactEntity!!)
        }
        setUpLauncher()
        binding.buttonSave.setOnClickListener {
            saveContact(contactEntity != null)
        }
        viewModel.getContactCount(requireContext())?.observe(viewLifecycleOwner) {
            count = it!!
        }

        binding.imageViewProfile.setOnClickListener {
            bottomSheet.show(childFragmentManager, "EditFragment")
        }

        binding.imageviewEdit.setOnClickListener {
            bottomSheet.show(childFragmentManager, "EditFragment")
        }
    }

    private fun setUpLauncher() {
        launcher = mRegistry?.register(
            "key",
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data = result.data!!
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(Menu.NONE, 1, Menu.NONE, "Camera");
        menu.add(Menu.NONE, 2, Menu.NONE, "Gallery");
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                chooseFromCamera()
                return true
            }
            2 -> {
                chooseFromGallery()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            loadImage()
        }

        if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == RESULT_OK) {
            val selectedUri = data?.data
            currentPhotoPath = getRealPathFromURIForGallery(selectedUri)!!
            decodeFile(currentPhotoPath)
            loadImage()
        }
    }

    private fun loadImage() {
        Picasso.get().load(Uri.fromFile(File(currentPhotoPath))).into(binding.imageViewProfile)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun chooseFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(requireActivity(), "Error ${ex.message}", Toast.LENGTH_SHORT)
                        .show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.swasi.utility.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun chooseFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, REQUEST_GALLERY_IMAGE)
    }

    // And to convert the image URI to the direct file system path of the image file
    private fun getRealPathFromURI(contentUri: Uri?): String {
        // can post image
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = requireActivity().managedQuery(
            contentUri,
            proj,  // Which columns to return
            null,  // WHERE clause; which rows to return (all rows)
            null,  // WHERE clause selection arguments (none)
            null
        ) // Order-by clause (ascending by name)
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    private fun getRealPathFromURIForGallery(uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
        return uri.path
    }

    private fun updateContact() {
        if (currentPhotoPath.isNotEmpty()) {
            contactEntity?.let { contact ->
                GlobalScope.launch {
                    SwasiContactRoomDatabase.getDatabase(requireContext()).contactDao()
                        .update(contact)
                }
            }
        }
    }

    private fun saveContact(isUpdate: Boolean) {
        if (!isUpdate) {
            contactEntity = ContactEntity(count + 1)
        }

        contactEntity?.let {
            it.mobileNumber = binding.etNumber.text.toString()
            it.name = binding.etName.text.toString()
            if (currentPhotoPath.isNotEmpty()) {
                it.imagePath = currentPhotoPath
            }
        }
        if (isUpdate) {
            viewModel.updateContact(requireContext(), contactEntity!!)
        } else {
            viewModel.insertContact(requireContext(), contactEntity!!)
        }

        val action =
            EditContactFragmentDirections.actionEditContactFragmentToContactFragment()
        findNavController().navigate(action)
    }

    override fun onGalleryClicked() {
        chooseFromGallery()
        cancelBottomSheetDialog()
    }

    override fun onCameraClicked() {
        chooseFromCamera()
        cancelBottomSheetDialog()
    }

    override fun onCancelClicked() {
        cancelBottomSheetDialog()
    }

    private fun cancelBottomSheetDialog(){
        bottomSheet.dismissAllowingStateLoss()
    }

    private fun setImageDrawable(AppCompatImageView: CircleImageView, contactEntity: ContactEntity) {
        if (contactEntity.imagePath.isNotEmpty()) {
            Picasso.get().load(Uri.fromFile(File(contactEntity.imagePath))).into(AppCompatImageView)
        } else {
            AppCompatImageView.setImageResource(contactEntity.drawable)
        }
    }
}