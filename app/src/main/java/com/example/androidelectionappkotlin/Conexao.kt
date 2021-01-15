package com.example.androidelectionappkotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val nome = "banco"

class Conexao(context: Context) : SQLiteOpenHelper(context, nome, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table candidato(id integer primary key autoincrement, nome varchar(50), numero integer, votos integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}
