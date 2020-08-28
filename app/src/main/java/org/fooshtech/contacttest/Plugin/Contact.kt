package org.fooshtech.contacttest.Plugin

import kotlinx.android.parcel.Parcelize


data class Contact(
    val address: String,
    val email: String,
    val gender: String,
    val id: String,
    val name: String,
    val phone: Phone
)