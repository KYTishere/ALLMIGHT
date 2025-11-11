package com.allmighty.budget.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.allmighty.budget.data.*
import com.allmighty.budget.vm.BudgetViewModel

@Composable
fun DashboardScreen(){
    val vm: BudgetViewModel = viewModel()
    val txs by vm.transactions.collectAsState()
    val cats by vm.categories.collectAsState()
    val goals by vm.goals.collectAsState()

    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick={ /* Chatbot actions */ }, text={Text("Chatbot")})
    }){ pad ->
        Column(Modifier.fillMaxSize().padding(pad).padding(16.dp)){
            Text("Allmighty Budget", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            val income = txs.filter{it.type==TxType.INCOME}.sumOf { it.amount }
            val expense = txs.filter{it.type==TxType.EXPENSE}.sumOf { it.amount }
            val savings = txs.filter{it.type==TxType.SAVINGS}.sumOf { it.amount }
            val balance = income - expense - savings

            Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(8.dp)){
                StatCard("Income", income)
                StatCard("Expense", expense)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(8.dp)){
                StatCard("Savings", savings)
                StatCard("Balance", balance)
            }

            Spacer(Modifier.height(8.dp))
            Text("Recent", style = MaterialTheme.typography.titleLarge)
            LazyColumn(Modifier.weight(1f)){
                items(txs.take(20)){ t ->
                    val cat = cats.find{it.id==t.categoryId}?.name ?: "—"
                    ListItem(headlineContent={ Text("₹${t.amount}") }, supportingContent={ Text("${t.type} · ${t.date} · ${t.note ?: ""}") }, trailingContent={ Text(cat) })
                    Divider()
                }
            }
        }
    }
}

@Composable private fun StatCard(label: String, value: Long){
    ElevatedCard(Modifier.weight(1f)){
        Column(Modifier.padding(12.dp)){
            Text(label, style=MaterialTheme.typography.labelLarge)
            Text("₹$value", style=MaterialTheme.typography.headlineSmall)
        }
    }
}