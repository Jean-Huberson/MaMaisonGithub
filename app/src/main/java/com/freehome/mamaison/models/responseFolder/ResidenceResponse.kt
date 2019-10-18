package com.freehome.mamaison.models.responseFolder

import com.freehome.mamaison.models.listFolder.ListResidence
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResidenceResponse(

    @SerializedName("list_Residence")
    @Expose
    var list_Residence:List<ListResidence>,
    var error:Boolean,
    var texterror:String
)