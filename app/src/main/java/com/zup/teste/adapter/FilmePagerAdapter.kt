package com.zup.teste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.zup.teste.R
import com.zup.teste.fragment.DetalhesDialogFragment
import androidx.appcompat.app.AppCompatActivity
import com.zup.teste.model.FilmeModel
import com.zup.teste.interfaces.RefreshListInterface

class FilmePagerAdapter(var mContext: Context, var filmes: List<FilmeModel>, var refreshList: RefreshListInterface) : PagerAdapter() {
    var mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return this.filmes.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.list_meus_filmes_item, container, false)

        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val titulo = itemView.findViewById<TextView>(R.id.titulo)

        Glide.with(mContext).load(filmes[position].poster).placeholder(R.drawable.image).into(imageView)
        titulo.text = filmes[position].titulo + " (" + filmes[position].ano + ")"

        container.addView(itemView)

        itemView.setOnClickListener {

            val fm: FragmentManager? = (mContext as AppCompatActivity).supportFragmentManager
            val detalhesDialogFragment: DetalhesDialogFragment = DetalhesDialogFragment.newInstance(filmes[position].id, refreshList)
            detalhesDialogFragment.show(fm, "detalhes_fragment")
        }

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

}
