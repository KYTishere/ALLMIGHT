package com.allmighty.budget.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.allmighty.budget.navigation.Route
import com.allmighty.budget.vm.AuthViewModel

@Composable
fun RegisterScreen(nav: NavController){
    val auth: AuthViewModel = viewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center){
        Text("Create account", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value=username, onValueChange={username=it}, label={ Text("Username") }, singleLine=true)
        OutlinedTextField(value=password, onValueChange={password=it}, label={ Text("Password") }, singleLine=true, visualTransformation=PasswordVisualTransformation())
        OutlinedTextField(value=pin, onValueChange={ if(it.length<=4) pin=it.filter { c-> c.isDigit() } }, label={ Text("4-digit PIN (optional)") }, singleLine=true)
        Spacer(Modifier.height(12.dp))
        Button(onClick={ auth.register(username,password, pin.ifBlank{null}); nav.navigate(Route.Login.r){ popUpTo(Route.Login.r){inclusive=true} } }, modifier=Modifier.fillMaxWidth()){
            Text("Register")
        }
    }
}