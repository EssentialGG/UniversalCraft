package club.sk1er.mods.core.universal

enum class GuiScale(val number: Int) {
    Auto(0),
    Small(1),
    Medium(2),
    Large(3);

    companion object {
        @JvmStatic
        fun fromNumber(number: Int) = values().first { it.number == number }
    }
}
