package com.allmighty.budget.data

import androidx.room.*
import java.time.LocalDate

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "username") val username: String,
    val passwordHash: String,
    val pinHash: String? = null
)

enum class TxType { INCOME, EXPENSE, SAVINGS }
enum class GoalPeriod { MONTHLY, QUARTERLY, YEARLY, CUSTOM }

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val name: String
)

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val type: TxType,
    val amount: Long,
    val categoryId: Long?,
    val note: String? = null,
    val date: LocalDate = LocalDate.now()
)

@Entity
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val name: String,
    val target: Long,
    val period: GoalPeriod = GoalPeriod.MONTHLY,
    val contrib: Long = 0
)