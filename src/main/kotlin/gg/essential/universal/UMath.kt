package gg.essential.universal

import kotlin.math.sqrt

object UMath {
    @JvmStatic
    fun clampFloat(target: Float, min: Float, max: Float): Float = target.coerceIn(min, max)

    @JvmStatic
    fun sqrtDouble(value: Double): Float = sqrt(value).toFloat()

    @JvmStatic
    fun wrapAngleTo180(value: Double): Double = (value % 360.0).let {
        when {
            it >= 180.0 -> it - 360.0
            it <= -180.0 -> it + 360.0
            else -> it
        }
    }

    @JvmStatic
    fun clampInt(num: Int, min: Int, max: Int): Int = num.coerceIn(min, max)
}
