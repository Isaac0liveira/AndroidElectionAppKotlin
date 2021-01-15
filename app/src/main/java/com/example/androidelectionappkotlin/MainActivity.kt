package com.example.androidelectionappkotlin

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val candidatos = mutableListOf<Candidato>()
    private val candidatosFilter = mutableListOf<Candidato>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        candidatos.addAll(CandidatoDAO(this).obterLista())
        candidatos.sortedBy { candidato -> candidato.votos.toInt() }
        candidatosFilter.addAll(candidatos)
        candidatosFilter.sortedBy { candidato -> candidato.votos.toInt() }
        val adapter = CandidatoAdapter(this, candidatosFilter)
        listView.adapter = adapter
        registerForContextMenu(listView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_layout, menu)
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        MenuInflater(this).inflate(R.menu.context_menu, menu)
    }

    fun adicionar(item: MenuItem){
        startActivity(Intent(this, AdicionarActivity::class.java))
    }

    fun votar(item: MenuItem){
        startActivity(Intent(this, VotarActivity::class.java))
    }

    fun atualizar(item: MenuItem){
        val info: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val candidato: Candidato = candidatos.get(info.position)
        startActivity(Intent(this, AdicionarActivity::class.java).putExtra("candidato", candidato))
    }

    fun excluir(item: MenuItem){
        val info: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val candidato: Candidato = candidatos.get(info.position)
        AlertDialog.Builder(this).setTitle("Atenção").setMessage("Deseja realmente excluir este candidato?").setNegativeButton("Não", null).
                setPositiveButton("Sim") { _: DialogInterface, i: Int ->
                    CandidatoDAO(this).excluir(candidato)
                    candidatos.clear()
                    candidatosFilter.clear()
                    candidatos.addAll(CandidatoDAO(this).obterLista())
                    candidatosFilter.addAll(candidatos)
                    listView.invalidateViews()
                }.show()
    }

    override fun onResume() {
        super.onResume()
        candidatos.clear()
        candidatosFilter.clear()
        candidatos.addAll(CandidatoDAO(this).obterLista())
        candidatos.sortedBy { candidato -> candidato.votos.toInt() }
        candidatosFilter.addAll(candidatos)
        candidatosFilter.sortedBy { candidato -> candidato.votos.toInt() }
        listView.invalidateViews()
    }


}