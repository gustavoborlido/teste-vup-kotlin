package com.zup.teste.activity;

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.zup.teste.R
import com.zup.teste.fragment.MeusFilmesFragment
import com.zup.teste.fragment.TodosFilmesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val meusFilmesFragment = MeusFilmesFragment.newInstance()
        openFragment(meusFilmesFragment)

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.meus_filmes -> {

                val meusFilmesFragment = MeusFilmesFragment.newInstance()
                openFragment(meusFilmesFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.todos_filmes -> {

                val todosFilmesFragment = TodosFilmesFragment.newInstance()
                openFragment(todosFilmesFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}