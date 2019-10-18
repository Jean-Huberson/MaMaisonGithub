package com.freehome.mamaison.models.listFolder

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListQuartier {

    @SerializedName("id_quartier")
    @Expose
    private var id_quartier:String? = null

    fun getQuartierId():String? {
        return id_quartier
    }

    @SerializedName("description_quartier")
    @Expose
    private var description_quartier:String? = null

    fun getQuartierDescription():String? {
        return description_quartier
    }
}