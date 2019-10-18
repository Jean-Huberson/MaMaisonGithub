package com.freehome.mamaison.models.responseFolder

import com.freehome.mamaison.models.listFolder.ListLog
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SalesResponse (

    @SerializedName("list_Vente")
    @Expose
    var list_Log: List<ListLog>,
    var error:Boolean,
    var texterror:String

)
