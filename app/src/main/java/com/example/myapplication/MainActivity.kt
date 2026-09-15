package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.local.SportGymDatabase
import com.example.myapplication.data.local.entities.DietPlanEntity
import com.example.myapplication.data.local.entities.UserEntity
import com.example.myapplication.data.repository.SportGymRepositoryImpl
import com.example.myapplication.domain.model.GoalType
import com.example.myapplication.ui.screens.dashboard.DashboardScreen
import com.example.myapplication.ui.screens.dashboard.DashboardViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = SportGymDatabase.getDatabase(applicationContext)
        val repository = SportGymRepositoryImpl(database)
        val viewModel = DashboardViewModel(repository)

        lifecycleScope.launch {
            val existingUser = database.userDao().getCurrentUser()
            if (existingUser == null) {
                val userId = database.userDao().upsertUser(
                    UserEntity(
                        id = 1,
                        name = "Ana García",
                        goal = GoalType.LOSE_WEIGHT.name,
                        caloriesTarget = 2200,
                        proteinTarget = 160,
                        carbTarget = 230,
                        fatTarget = 70
                    )
                )
                database.dietPlanDao().upsertDietPlan(
                    DietPlanEntity(
                        userId = userId,
                        goal = GoalType.LOSE_WEIGHT.name,
                        caloriesTarget = 2200,
                        proteinTarget = 160,
                        carbTarget = 230,
                        fatTarget = 70,
                        updatedAtMillis = System.currentTimeMillis()
                    )
                )
            }
        }

        setContent {
            MyApplicationTheme {
                DashboardScreen(viewModel = viewModel)
            }
        }
    }
}