package com.freehome.mamaison.models.responseFolder

import com.freehome.mamaison.models.listFolder.ListLocation
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationResponse (

    @SerializedName("list_Location")
    @Expose
    var list_Location:List<ListLocation>,
    var error:Boolean,
    var texterror:String
 )