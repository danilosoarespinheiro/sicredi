package com.example.sicredi.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class EventAPIService (context: Context) {
    private val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"

    private var api: EventAPI? = null

    val cachSize = (5 * 1024 * 1024).toLong()
    val myCache = okhttp3.Cache(context.cacheDir, cachSize)

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(context)!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            chain.proceed(request)
        }
        .build()

    fun eventAPIService() {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(EventAPI::class.java)
    }

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    fun getEvents(): Single<List<Event>>? {
        return api?.getAllEvents()
    }

    fun getOneEvent(eventId: String): Single<Event>? {
        return api?.getOneEvent(eventId)
    }

    fun checkIn(checkIn: CheckIn): Single<Event>? {
        return api?.checkIn(checkIn)
    }
}