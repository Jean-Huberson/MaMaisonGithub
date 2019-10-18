package com.freehome.mamaison.mamaisonapi



import com.freehome.mamaison.models.responseFolder.*
import com.freehome.mamaison.models.User
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("client/logClient.php")
    fun loginClient(@Body requestBody: User): Call<AuthResponse>

    @POST("client/createClient.php")
    fun createClient(@Body requestBody: User):Call<AuthResponse>

    @GET("logement/getSale.php")
    fun getSales():Call<SalesResponse>

    @GET("logement/getLocation.php")
    fun getLocations():Call<LocationResponse>

    @GET("logement/getResidence.php")
    fun getResidences():Call<ResidenceResponse>

    @GET("commune/getAllCommune.php")
    fun getAllCommune():Call<CommuneResponse>

    @GET("quartier/getAllQuartier.php")
    fun getAllQuartier():Call<QuartierResponse>

}