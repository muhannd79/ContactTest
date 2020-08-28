package org.fooshtech.contacttest

import android.app.IntentService
import android.content.Intent


const val EXTRA_IS_URL = "ContactsIntentService"  // or we can define it as companion object

class ContactsIntentService : IntentService("Contacts"){

    /**
     * @param
     */
    override fun onHandleIntent(intent: Intent?) {


        intent?.getStringExtra(EXTRA_IS_URL)?.let {

            val  contactResponse   =    NetworkContacts(it).run {
               executeNetworkCall()
            }

            // todo send the Broadcast.....
            Intent(ACTION_NETWORK_BROADCAST).also {
                it.putExtra(EXTRA_NETWORK_BROADCAST,contactResponse)
                baseContext.sendBroadcast(it)
            }

        }



    }



//    companion object{
//
//        const val EXTRA_US_URL = "ContactsIntentService"
//
//
//    }


}