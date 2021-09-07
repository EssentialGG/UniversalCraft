package gg.essential.universal

//#if MC<=11202
import org.lwjgl.input.Mouse
//#endif

import kotlin.math.max

object UMouse {
    @JvmStatic
    fun getTrueX(): Double {
        //#if MC>=11502
        //$$ return UMinecraft.getMinecraft().mouseHelper.mouseX
        //#else
        return Mouse.getX().toDouble()
        //#endif
    }

    @JvmStatic
    fun getScaledX(): Double {
        return getTrueX() * UResolution.scaledWidth / max(1, UResolution.windowWidth)
    }

    @JvmStatic
    fun getTrueY(): Double {
        //#if MC>=11502
        //$$ return UMinecraft.getMinecraft().mouseHelper.mouseY
        //#else
        return Mouse.getY().toDouble()
        //#endif
    }

    @JvmStatic
    fun getScaledY(): Double {
        return getTrueY() * UResolution.scaledHeight / max(1, UResolution.windowHeight)
    }
}
