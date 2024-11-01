package com.example.authdemo0.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authdemo0.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    var showAlert by mutableStateOf(false)

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){ task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        }else{
                            Log.e("LoginViewModel","Login falled",task.exception)
                            showAlert = true
                        }
                    }
            }catch (e: Exception){
                Log.e("LoginViewModel","Login falled", e)
                showAlert = true
            }
        }
    }

    fun register(email: String, password: String,userName: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){ task ->
                        if (task.isSuccessful) {
                            saverUser(userName)
                            onSuccess()
                        }else{
                            Log.e("LoginViewModel","Register falled",task.exception)
                            showAlert = true
                        }
                    }
            }catch (e: Exception){
                Log.e("LoginViewModel","Register falled", e)
                showAlert = true
            }
        }
    }

    private fun saverUser(userName: String){
        val id=auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = User(userId = id.toString(), email = email.toString(), name = userName)
            FirebaseFirestore.getInstance().collection("users")
                .add(user.topMap())
                .addOnCompleteListener(){ task ->
                    if(task.isSuccessful){
                        Log.d("LoginViewModel","User saved")
                    } else {
                        Log.e("LoginViewModel", "User mot saved", task.exception)
                    }
                }
        }
    }

    fun closeAlert(){
        showAlert = false
    }
}