package com.zup.teste.fragment

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.zup.teste.R
import com.zup.teste.utilities.ApiClient
import com.zup.teste.adapter.FilmeAdapter
import com.zup.teste.model.FilmeModel
import com.zup.teste.model.FilmesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodosFilmesFragment : androidx.fragment.app.Fragment() {

    var dataList = ArrayList<FilmeModel>()
    lateinit var recyclerView: RecyclerView
    var pesquisar: EditText? = null
    var pesquisarBtn: ImageButton? = null
    var semRegistros: TextView? = null
    var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_todos_filmes, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)

        var adapter = FilmeAdapter(dataList, context!!)
        adapter.itemClick = object: FilmeAdapter.ItemClick {
            override fun onClick(filme: FilmeModel) {
                val fm: FragmentManager? = fragmentManager
                val detalhesDialogFragment: DetalhesDialogFragment = DetalhesDialogFragment.newInstance(filme.id, null)
                detalhesDialogFragment.show(fm, "detalhes_fragment")
            }
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        pesquisar = view.findViewById(R.id.pesquisar)
        pesquisarBtn = view.findViewById(R.id.btn_pesquisar)
        semRegistros = view.findViewById(R.id.semRegistros)
        progressBar = view.findViewById(R.id.progressBar)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))


        pesquisarBtn!!.setOnClickListener {

            var texto = pesquisar!!.text.toString()

            if(!texto.isEmpty()){
               buscarFilmes(texto)
            }
        }


        pesquisar!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                var texto = pesquisar!!.text.toString()

                if(!texto.isEmpty()){
                    buscarFilmes(texto)
                }

                return@OnKeyListener true
            }
            false
        })

        return view
    }


    fun buscarFilmes(texto: String){

        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        progressBar!!.visibility = View.VISIBLE
        semRegistros!!.visibility = View.GONE
        recyclerView!!.visibility = View.GONE

        val call: Call<FilmesModel> = ApiClient.getClient.getFilmes(texto)
        call.enqueue(object : Callback<FilmesModel> {

            override fun onResponse(call: Call<FilmesModel>?, response: Response<FilmesModel>?) {

                if(response!!.body()!!.busca != null){
                    dataList.clear()
                    dataList.addAll(response!!.body()!!.busca as ArrayList<FilmeModel>)
                    recyclerView.adapter!!.notifyDataSetChanged()

                    semRegistros!!.visibility = View.GONE
                    recyclerView!!.visibility = View.VISIBLE
                    progressBar!!.visibility = View.GONE
                }else{
                    semRegistros!!.visibility = View.VISIBLE
                    recyclerView!!.visibility = View.GONE
                    progressBar!!.visibility = View.GONE

                }
            }

            override fun onFailure(call: Call<FilmesModel>?, t: Throwable?) {
                semRegistros!!.visibility = View.VISIBLE
                recyclerView!!.visibility = View.GONE
                progressBar!!.visibility = View.GONE
                Log.d("TAG", "ERROR")
            }
        })
    }


    companion object {
        fun newInstance(): TodosFilmesFragment = TodosFilmesFragment()
    }
}
