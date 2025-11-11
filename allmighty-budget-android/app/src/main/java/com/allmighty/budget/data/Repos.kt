package com.allmighty.budget.data

import kotlinx.coroutines.flow.Flow

class Repo(private val db: AppDatabase) {
    suspend fun register(username: String, password: String, pin: String?): Long {
        require(username.isNotBlank() && password.isNotBlank())
        val passHash = Security.hashPassword(password)
        val pinHash = pin?.takeIf { it.length==4 }?.let { Security.hashPin(it) }
        val id = db.userDao().insert(User(username = username.trim(), passwordHash = passHash, pinHash = pinHash))
        listOf("Food","Travel","Bills","Shopping","Health","Education","Savings").forEach {
            db.categoryDao().insert(Category(userId = id, name = it))
        }
        return id
    }
    suspend fun login(username: String, password: String): User? {
        val u = db.userDao().byUsername(username.trim()) ?: return null
        return if (Security.verifyPassword(password, u.passwordHash)) u else null
    }
    fun categories(uid: Long): Flow<List<Category>> = db.categoryDao().list(uid)
    fun transactions(uid: Long): Flow<List<Transaction>> = db.txDao().list(uid)
    fun goals(uid: Long): Flow<List<Goal>> = db.goalDao().list(uid)
    suspend fun addTx(t: Transaction) = db.txDao().insert(t)
    suspend fun addCategory(uid: Long, name: String) = db.categoryDao().insert(Category(userId = uid, name = name))
    suspend fun addGoal(uid: Long, name: String, target: Long, period: GoalPeriod) = db.goalDao().insert(Goal(userId = uid, name = name, target = target, period = period))
    suspend fun contribGoal(g: Goal, add: Long) = db.goalDao().update(g.copy(contrib = g.contrib + add))
}