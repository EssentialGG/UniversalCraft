package gg.essential.universal

//#if MC<=11202
import org.lwjgl.input.Mouse
//#endif

import kotlin.math.max

object UMouse {
    object Raw {
        @JvmStatic
        val x: Double
            get() {
                //#if MC>=11502
                //$$ return UMinecraft.getMinecraft().mouseHelper.mouseX
                //#else
                return Mouse.getX().toDouble()
                //#endif
            }

        @JvmStatic
        val y: Double
            get() {
                //#if MC>=11400
                //$$ return UMinecraft.getMinecraft().mouseHelper.mouseY
                //#else
                return UResolution.windowHeight - Mouse.getY().toDouble() - 1
                //#endif
            }
    }

    object Scaled {
        @JvmStatic
        val x: Double
            get() = Raw.x * UResolution.scaledWidth / max(1, UResolution.windowWidth)

        @JvmStatic
        val y: Double
            get() = Raw.y * UResolution.scaledHeight / max(1, UResolution.windowHeight)
    }

    @JvmStatic
    @Deprecated("Orientation is different between Minecraft versions.", replaceWith = ReplaceWith("UMouse.Raw.x"))
    fun getTrueX(): Double {
        //#if MC>=11502
        //$$ return UMinecraft.getMinecraft().mouseHelper.mouseX
        //#else
        return Mouse.getX().toDouble()
        //#endif
    }

    @JvmStatic
    @Deprecated("Orientation is different between Minecraft versions.", replaceWith = ReplaceWith("UMouse.Scaled.x"))
    @Suppress("DEPRECATION")
    fun getScaledX(): Double {
        return getTrueX() * UResolution.scaledWidth / max(1, UResolution.windowWidth)
    }

    @JvmStatic
    @Deprecated("Orientation is different between Minecraft versions.", replaceWith = ReplaceWith("UMouse.Raw.y"))
    fun getTrueY(): Double {
        //#if MC>=11502
        //$$ return UMinecraft.getMinecraft().mouseHelper.mouseY
        //#else
        return Mouse.getY().toDouble()
        //#endif
    }

    @JvmStatic
    @Deprecated("Orientation is different between Minecraft versions.", replaceWith = ReplaceWith("UMouse.Scaled.y"))
    @Suppress("DEPRECATION")
    fun getScaledY(): Double {
        return getTrueY() * UResolution.scaledHeight / max(1, UResolution.windowHeight)
    }
}
