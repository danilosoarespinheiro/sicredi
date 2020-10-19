package com.example.sicredi.model

import com.google.gson.annotations.SerializedName

class CheckIn {

    @SerializedName("eventId")
    var eventId: Int = 0

    @SerializedName("name")
    var name: String

    @SerializedName("email")
    var email: String

    constructor( eventId: Int, name: String, email: String) {

        this.eventId = eventId
        this.name = name
        this.email = email
    }
}
