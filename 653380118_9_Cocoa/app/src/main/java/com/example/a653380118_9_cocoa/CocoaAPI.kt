package com.example.a653380118_9_cocoa

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CocoaAPI {
    @GET("api/cocoadrink")
    fun retrieveCocoa(): Call<List<Cocoa>>

    @FormUrlEncoded
    @POST("api/cocoadrink")
    fun insertCocoa(
        @Field("customer") customer: String,
        @Field("glass_size") glass_size: String,
        @Field("number_of_glass") number_of_glass: Int,
        @Field("sweet") sweet: Int,
        @Field("price") price: Int
    ): Call<Cocoa>

    companion object{
        fun create(): CocoaAPI {
            val cocoaClient: CocoaAPI = Retrofit.Builder()
                .baseUrl("http://10.199.121.133:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CocoaAPI::class.java)
            return cocoaClient
        }
    }
}