package com.example.androidelectionappkotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CandidatoAdapter(var activity: MainActivity, var candidatos: MutableList<Candidato>) : BaseAdapter() {

    override fun getCount(): Int {
        return candidatos.size
    }

    override fun getItem(position: Int): Any {
        return candidatos.get(position)
    }

    override fun getItemId(position: Int): Long {
        return candidatos.get(position).id.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = activity.layoutInflater.inflate(R.layout.item, parent, false)
        view.findViewById<TextView>(R.id.itemNome).text = candidatos.get(position).nome
        view.findViewById<TextView>(R.id.itemNumero).text = candidatos.get(position).numero.toString()
        view.findViewById<TextView>(R.id.itemVoto).text = candidatos.get(position).votos.toString()
        return view
    }
}