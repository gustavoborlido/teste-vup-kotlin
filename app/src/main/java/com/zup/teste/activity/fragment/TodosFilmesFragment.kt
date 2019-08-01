package com.zup.teste.activity.fragment

import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zup.teste.R
import com.zup.teste.activity.ApiClient
import com.zup.teste.activity.FilmeAdapter
import com.zup.teste.activity.FilmeModel
import com.zup.teste.activity.FilmesModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodosFilmesFragment : Fragment() {

    var dataList = ArrayList<FilmeModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FilmeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_todos_filmes, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.adapter= FilmeAdapter(dataList, context!!)
        recyclerView.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)



        val call: Call<FilmesModel> = ApiClient.getClient.getFilmes()
        call.enqueue(object : Callback<FilmesModel> {

            override fun onResponse(call: Call<FilmesModel>?, response: Response<FilmesModel>?) {
                dataList.addAll(response!!.body()!!.busca as ArrayList<FilmeModel>)
                recyclerView.adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<FilmesModel>?, t: Throwable?) {

                Log.d("TAG", "ERROR")
            }

        })

        return view
    }


    companion object {
        fun newInstance(): TodosFilmesFragment = TodosFilmesFragment()
    }
}
