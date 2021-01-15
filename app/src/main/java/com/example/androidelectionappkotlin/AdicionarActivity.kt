package com.example.androidelectionappkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_adicionar.*

class AdicionarActivity : AppCompatActivity() {

    lateinit var candidato: Candidato
    val candidatos = mutableListOf<Candidato>()
    var same = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar)
        candidatos.addAll(CandidatoDAO(this).obterLista())
        if(this.intent.getSerializableExtra("candidato") != null){
            candidato = intent.getSerializableExtra("candidato") as Candidato
            txtNome.setText(candidato.nome)
            txtNumero.setText(candidato.numero.toString())
            same = candidato.id as Int
        }
    }

    fun confirmar(view: View){
        var error = 0
        for (c: Candidato in candidatos) {
            if (txtNome.text.toString().equals(c.nome) && c.id != same) {
                error = 1
            }
            if (txtNumero.getText().toString().toInt() == c.numero && c.id != same) {
                if (error == 0) {
                    error = 2
                } else {
                    error = 3
                }
            }
            if(c.id == same && txtNome.text.toString().equals(c.nome)){
                error = 4
            }
            if(c.id == same && txtNumero.getText().toString().toInt() == c.numero){
                if(error == 4){
                    error = 6
                }else {
                    error = 5
                }
            }
        }
        if(error == 1){
            txtNome.setText("")
            txtNome.setHint("Este nome já existe!")
            return;
        }else if(error == 2){
            txtNumero.setText("")
            txtNumero.setHint("Número pertence a um candidato existente!")
            return;
        }else if(error == 3) {
            txtNome.setText("");
            txtNome.setHint("Este nome já existe!")
            txtNumero.setText("");
            txtNumero.setHint("Número pertence a um candidato existente!")
            return;
        }else if(error == 4) {
            txtNome.setText("");
            txtNome.setHint("Este nome já é seu!")
            return;
        }else if(error == 5){
            txtNumero.setText("");
            txtNumero.setHint("Este número já é seu!")
            return
        }else if(error == 6){
            txtNome.setText("");
            txtNome.setHint("Este nome já é seu!")
            txtNumero.setText("");
            txtNumero.setHint("Este número já é seu!")
            return
        }else if(error == 0) {
                if (!this::candidato.isInitialized) {
                    val candidato = Candidato()
                    candidato.nome = txtNome.text.toString()
                    candidato.numero = Integer.parseInt(txtNumero.text.toString())
                    val id = CandidatoDAO(this).inserir(candidato)
                    Toast.makeText(this, "Candidato inserido com ID: $id", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    candidato.nome = txtNome.text.toString()
                    candidato.numero = txtNumero.text.toString().toInt()
                    CandidatoDAO(this).atualizar(candidato)
                    Toast.makeText(this, "Candidato atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
    }
}