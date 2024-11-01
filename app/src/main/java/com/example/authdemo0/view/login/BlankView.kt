package com.example.authdemo0.view.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BlankView(navController: NavController){
    LaunchedEffect (Unit){
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate("home")
        } else{
            navController.navigate("login")
        }
    }
}
