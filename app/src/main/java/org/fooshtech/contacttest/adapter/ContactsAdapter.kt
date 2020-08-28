package org.fooshtech.contacttest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import org.fooshtech.contacttest.R
import org.fooshtech.contacttest.model.ContactResponse

class ContactsAdapter(val dataSet : ContactResponse) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    lateinit var view : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {

        view  = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return  ContactsViewHolder(view)

    }


    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {

        holder.tvItemName.text = dataSet.contacts[position].name
        holder.tvItemEmail.text = dataSet.contacts[position].email
    }


    override fun getItemCount(): Int {
      return  dataSet.contacts.size
    }


    // contactsItem will be just a parameters so in case these is a fun and need to access it, it can till we put var or val
    class ContactsViewHolder(contactsItem : View) : RecyclerView.ViewHolder(contactsItem) {

        val tvItemName  : TextView = contactsItem.tv_item_name
        val tvItemEmail  : TextView = contactsItem.tv_item_email
    }
}

