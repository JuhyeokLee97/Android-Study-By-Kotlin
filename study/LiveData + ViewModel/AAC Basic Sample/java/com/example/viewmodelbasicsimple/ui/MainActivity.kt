package com.example.viewmodelbasicsimple.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.viewmodelbasicsimple.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = ProductListFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment).commit()
    }
}