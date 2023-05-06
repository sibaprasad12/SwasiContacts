package com.swasi.utility.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.swasi.utility.model.ContactEntity


@Dao
interface ContactDao {

    @Query("SELECT * FROM contact_table ORDER BY id ASC")
    suspend fun getAllContact(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contactEntity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contactList: List<ContactEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(contactEntity: ContactEntity)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM contact_table")
    fun getRowCount(): LiveData<Int?>?

    @Query("DELETE FROM contact_table WHERE id = :userId")
    fun deleteByUserId(userId: Int)

}
