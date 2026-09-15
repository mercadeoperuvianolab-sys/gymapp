package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.dao.DietPlanDao
import com.example.myapplication.data.local.dao.MealDao
import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.local.entities.DietPlanEntity
import com.example.myapplication.data.local.entities.MealEntity
import com.example.myapplication.data.local.entities.UserEntity

@Database(
    entities = [UserEntity::class, DietPlanEntity::class, MealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SportGymDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun dietPlanDao(): DietPlanDao
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: SportGymDatabase? = null

        fun getDatabase(context: Context): SportGymDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SportGymDatabase::class.java,
                    "sport_gym.db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
