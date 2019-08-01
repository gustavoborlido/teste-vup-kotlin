package com.zup.teste.activity;

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.zup.teste.R
import com.zup.teste.activity.fragment.MeusFilmesFragment
import com.zup.teste.activity.fragment.TodosFilmesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

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

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}