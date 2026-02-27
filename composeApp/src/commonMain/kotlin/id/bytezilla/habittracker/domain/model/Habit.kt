package id.bytezilla.habittracker.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

data class Habit(
    val id: String,
    val name: String,
    val icon: String,
    val color: Int,
    val completedDates: Set<String> = emptySet(), // Format: YYYY-MM-DD
    val createdAt: Long = 0L
)

fun Habit.isCompletedToday(today: String): Boolean {
    return completedDates.contains(today)
}
