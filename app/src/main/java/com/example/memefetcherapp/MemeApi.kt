package com.example.memefetcherapp

import retrofit2.Call
import retrofit2.http.GET

interface MemeApi {
    @GET("gimme")
    fun getMeme(): Call<MemeResponse>
}
