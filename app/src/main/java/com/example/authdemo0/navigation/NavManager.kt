package com.example.authdemo0.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authdemo0.view.login.BlankView
import com.example.authdemo0.view.login.TabsView
import com.example.authdemo0.view.notas.HomeView
import com.example.authdemo0.viewmodel.LoginViewModel
import com.example.authdemo0.viewmodel.NotesViewModel


@Composable
fun NavManager(loginViewModel: LoginViewModel,
               notesViewModel: NotesViewModel){
    val navController = rememberNavController()
    NavHost(navController= navController, startDestination = "blank"){
        composable("blank"){
            BlankView(navController = navController)
        }
        composable("login") {
            TabsView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("home") {
            HomeView(navController = navController, viewModel = notesViewModel)
        }
    }
}