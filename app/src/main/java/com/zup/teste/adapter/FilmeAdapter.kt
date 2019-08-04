package com.zup.teste.adapter


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zup.teste.R
import com.zup.teste.model.FilmeModel

class FilmeAdapter(private var dataList: List<FilmeModel>, private val context: Context) : RecyclerView.Adapter<FilmeAdapter.ViewHolder>() {


    interface ItemClick
    {
        fun onClick(filme: FilmeModel)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_filme_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filmeModel = dataList.get(position)

        holder.titulo.text = filmeModel.titulo
        holder.ano.text = filmeModel.ano
        Glide.with(context).load(filmeModel.poster).placeholder(R.drawable.image).into(holder.poster)

        if(itemClick != null)
        {
            holder?.itemView?.setOnClickListener { v ->
                itemClick?.onClick(dataList.get(position))
            }
        }

    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var titulo: TextView
        var ano: TextView
        var poster: ImageView
        init {
            titulo = itemLayoutView.findViewById(R.id.titulo)
            ano = itemLayoutView.findViewById(R.id.ano)
            poster = itemLayoutView.findViewById(R.id.poster)

        }

    }

}