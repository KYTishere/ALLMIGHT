package com.allmighty.budget.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.allmighty.budget.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class BudgetViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repo(AppDatabase.get(app))
    private val _uid = MutableStateFlow<Long?>(null)
    val uid = _uid.asStateFlow()

    val categories = uid.filterNotNull().flatMapLatest { repo.categories(it) }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val transactions = uid.filterNotNull().flatMapLatest { repo.transactions(it) }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val goals = uid.filterNotNull().flatMapLatest { repo.goals(it) }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setUser(id: Long?){ _uid.value = id }

    fun addTx(type: TxType, amount: Long, categoryId: Long?, note: String?, date: LocalDate){
        val id = _uid.value ?: return
        viewModelScope.launch { repo.addTx(Transaction(userId=id, type=type, amount=amount, categoryId=categoryId, note=note, date=date)) }
    }
    fun addCategory(name: String){
        val id = _uid.value ?: return
        viewModelScope.launch { repo.addCategory(id, name) }
    }
    fun addGoal(name: String, target: Long, period: GoalPeriod){
        val id = _uid.value ?: return
        viewModelScope.launch { repo.addGoal(id, name, target, period) }
    }
    fun contribGoal(g: Goal, add: Long){ viewModelScope.launch { repo.contribGoal(g, add) } }
}