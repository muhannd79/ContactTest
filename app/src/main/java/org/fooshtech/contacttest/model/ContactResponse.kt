package org.fooshtech.contacttest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ContactResponse(val contacts: List<ContactItem>) : Parcelable

@Parcelize
data class ContactItem (val id:String , val name: String , val email : String , val address : String ,
              val gender : String ,
              val phone : PhoneItem
) : Parcelable

@Parcelize
data class PhoneItem(val mobile : String , val home : String , val office : String ) : Parcelable






