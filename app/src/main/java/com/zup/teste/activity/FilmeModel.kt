package com.zup.teste.activity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FilmeModel(

    @Expose
    @SerializedName("Title")
    val titulo: String,

    @Expose
    @SerializedName("Year")
    val ano: String,

    @Expose
    @SerializedName("Poster")
    val poster: String
)

data class FilmesModel(

    @Expose
    @SerializedName("Search")
    val busca: List<FilmeModel>
)
