package com.example.myapplication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val goal: String,
    val caloriesTarget: Int,
    val proteinTarget: Int,
    val carbTarget: Int,
    val fatTarget: Int
)
