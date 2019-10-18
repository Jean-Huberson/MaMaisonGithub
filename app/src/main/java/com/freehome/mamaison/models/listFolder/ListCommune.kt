package com.freehome.mamaison.models.listFolder

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListCommune {

    @SerializedName("id_commune")
    @Expose
    private var id_commune:String? = null

    fun getCommuneId():String? {
        return id_commune
    }

    @SerializedName("description_commune")
    @Expose
    private var description_commune:String? = null

    fun getCommuneDescription():String? {
        return description_commune
    }
}