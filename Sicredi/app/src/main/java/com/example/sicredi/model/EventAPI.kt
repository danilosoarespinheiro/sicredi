package com.example.sicredi.model

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventAPI {

    @GET("events")
    fun getAllEvents(): Single<List<Event>>

    @GET("events/{id}")
    fun getOneEvent(@Path("id") id:String): Single<Event>

    @POST("checkin")
    fun checkIn(@Body checkIn: CheckIn): Single<Event>
}