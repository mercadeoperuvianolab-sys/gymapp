package com.example.myapplication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diet_plans")
data class DietPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val goal: String,
    val caloriesTarget: Int,
    val proteinTarget: Int,
    val carbTarget: Int,
    val fatTarget: Int,
    val updatedAtMillis: Long
)
