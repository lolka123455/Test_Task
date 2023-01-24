package com.example.testtask.effective

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.testtask.effective.databinding.ActivityMainBinding

/**
 * This is the main activity of the app. It is responsible for inflating the layout and setting up the navigation host fragment.
 *
 * @constructor Creates a new instance of the MainActivity
 *
 * @property navHostFragment the navigation host fragment used to handle app navigation
 *
 * @see androidx.appcompat.app.AppCompatActivity
 * @see androidx.navigation.fragment.NavHostFragment
 *
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * The navigation host fragment used to handle app navigation
     *
     * @see NavHostFragment
     *
     */

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }
}