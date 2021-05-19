package gg.essential.universal

enum class GuiScale {
    Auto,
    Small,
    Medium,
    Large,
    VeryLarge;

    companion object {
        @JvmStatic
        fun fromNumber(number: Int): GuiScale = values()[number]
    }
}
