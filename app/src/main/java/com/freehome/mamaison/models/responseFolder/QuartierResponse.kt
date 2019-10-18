package com.freehome.mamaison.models.responseFolder

import com.freehome.mamaison.models.listFolder.ListQuartier

data class QuartierResponse (

    var list_quartier:List<ListQuartier>,
    var error:Boolean
)