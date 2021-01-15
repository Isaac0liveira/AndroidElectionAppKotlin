package com.example.androidelectionappkotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build.ID
import android.util.Log
import java.text.NumberFormat

class CandidatoDAO(context: Context) {
    private val conexao = Conexao(context)
    private val banco: SQLiteDatabase = conexao.writableDatabase
    fun inserir(candidato: Candidato): Number{
        val content = ContentValues()
        content.put("nome", candidato.nome)
        content.put("numero", candidato.numero.toInt())
        content.put("votos", 0)
        return banco.insert("Candidato", null, content)
    }

    fun obterLista(): List<Candidato>{
        val candidatos = mutableListOf<Candidato>()
        var cursor: Cursor = banco.rawQuery("select * from Candidato", null)
        while(cursor.moveToNext()) run {
            val candidato = Candidato()
            candidato.id = cursor.getInt(0)
            candidato.nome = cursor.getString(1).toString()
            candidato.numero = cursor.getInt(2)
            candidato.votos = cursor.getInt(3)
            candidatos.add(candidato)
            Log.d("nome", candidato.nome)
        }
        return candidatos
    }

    fun votar(candidato: Candidato) {
        val content = ContentValues()
        content.put("votos", candidato.votos.toInt() + 1)
        banco.update("Candidato", content, "id = ?", arrayOf(candidato.id.toString()))
    }

    fun atualizar(candidato: Candidato){
        val content = ContentValues()
        content.put("nome", candidato.nome)
        content.put("numero", candidato.numero.toInt())
        Log.d("voto", candidato.votos.toString())
        banco.update("Candidato", content, "id = ?", arrayOf(candidato.id.toString()))
    }

    fun excluir(candidato: Candidato){
        banco.delete("Candidato", "id = ?", arrayOf(candidato.id.toString()))
    }
}