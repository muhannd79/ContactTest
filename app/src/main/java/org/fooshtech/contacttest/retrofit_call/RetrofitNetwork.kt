package org.fooshtech.contacttest.retrofit_call

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.fooshtech.contacttest.checkInternetConnection
import org.fooshtech.contacttest.model.ContactResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitNetwork {

    @GET("contacts")
    fun gtMeContacts(): Call<ContactResponse>


    companion object {

        fun initRetrofit(context: Context): RetrofitNetwork {

            return Retrofit.Builder()
                .client(createCacheClient(context))
                .baseUrl("https://api.androidhive.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitNetwork::class.java)

        }


        private fun createCacheClient(context: Context): OkHttpClient {
            // cache size
            // 1MB
            val cacheSize = (1 * 1024 * 1024).toLong()
            val cache = Cache(context.cacheDir, cacheSize)
            val cacheBuilder = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor {
                    var request = it.request()
                    request = if (context.checkInternetConnection()) {
                        //true -> is connected
                        request.newBuilder().build()
                    } else {
                        request.newBuilder().addHeader(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 10
                        ).build()
                    }
                    it.proceed(request)
                }
            return cacheBuilder.build()
        }

    }
}