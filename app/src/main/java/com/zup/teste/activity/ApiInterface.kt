package com.zup.teste.activity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("?apikey=ed42b924")
    fun getFilmes(@Query("s") nome: String): Call<FilmesModel>

}