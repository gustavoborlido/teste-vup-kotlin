package com.zup.teste.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.zup.teste.R
import com.zup.teste.interfaces.RefreshListInterface
import com.zup.teste.model.FilmeModel
import com.zup.teste.utilities.ApiClient
import com.zup.teste.utilities.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesDialogFragment(var refreshList: RefreshListInterface?) : DialogFragment() {

    var filme: FilmeModel? = null
    var poster: ImageView? = null
    var enredo: TextView? = null
    var atores: TextView? = null
    var diretores: TextView? = null
    var title: TextView? = null
    var acao: ImageView? = null
    var db: AppDatabase? = null
    var filmeSalvo: Boolean = false
    var progressbar: ProgressBar? = null
    var scrollView: ScrollView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detalhes_dialog, container)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val toolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        db = AppDatabase.getAppDataBase(context!!)

        poster = view.findViewById(R.id.poster)
        enredo = view.findViewById(R.id.enredo)
        atores = view.findViewById(R.id.atores)
        diretores = view.findViewById(R.id.diretores)
        title = view.findViewById(R.id.toolbar_title)
        acao = view.findViewById(R.id.acao)
        progressbar = view.findViewById(R.id.progressBar)
        scrollView = view.findViewById(R.id.scrollView)

        toolbar.setNavigationOnClickListener {
           dismiss()
        }

        acao!!.setOnClickListener {

            if(filmeSalvo){

                val builder = AlertDialog.Builder(it.context)
                builder.setTitle("Excluir filme?")
                builder.setMessage("Tem certeza que deseja excluir este filme?")
                builder.setPositiveButton("SIM"){dialog, which ->

                    db!!.filmeDao().delete(filme!!)
                    Toast.makeText(it.context, "Filme excluído com sucesso!", Toast.LENGTH_LONG).show()
                    if(refreshList != null)
                        refreshList!!.refreshList()
                }
                builder.setNegativeButton("NÃO"){dialog,which ->
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()

            }else{
                db!!.filmeDao().add(filme!!)
                Toast.makeText(context, "Filme salvo com sucesso!", Toast.LENGTH_LONG).show()
            }

            dismiss()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filme = db!!.filmeDao().findById(arguments!!.getString("idFilme")!!)

        if(filme == null){

            filmeSalvo = false

            acao!!.setImageResource(R.drawable.ic_save_36dp)

            val call: Call<FilmeModel> = ApiClient.getClient.getFilme(arguments!!.getString("idFilme")!!)
            call.enqueue(object : Callback<FilmeModel> {

                override fun onResponse(call: Call<FilmeModel>?, response: Response<FilmeModel>?) {

                    if(response!!.body() != null){
                        filme = response!!.body() as FilmeModel
                        exibirFilme()

                    }
                }

                override fun onFailure(call: Call<FilmeModel>?, t: Throwable?) {
                    Log.d("TAG", "ERROR")
                }
            })
        } else {
            filmeSalvo = true
            acao!!.setImageResource(R.drawable.ic_delete_36dp)
            exibirFilme()
        }



    }

    fun exibirFilme(){

        Glide.with(context!!).load(filme!!.poster).placeholder(R.drawable.image).into(poster!!)
        title!!.text = filme!!.titulo + " (" + filme!!.ano + ")"
        enredo!!.text = "Enredo:  " + filme!!.enredo
        atores!!.text = "Atores:  " + filme!!.atores
        diretores!!.text = "Diretores:  " + filme!!.diretores

        progressbar!!.visibility = View.GONE
        scrollView!!.visibility = View.VISIBLE
        acao!!.visibility = View.VISIBLE

    }

    companion object {
        fun newInstance(idFilme: String, refreshList: RefreshListInterface?): DetalhesDialogFragment {

            val frag = DetalhesDialogFragment(refreshList)
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