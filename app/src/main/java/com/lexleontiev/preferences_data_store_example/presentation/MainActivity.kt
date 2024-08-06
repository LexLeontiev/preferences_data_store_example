package com.lexleontiev.preferences_data_store_example.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.lexleontiev.preferences_data_store_example.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}