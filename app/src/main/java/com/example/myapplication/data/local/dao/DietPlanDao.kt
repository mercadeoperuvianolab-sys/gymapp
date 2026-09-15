package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.local.entities.DietPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DietPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDietPlan(plan: DietPlanEntity): Long

    @Query("SELECT * FROM diet_plans WHERE userId = :userId ORDER BY updatedAtMillis DESC LIMIT 1")
    suspend fun getLatestPlanForUser(userId: Long): DietPlanEntity?

    @Query("SELECT * FROM diet_plans WHERE userId = :userId ORDER BY updatedAtMillis DESC")
    fun observePlansByUser(userId: Long): Flow<List<DietPlanEntity>>
}
