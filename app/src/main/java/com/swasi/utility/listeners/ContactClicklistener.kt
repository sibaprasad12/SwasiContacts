package com.swasi.utility.listeners

import com.swasi.utility.model.ContactEntity


/**
 * Created by Sibaprasad Mohanty on 02/01/2022.
 * Spm Limited
 * sp.dobest@gmail.com
 */

interface ContactClicklistener {
    fun onWhatsUpClick(contactEntity: ContactEntity)
    fun onTelegramClick(contactEntity: ContactEntity)
    fun onCallClick(contactEntity: ContactEntity)
    fun onEditClick(contactEntity: ContactEntity?)
    fun onDeleteClick(contactEntity: ContactEntity?)
}