package id.bytezilla.habittracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform