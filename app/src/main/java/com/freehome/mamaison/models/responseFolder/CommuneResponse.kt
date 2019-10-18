package com.freehome.mamaison.models.responseFolder

import com.freehome.mamaison.models.listFolder.ListCommune

data class CommuneResponse (

    var list_commune:List<ListCommune>,
    var error:Boolean
)