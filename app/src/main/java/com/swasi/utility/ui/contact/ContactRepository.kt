package com.swasi.utility.ui.contact

import androidx.annotation.WorkerThread
import com.swasi.utility.ui.database.ContactDao
import com.swasi.utility.model.ContactEntity

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ContactRepository(private val contactDao: ContactDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
//    val allContacts: Flow<List<ContactEntity>> = contactDao.getAllContact()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(contact: ContactEntity) {
        contactDao.insert(contact)
    }
}
