package gg.essential.universal

//#if MC<=11202
import org.lwjgl.input.Mouse
//#else
//$$ import gg.essential.universal.mixin.MouseAccessor
//$$ import org.lwjgl.glfw.GLFW
//#endif

import kotlin.math.max

object UMouse {

    //#if MC>=11502
    //$$ var prevScroll: Double = 0.0
    //#endif

    object Buttons {
        fun isButtonDown(button: Int): Boolean {
            //#if MC>=11502
            //$$ return GLFW.glfwGetMouseButton(UMinecraft.getMinecraft().getMainWindow().getHandle(), button) == GLFW.GLFW_PRESS
            //#else
            return Mouse.isButtonDown(button)
            //#endif
        }

        fun getButtonState(button: Int): Int {
            //#if MC>=11502
            //$$ return GLFW.glfwGetMouseButton(UMinecraft.getMinecraft().getMainWindow().getHandle(), button)
            //#else
            return if(Mouse.isButtonDown(button)) 1 else 0
            //#endif
        }

        fun isLeftClicked(): Boolean {
            return isButtonDown(0)
        }

        fun isRightClicked(): Boolean {
            return isButtonDown(1)
        }
    }
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

        /** Return the change in the mouse's X position since the last time this method was called. */
        @JvmStatic
        val deltaX: Double
            get() {
                //#if MC>=11502
                    //#if FORGE==1
                    //$$ return UMinecraft.getMinecraft().mouseHelper.getXVelocity()
                    //#else
                    //$$ return (UMinecraft.getMinecraft().mouse as MouseAccessor).getCursorDeltaX()
                    //#endif
                //#else
                return Mouse.getDX().toDouble()
                //#endif
            }

        /** Return the change in the mouse's Y position since the last time this method was called. */
        @JvmStatic
        val deltaY: Double
            get() {
                //#if MC>=11502
                    //#if FORGE==1
                    //$$ return UMinecraft.getMinecraft().mouseHelper.getXVelocity()
                    //#else
                    //$$ return (UMinecraft.getMinecraft().mouse as MouseAccessor).getCursorDeltaY()
                    //#endif
                //#else
                return UResolution.windowHeight - Mouse.getDY().toDouble() - 1
                //#endif
            }

        val wheelDelta: Double
            get() {
                //#if MC>=11502
                //$$ val scrollDelta = (UMinecraft.getMinecraft().mouseHelper as MouseAccessor).eventDeltaWheel
                //$$ val amount = scrollDelta - prevScroll
                //$$ prevScroll = scrollDelta
                //$$ return amount
                //#else
                return Mouse.getDWheel().toDouble()
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

        /** Return the change in the mouse's X position since the last time this method was called. */
        @JvmStatic
        val deltaX: Double
            get() = Raw.deltaX * UResolution.scaledWidth / max(1, UResolution.windowWidth)

        /** Return the change in the mouse's Y position since the last time this method was called. */
        @JvmStatic
        val deltaY: Double
            get() = Raw.deltaY * UResolution.scaledHeight / max(1, UResolution.windowHeight)
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
