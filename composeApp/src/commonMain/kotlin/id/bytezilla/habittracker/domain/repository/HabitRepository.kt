package id.bytezilla.habittracker.domain.repository

import id.bytezilla.habittracker.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class HabitRepository {
    private val _habits = MutableStateFlow<List<Habit>>(
        listOf(
            Habit("1", "Drink Water", "🚰", 0xFF2196F3.toInt()),
            Habit("2", "Read Book", "📚", 0xFF4CAF50.toInt()),
            Habit("3", "Workout", "💪", 0xFFFF9800.toInt())
        )
    )
    val habits: Flow<List<Habit>> = _habits.asStateFlow()

    fun addHabit(habit: Habit) {
        _habits.value = _habits.value + habit
    }

    fun toggleHabit(habitId: String, date: String) {
        _habits.value = _habits.value.map { habit ->
            if (habit.id == habitId) {
                val newDates = if (habit.completedDates.contains(date)) {
                    habit.completedDates - date
                } else {
                    habit.completedDates + date
                }
                habit.copy(completedDates = newDates)
            } else {
                habit
            }
        }
    }

    fun deleteHabit(habitId: String) {
        _habits.value = _habits.value.filter { it.id != habitId }
    }
}
