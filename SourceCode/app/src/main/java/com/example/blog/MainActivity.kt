package com.example.blog


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavBar)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

        if (currentFragmentTag == "fragment1" || currentFragmentTag == "fragment2" || currentFragmentTag == "fragment3") {

            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }

        supportActionBar?.hide()

    }
    fun setCurrentFragmentTag(fragmentTag: String) {
        currentFragmentTag = fragmentTag
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavBar)

        if (currentFragmentTag == "fragment1" || currentFragmentTag == "fragment2" || currentFragmentTag == "fragment3") {
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }
    }
}