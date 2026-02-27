package id.bytezilla.habittracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bytezilla.habittracker.domain.model.Habit
import id.bytezilla.habittracker.domain.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HabitViewModel(private val repository: HabitRepository = HabitRepository()) : ViewModel() {

    val habits: StateFlow<List<Habit>> = repository.habits
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayDate: String
        get() {
            val now = Clock.System.now()
            val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
            return "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')}"
        }

    val dailyProgress: StateFlow<Float> = habits.map { list ->
        if (list.isEmpty()) 0f
        else {
            val completedCount = list.count { it.completedDates.contains(todayDate) }
            completedCount.toFloat() / list.size
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    fun toggleHabit(habitId: String) {
        repository.toggleHabit(habitId, todayDate)
    }

    fun addHabit(name: String, icon: String, color: Int) {
        val newHabit = Habit(
            id = Clock.System.now().toEpochMilliseconds().toString(),
            name = name,
            icon = icon,
            color = color,
            createdAt = Clock.System.now().toEpochMilliseconds()
        )
        repository.addHabit(newHabit)
    }
}
