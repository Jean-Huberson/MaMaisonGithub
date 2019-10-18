package com.freehome.mamaison.models.responseFolder

data class AuthResponse(
    var idClients:Int,
    var nomClients:String,
    var prenomClients:String,
    var emailClients:String,
    var telephoneClients:String,
    var mot_de_passClients:String
){
    var result:Boolean = true
    var iserror:String? = null

}

