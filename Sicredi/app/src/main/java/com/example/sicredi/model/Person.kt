package com.example.sicredi.model

import com.google.gson.annotations.SerializedName

class Person {

    @SerializedName("picture")
    var picture: String;

    @SerializedName("name")
    var name: String;

    @SerializedName("eventId")
    var eventId: Int = 0

    @SerializedName("id")
    var id: Int = 0

    constructor(picture: String, name: String, eventId: Int, id: Int) {
        this.picture = picture
        this.name = name
        this.eventId = eventId
        this.id = id
    }
}
