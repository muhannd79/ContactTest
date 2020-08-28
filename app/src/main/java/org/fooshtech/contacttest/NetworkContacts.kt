package org.fooshtech.contacttest

import org.fooshtech.contacttest.model.ContactItem
import org.fooshtech.contacttest.model.ContactResponse
import org.fooshtech.contacttest.model.PhoneItem
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.NullPointerException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Query and return ContactResponse
 **/
class NetworkContacts(val url: String) {

    fun executeNetworkCall(): ContactResponse {

        var contactURL = URL(url)
        var httpURLConnection: HttpURLConnection = contactURL.openConnection() as HttpURLConnection

        //Configure the HTTPUrlConnection
        httpURLConnection.connectTimeout = 1000
        httpURLConnection.readTimeout = 15000
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.doInput = true  //define the fllowo of the datat

        //Execute the Connection
        httpURLConnection.connect()

        //Receive the Data
        val inputStream = httpURLConnection.inputStream

        //Receive the Server Response
        val responseCode = httpURLConnection.responseCode

        val stringResponse: String? = parseIstoString(inputStream)

        val constactResponse: ContactResponse = pasrePoko(stringResponse)

        return constactResponse

    }

    private fun pasrePoko(stringResponse: String?): ContactResponse {

        if (stringResponse == null) {
            throw    NullPointerException()
        }

        val jsonResponse: JSONObject = JSONObject(stringResponse)
        val jsonArray = jsonResponse.getJSONArray("contacts")

        // 0 to n-1 -.> until
        var phoneItem: PhoneItem
        var contactItem: ContactItem
        var listOfContactItem = mutableListOf<ContactItem>() // = new ArrayList<ContactItem> // ArrayList<ContactItem>()

        for (index in 0 until jsonArray.length()) {

            val jsonItem = jsonArray.get(index) as JSONObject
            val jsoItemPhone = jsonItem.getJSONObject("phone")
            phoneItem = PhoneItem(
                jsoItemPhone.getString("mobile"),
                jsoItemPhone.getString("home"),
                jsoItemPhone.getString("office")
            )

            contactItem = ContactItem(
                phone = phoneItem, id = jsonItem.getString("id"),
                name = jsonItem.getString("name"), gender = jsonItem.getString("gender"),
                email = jsonItem.getString("email"), address = jsonItem.getString("address")
            )

            listOfContactItem.add(contactItem)
        }
        return ContactResponse(listOfContactItem)
    }


    private fun parseIstoString(inputStream: InputStream): String? {

        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(inputStream))
        // In Kotlin we need to inilize the variable
        var line: String? = reader.readLine()
        while (line != null) {
            // builder.append( """ $line """.trimIndent())
            builder.append("$line\n")
            line = reader.readLine()

        }
        if (builder.isEmpty()) {
            return null
        }
        return builder.toString()

    }
}