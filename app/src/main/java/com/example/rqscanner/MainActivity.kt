
package com.example.rqscanner

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.rqscanner.Include.SQLHelper
import com.example.rqscanner.view.BottomNavigationLayout
import com.example.rqscanner.view.NavigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBarLayout()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Suppress("UNUSED_EXPRESSION")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarLayout() {
    val cntx = LocalContext.current
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationLayout(navController =  navController) }) {
        NavigationGraph(navController = navController, cntx)
    }
}







