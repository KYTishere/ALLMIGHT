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
import com.allmighty.budget.vm.BudgetViewModel

@Composable
fun LoginScreen(nav: NavController){
    val auth: AuthViewModel = viewModel()
    val budget: BudgetViewModel = viewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val user by auth.user.collectAsState()

    LaunchedEffect(user){
        user?.let { u ->
            budget.setUser(u.id)
            nav.navigate(Route.Dashboard.r){ popUpTo(Route.Login.r){ inclusive = true } }
        }
    }

    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center){
        Text("Allmighty Budget", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value=username, onValueChange={username=it}, label={ Text("Username") }, singleLine=true)
        OutlinedTextField(value=password, onValueChange={password=it}, label={ Text("Password") }, singleLine=true, visualTransformation=PasswordVisualTransformation())
        Spacer(Modifier.height(12.dp))
        Button(onClick={ auth.login(username,password) }, modifier=Modifier.fillMaxWidth()){ Text("Login") }
        TextButton(onClick={ nav.navigate(Route.Register.r) }, modifier=Modifier.fillMaxWidth()){ Text("Create account") }
    }
}