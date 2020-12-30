package com.shayan.qrnfcscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import butterknife.ButterKnife
import com.shayan.qrnfcscanner.viewModel.CodeListViewModel

class MainActivity : AppCompatActivity() {

    // This element contains our graph logic
    private lateinit var navController: NavController

    private lateinit var viewModel: CodeListViewModel


    // region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        initViewModel()

        navController = findNavController(R.id.nav_host_fragment_container)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun initViewModel() {
        this.viewModel = ViewModelProvider(this).get(CodeListViewModel::class.java)
    }

    fun getViewModel() : CodeListViewModel {
        return viewModel
    }

}