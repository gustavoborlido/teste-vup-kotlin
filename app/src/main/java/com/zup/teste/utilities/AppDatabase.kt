package com.zup.teste.utilities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zup.teste.dao.FilmeDao
import com.zup.teste.model.FilmeModel

@Database(entities = [FilmeModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmeDao(): FilmeDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}
