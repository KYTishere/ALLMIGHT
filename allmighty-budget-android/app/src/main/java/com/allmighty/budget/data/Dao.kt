package com.allmighty.budget.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert suspend fun insert(u: User): Long
    @Query("SELECT * FROM User WHERE username=:username LIMIT 1")
    suspend fun byUsername(username: String): User?
    @Query("SELECT * FROM User WHERE id=:id")
    suspend fun byId(id: Long): User?
    @Update suspend fun update(u: User)
}

@Dao
interface CategoryDao {
    @Insert suspend fun insert(c: Category): Long
    @Query("SELECT * FROM Category WHERE userId=:uid ORDER BY name")
    fun list(uid: Long): Flow<List<Category>>
    @Query("SELECT * FROM Category WHERE userId=:uid")
    suspend fun listOnce(uid: Long): List<Category>
}

@Dao
interface TxDao {
    @Insert suspend fun insert(t: Transaction): Long
    @Query("SELECT * FROM `Transaction` WHERE userId=:uid ORDER BY date DESC, id DESC")
    fun list(uid: Long): Flow<List<Transaction>>
    @Query("SELECT * FROM `Transaction` WHERE userId=:uid")
    suspend fun listOnce(uid: Long): List<Transaction>
}

@Dao
interface GoalDao {
    @Insert suspend fun insert(g: Goal): Long
    @Update suspend fun update(g: Goal)
    @Query("SELECT * FROM Goal WHERE userId=:uid ORDER BY id DESC")
    fun list(uid: Long): Flow<List<Goal>>
}