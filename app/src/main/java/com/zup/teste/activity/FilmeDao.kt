package com.zup.teste.activity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FilmeDao {

    @Query("SELECT * FROM FilmeModel")
    fun all(): List<FilmeModel>

    @Insert
    fun add(vararg filme: FilmeModel)

    @Query("SELECT * FROM FilmeModel WHERE FilmeModel.id = :id")
    fun findById(id: String): FilmeModel

    @Delete
    fun delete(vararg filme: FilmeModel)

}