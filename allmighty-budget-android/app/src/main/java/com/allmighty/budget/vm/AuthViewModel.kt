package com.allmighty.budget.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.allmighty.budget.data.AppDatabase
import com.allmighty.budget.data.Repo
import com.allmighty.budget.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repo(AppDatabase.get(app))
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun register(username: String, password: String, pin: String?) = viewModelScope.launch {
        val id = repo.register(username, password, pin)
        _user.value = User(id=id, username=username, passwordHash="", pinHash=null)
    }
    fun login(username: String, password: String) = viewModelScope.launch {
        _user.value = repo.login(username, password)
    }
    fun logout(){ _user.value = null }
}