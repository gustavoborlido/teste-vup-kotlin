package com.zup.teste.activity

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("?s=Game of&apikey=ed42b924")

    fun getFilmes(): Call<FilmesModel>

}