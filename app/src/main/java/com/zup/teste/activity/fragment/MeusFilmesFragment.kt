package com.zup.teste.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zup.teste.R

class MeusFilmesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_meus_filmes, container, false)

    companion object {
        fun newInstance(): MeusFilmesFragment = MeusFilmesFragment()
    }
}