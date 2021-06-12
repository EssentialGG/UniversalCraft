package gg.essential.universal

import kotlin.math.min

enum class GuiScale {
    Auto,
    Small,
    Medium,
    Large,
    VeryLarge;

    companion object {
        @JvmStatic
        fun fromNumber(number: Int): GuiScale = values()[number]

        @JvmStatic
        fun scaleForScreenSize(): GuiScale {
            val width = UResolution.windowWidth
            val height = UResolution.windowHeight
            val step = 700
            return fromNumber(min((width / step).coerceIn(1, 4), height / (step / 16 * 9).coerceIn(1, 4)))
        }
    }
}
