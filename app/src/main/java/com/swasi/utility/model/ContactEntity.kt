package com.swasi.utility.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sibaprasad Mohanty on 02/01/2022.
 * Spm Limited
 * sp.dobest@gmail.com
 */

@Parcelize
@Entity(tableName = "contact_table")
class ContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String = "",
    var mobileNumber: String = "",
    var drawable: Int = 0,
    var imagePath: String = ""
) : Parcelable