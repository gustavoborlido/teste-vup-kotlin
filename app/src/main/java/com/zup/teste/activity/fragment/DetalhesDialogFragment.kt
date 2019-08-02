package com.zup.teste.activity.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.zup.teste.R
import com.zup.teste.activity.ApiClient
import com.zup.teste.activity.FilmeModel
import com.zup.teste.activity.FilmesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesDialogFragment : DialogFragment() {

    var filme: FilmeModel? = null
    var poster: ImageView? = null
    var enredo: TextView? = null
    var atores: TextView? = null
    var diretores: TextView? = null
    var title: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detalhes_dialog, container)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val toolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {
           dismiss()
        }

        poster = view.findViewById(R.id.poster)
        enredo = view.findViewById(R.id.enredo)
        atores = view.findViewById(R.id.atores)
        diretores = view.findViewById(R.id.diretores)
        title = view.findViewById(R.id.toolbar_title)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val call: Call<FilmeModel> = ApiClient.getClient.getFilme(arguments!!.getString("idFilme")!!)
        call.enqueue(object : Callback<FilmeModel> {

            override fun onResponse(call: Call<FilmeModel>?, response: Response<FilmeModel>?) {

                if(response!!.body() != null){
                    filme = response!!.body() as FilmeModel
                    Glide.with(context!!).load(filme!!.poster).placeholder(R.drawable.image).into(poster!!)
//                    (activity as AppCompatActivity).supportActionBar?.title = filme!!.titulo + " (" + filme!!.ano + ")"
                    title!!.text = filme!!.titulo + " (" + filme!!.ano + ")"

                    enredo!!.text = "Enredo:  " + filme!!.enredo
                    atores!!.text = "Atores:  " + filme!!.atores
                    diretores!!.text = "Diretores:  " + filme!!.diretores
                }
            }

            override fun onFailure(call: Call<FilmeModel>?, t: Throwable?) {
                Log.d("TAG", "ERROR")
            }
        })
    }

    companion object {
        fun newInstance(idFilme: String): DetalhesDialogFragment {
            val frag = DetalhesDialogFragment()
            val args = Bundle()
            args.putString("idFilme", idFilme)
            frag.arguments = args
            return frag
        }
    }

    override fun onResume() {
        super.onResume()
        val window = dialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }

}