package com.swasi.utility.ui.contact

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swasi.utility.ui.database.SwasiContactRoomDatabase
import com.swasi.utility.model.ContactEntity
import com.swasi.utility.preference.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel() : ViewModel() {

    val favouriteContactList = MutableLiveData<List<ContactEntity>>()

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "userPreference"
    )

//    val allContacts: Flow<List<ContactEntity>> = repository.allContacts

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(contactEntity: ContactEntity) = viewModelScope.launch {
        // repository.insert(contactEntity)
    }

    suspend fun incrementCounter(context: Context) {
        context.userPreferencesDataStore.edit { preferences ->
            // Read the current value from preferences
            val currentCounterValue = preferences[UserPreference.APP_INSTALLED] ?: 0
            // Write the current value + 1 into the preferences
            //  preferences[UserPreference.APP_INSTALLED] = currentCounterValue
        }
    }

    fun getAllContacts(context: Context) {
        viewModelScope.launch {
            try {
                val countriesFromDb =
                    SwasiContactRoomDatabase.getDatabase(context).contactDao().getAllContact()
                favouriteContactList.postValue(countriesFromDb)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

    fun insertAll(context: Context, listContact: List<ContactEntity>) {
        viewModelScope.launch {
            try {
                SwasiContactRoomDatabase.getDatabase(context).contactDao().insertAll(listContact)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

    fun updateContact(context: Context, contactEntity: ContactEntity) {
        viewModelScope.launch {
            try {
                SwasiContactRoomDatabase.getDatabase(context).contactDao().update(contactEntity)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

    fun insertContact(context: Context, contactEntity: ContactEntity) {
        viewModelScope.launch {
            try {
                SwasiContactRoomDatabase.getDatabase(context).contactDao().insert(contactEntity)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

    fun getContactCount(context: Context) =
        SwasiContactRoomDatabase.getDatabase(context).contactDao().getRowCount()


    fun deleteContact(context: Context, contactEntity: ContactEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                SwasiContactRoomDatabase.getDatabase(context).contactDao()
                    .deleteByUserId(contactEntity.id)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

}

//class ContactViewModelFactory(private val repository: ContactRepository) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ContactViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
