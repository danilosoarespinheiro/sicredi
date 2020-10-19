package com.example.sicredi.model

import com.google.gson.annotations.SerializedName

class Event {

    @SerializedName("people")
    lateinit var people : List<Person>

    @SerializedName("date")
    var date : Long = 0

    @SerializedName("description")
    lateinit var description :String

    @SerializedName("image")
    lateinit var image : String

    @SerializedName("longitude")
    var longitude : Double = 0.0

    @SerializedName("latitude")
    var latitude : Double = 0.0

    @SerializedName("price")
    var price : Double = 0.0

    @SerializedName("title")
    lateinit var title : String

    @SerializedName("id")
    var id : Int = 0

}