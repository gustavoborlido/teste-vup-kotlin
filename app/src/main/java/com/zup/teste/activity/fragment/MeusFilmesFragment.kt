package com.zup.teste.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.zup.teste.R
import com.zup.teste.activity.AppDatabase
import com.zup.teste.activity.FilmePagerAdapter
import com.zup.teste.activity.FilmeModel
import com.zup.teste.activity.RefreshListInterface

class MeusFilmesFragment : RefreshListInterface, androidx.fragment.app.Fragment() {

    var viewPager: ViewPager? = null
    var db: AppDatabase? = null
    var filmes: ArrayList<FilmeModel>? = null
    var filmePagerAdapter: FilmePagerAdapter? = null
    var deslize: TextView? = null
    var naoHaRegistros: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_meus_filmes, container, false)

        deslize = view.findViewById(R.id.deslize)
        naoHaRegistros = view.findViewById(R.id.naoHaRegistros)

        viewPager = view.findViewById(R.id.pager)
        db = AppDatabase.getAppDataBase(context!!)
        filmes = db!!.filmeDao().all() as ArrayList<FilmeModel>
        filmePagerAdapter = FilmePagerAdapter(context!!, filmes!!, this)
        viewPager!!.adapter = filmePagerAdapter

        possuiOuNaoRegistros()

        return view
    }

    override fun refreshList() {
        filmes!!.clear()
        filmes!!.addAll(db!!.filmeDao().all())
        viewPager!!.adapter!!.notifyDataSetChanged()
        possuiOuNaoRegistros()

    }

    fun possuiOuNaoRegistros(){

        if(filmes != null && filmes!!.size > 0){
            viewPager!!.visibility = View.VISIBLE
            deslize!!.visibility = View.VISIBLE
            naoHaRegistros!!.visibility = View.GONE
        }else{
            viewPager!!.visibility = View.GONE
            deslize!!.visibility = View.GONE
            naoHaRegistros!!.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance(): MeusFilmesFragment = MeusFilmesFragment()
    }
}