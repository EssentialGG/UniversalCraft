package gg.essential.universal

import kotlin.math.min

enum class GuiScale {
    Auto,
    Small,
    Medium,
    Large,
    VeryLarge;

    companion object {
        private val guiScaleOverride = System.getProperty("essential.guiScaleOverride","-1").toInt()

        @JvmStatic
        fun fromNumber(number: Int): GuiScale = values()[number]

        @JvmOverloads
        @JvmStatic
        fun scaleForScreenSize(step: Int = 650): GuiScale {
            if(guiScaleOverride != -1) return fromNumber(guiScaleOverride.coerceIn(0, 4))

            val width = UResolution.viewportWidth
            val height = UResolution.viewportHeight
            return fromNumber(min((width / step).coerceIn(1, 4), (height / (step / 16 * 9)).coerceIn(1, 4)))
        }
    }
}
