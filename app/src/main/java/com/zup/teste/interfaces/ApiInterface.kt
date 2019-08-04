package com.zup.teste.interfaces

import com.zup.teste.model.FilmeModel
import com.zup.teste.model.FilmesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("?apikey=ed42b924")
    fun getFilmes(@Query("s") nome: String): Call<FilmesModel>

    @GET("?apikey=ed42b924")
    fun getFilme(@Query("i") nome: String): Call<FilmeModel>

}