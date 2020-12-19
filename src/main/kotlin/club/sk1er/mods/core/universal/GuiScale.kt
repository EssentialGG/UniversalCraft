package club.sk1er.mods.core.universal

enum class GuiScale {
    Auto,
    Small,
    Medium,
    Large,
    VeryLarge;

    companion object {
        @JvmStatic
        fun fromNumber(number: Int) = values()[number]
    }
}
