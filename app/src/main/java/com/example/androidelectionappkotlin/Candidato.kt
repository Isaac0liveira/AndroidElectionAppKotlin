package com.example.androidelectionappkotlin

import java.io.Serializable

class Candidato : Serializable{
    lateinit var nome: String
    lateinit var numero: Number
    lateinit var votos: Number
    lateinit var id: Number

    override fun toString() = nome
}