package org.fooshtech.contacttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.fooshtech.contacttest.model.ContactResponse
import org.fooshtech.contacttest.retrofit_call.RetrofitNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val ACTION_NETWORK_BROADCAST = "NetworkBroadcastReceiver"
const val EXTRA_NETWORK_BROADCAST = "NetworkBroadcastReceiver"

class MainActivity : AppCompatActivity() {


    val urlContacts: String = "https://api.androidhive.info/contacts/"

    // val urlBaseUrl = "https://api.androidhive.info/"
    // val urlEndpoint = "contacts"
    private lateinit var networkBR: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        1  with Normal Way
//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//        getMeContacts()

        // 2  with Retrofit
        //Retrofit Method
         initNetworkCall()


        //3 with Service
//        registerNetworkBroadcast()
//        val contactExplicit = Intent(this, ContactsIntentService::class.java)
//        contactExplicit.putExtra(EXTRA_IS_URL, urlContacts)
//        startService(contactExplicit)
    }

    private fun registerNetworkBroadcast() {

        val contactIntentFilter = IntentFilter(ACTION_NETWORK_BROADCAST)
        networkBR = NetworkBroadcastReciver()
        registerReceiver(networkBR, contactIntentFilter)


    }

    private fun getMeContacts() {

        val network = NetworkContacts(urlContacts)
        val contactResponse = network.executeNetworkCall()

        val displayFragment = FragmentDisplay.newInstance(contactResponse)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container, displayFragment)
            .commit()

        Log.d("tmz", contactResponse.toString())


    }

    private fun initNetworkCall() {

        RetrofitNetwork
            .initRetrofit(this)
            .gtMeContacts()
            .enqueue(object : Callback<ContactResponse> {

                override fun onResponse(
                    call: Call<ContactResponse>, response: Response<ContactResponse>
                ) {

                    if (response.isSuccessful) {
                        Log.d("tmz", "muhannd=" + response.body().toString())
                        initFragment(response.body())
                    }
                }


                override fun onFailure(call: Call<ContactResponse>, t: Throwable) {


                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun initFragment(body: ContactResponse?) {

        //if Body is not null run what inside the let Lambda
        body?.also {

            val displayFragment = FragmentDisplay.newInstance(it)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_fragment_container, displayFragment)
                .commit()
        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }

    inner class NetworkBroadcastReciver : BroadcastReceiver() {


        override fun onReceive(context: Context?, intent: Intent?) {
            //we be execute on MainThread
            if (intent?.action == ACTION_NETWORK_BROADCAST) {

                intent.getParcelableExtra<ContactResponse>(EXTRA_NETWORK_BROADCAST)?.let {

                    val displayFragment = FragmentDisplay.newInstance(it)

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_fragment_container, displayFragment)
                        .commit()
                }
            }

        }
    }
}

//Extension Functions
// Same as we do with Utility Class n Java
// fun<Target>.extensionFunctionName(anyParm : Any) :
fun Context.checkInternetConnection(): Boolean {

    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

   // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

   val activeNetwork = connectivityManager.activeNetworkInfo

    return activeNetwork?.isConnected ?: false



}
