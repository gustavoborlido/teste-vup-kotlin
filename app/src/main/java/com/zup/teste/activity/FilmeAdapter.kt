package com.zup.teste.activity


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zup.teste.R

class FilmeAdapter(private var dataList: List<FilmeModel>, private val context: Context) : RecyclerView.Adapter<FilmeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_filme_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filmeModel=dataList.get(position)

        holder.titleTextView.text = filmeModel.titulo
    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        lateinit var titleTextView:TextView
        init {
            titleTextView=itemLayoutView.findViewById(R.id.title)

        }

    }

}