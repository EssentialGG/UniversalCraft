package club.sk1er.mods.core.universal

import kotlin.math.sqrt

object UniversalMathHelper {
    @JvmStatic
    fun clampFloat(target: Float, min: Float, max: Float) = target.coerceIn(min, max)

    @JvmStatic
    fun sqrtDouble(value: Double) = sqrt(value).toFloat()

    @JvmStatic
    fun wrapAngleTo180(value: Double) = (value % 360.0).let {
        when {
            it >= 180.0 -> it - 360.0
            it <= -180.0 -> it + 360.0
            else -> it
        }
    }

    @JvmStatic
    fun clampInt(num: Int, min: Int, max: Int) = num.coerceIn(min, max)
}
