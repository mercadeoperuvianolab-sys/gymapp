package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.local.entities.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity): Long

    @Query("SELECT * FROM meals WHERE userId = :userId ORDER BY consumedAtEpochMillis DESC")
    fun observeMeals(userId: Long): Flow<List<MealEntity>>

    @Query(
        "SELECT * FROM meals WHERE userId = :userId AND consumedAtEpochMillis >= :startMillis AND consumedAtEpochMillis < :endMillis ORDER BY consumedAtEpochMillis DESC"
    )
    fun observeMealsByDay(
        userId: Long,
        startMillis: Long,
        endMillis: Long
    ): Flow<List<MealEntity>>
}
