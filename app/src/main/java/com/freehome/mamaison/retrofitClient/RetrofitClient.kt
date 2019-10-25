package com.freehome.mamaison.retrofitClient


import com.freehome.mamaison.mamaisonapi.ApiServices
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val ENDPOINT = "http://192.168.1.116/mamaisonapi/v1/"

    fun getClient():ApiServices {

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val original = chain.request()
            //header
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .method(original.method(), original.body())
                .build()

            return@Interceptor chain.proceed(request)
        }).addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
                   .baseUrl(ENDPOINT)
                   .client(client)
                   .addConverterFactory(GsonConverterFactory.create(gson))
                   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                   .build()
        val services = retrofit.create(ApiServices::class.java)

        return services
    }
}