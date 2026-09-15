package com.example.myapplication.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.domain.model.GoalType
import com.example.myapplication.ui.components.MetricCard

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val currentState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Sport Gym",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Hola, ${currentState.userName}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Meta: ${goalLabel(currentState.goal)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    title = "Calorías",
                    value = "${currentState.consumedCalories}/${currentState.targetCalories} kcal",
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Estado",
                    value = if (currentState.alert) "Alerta" else "OK",
                    modifier = Modifier.weight(1f),
                    accent = if (currentState.alert) Color(0xFFE53935) else Color(0xFF2E7D32)
                )
            }
        }

        item {
            Text(
                text = "Macros diarios",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    title = "Proteína",
                    value = "${currentState.protein}g",
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Carbos",
                    value = "${currentState.carbs}g",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            MetricCard(
                title = "Grasas",
                value = "${currentState.fat}g",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            if (currentState.alert) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                ) {
                    Text(
                        text = "Atención: se detectó una desviación superior al ±10% respecto al plan asignado.",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFFB71C1C)
                    )
                }
            }
        }
    }
}

private fun goalLabel(goalType: GoalType): String = when (goalType) {
    GoalType.LOSE_WEIGHT -> "Bajar de peso"
    GoalType.GAIN_MUSCLE -> "Ganar masa muscular"
    GoalType.MAINTAIN -> "Mantenerse"
}
