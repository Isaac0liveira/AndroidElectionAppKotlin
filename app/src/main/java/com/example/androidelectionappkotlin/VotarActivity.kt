package com.example.androidelectionappkotlin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_votar.*
import java.util.*
import kotlin.concurrent.schedule

class VotarActivity : AppCompatActivity() {
    private val candidatos = mutableListOf<Candidato>()
    private lateinit var candidatoSelecionado: Candidato
    private lateinit var timer: TimerTask

    fun pesquisar(s: String){
        runOnUiThread {
            for(c: Candidato in candidatos){
                if(c.numero.equals(Integer.parseInt(s))){
                    candidatoSelecionado = c
                    votarNome.setText(c.nome)
                    votarButton.visibility = View.VISIBLE
                }else{
                    votarNome.setText("Candidato não encontrado")
                    votarButton.visibility = View.INVISIBLE
                    candidatoSelecionado = Candidato()
                }
            }
        }
    }

    fun votar(view: View){
        CandidatoDAO(this).votar(candidatoSelecionado)
        Toast.makeText(this, "Voto computado com sucesso!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votar)
        candidatos.addAll(CandidatoDAO(this).obterLista())
        votarNumero.addTextChangedListener(object : TextWatcher {
            var timeRun = false;

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val string = s.toString()
                if (string.length in 1..4) {
                    if (!timeRun) {
                        timer = Timer("timer", false).schedule(2000) {
                            pesquisar(string)
                        }
                        timeRun = true
                    } else {
                        timer.cancel()
                        timer = Timer("timer", false).schedule(2000) {
                            pesquisar(string)
                        }
                    }
                }else if(string.length > 5){
                    runOnUiThread{
                        votarNumero.setText("")
                        votarNumero.setHint("Inválido")
                    }
                }
            }

        })
    }
}