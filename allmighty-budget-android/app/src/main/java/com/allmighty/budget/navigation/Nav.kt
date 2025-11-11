package com.allmighty.budget.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.allmighty.budget.ui.screens.*

sealed class Route(val r:String){
    data object Login: Route("login")
    data object Register: Route("register")
    data object Dashboard: Route("dashboard")
}

@Composable
fun AppNav(nav: NavHostController){
    NavHost(navController = nav, startDestination = Route.Login.r){
        composable(Route.Login.r){ LoginScreen(nav) }
        composable(Route.Register.r){ RegisterScreen(nav) }
        composable(Route.Dashboard.r){ DashboardScreen() }
    }
}