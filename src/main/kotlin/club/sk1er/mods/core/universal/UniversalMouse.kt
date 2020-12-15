package club.sk1er.mods.core.universal

//#if MC<=11202
import org.lwjgl.input.Mouse
//#endif

import kotlin.math.max

object UniversalMouse {
    @JvmStatic
    fun getTrueX(): Double {
        //#if FABRIC
        //$$ return UniversalMinecraft.getMinecraft().mouse.x
        //#elseif MC>=11502
        //$$ return UniversalMinecraft.getMinecraft().mouseHelper.mouseX
        //#else
        return Mouse.getX().toDouble()
        //#endif
    }

    @JvmStatic
    fun getScaledX(): Double {
        return getTrueX() * UniversalResolutionUtil.scaledWidth / max(1, UniversalResolutionUtil.windowWidth)
    }

    @JvmStatic
    fun getTrueY(): Double {
        //#if FABRIC
        //$$ return UniversalMinecraft.getMinecraft().mouse.y
        //#elseif MC>=11502
        //$$ return UniversalMinecraft.getMinecraft().mouseHelper.mouseY
        //#else
        return Mouse.getY().toDouble()
        //#endif
    }

    @JvmStatic
    fun getScaledY(): Double {
        return getTrueY() * UniversalResolutionUtil.scaledHeight / max(1, UniversalResolutionUtil.windowHeight)
    }
}
