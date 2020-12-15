package club.sk1er.mods.core.universal

//#if FABRIC
//$$ import net.minecraft.client.util.InputUtil
//$$ import org.lwjgl.glfw.GLFW
//#else
//$$ import net.minecraft.client.Minecraft
//#if MC>=11502
//$$ import org.lwjgl.glfw.GLFW
//$$ import net.minecraft.client.util.InputMappings
//#else
import org.lwjgl.input.Keyboard
//#endif
//#endif

object UniversalKeyboard {
    //#if MC>=11502
    //$$ const val KEY_LMETA: Int = GLFW.GLFW_KEY_LEFT_SUPER // TODO: Correct?
    //$$ const val KEY_RMETA: Int = GLFW.GLFW_KEY_RIGHT_SUPER // TODO: Correct?
    //$$ const val KEY_LCONTROL: Int = GLFW.GLFW_KEY_LEFT_CONTROL
    //$$ const val KEY_RCONTROL: Int = GLFW.GLFW_KEY_RIGHT_CONTROL
    //$$ const val KEY_LSHIFT: Int = GLFW.GLFW_KEY_LEFT_SHIFT
    //$$ const val KEY_RSHIFT: Int = GLFW.GLFW_KEY_RIGHT_SHIFT
    //$$ const val KEY_LMENU: Int = GLFW.GLFW_KEY_LEFT_ALT
    //$$ const val KEY_RMENU: Int = GLFW.GLFW_KEY_RIGHT_ALT
    //$$ const val KEY_A: Int = GLFW.GLFW_KEY_A
    //$$ const val KEY_B: Int = GLFW.GLFW_KEY_B
    //$$ const val KEY_C: Int = GLFW.GLFW_KEY_C
    //$$ const val KEY_D: Int = GLFW.GLFW_KEY_D
    //$$ const val KEY_E: Int = GLFW.GLFW_KEY_E
    //$$ const val KEY_F: Int = GLFW.GLFW_KEY_F
    //$$ const val KEY_G: Int = GLFW.GLFW_KEY_G
    //$$ const val KEY_H: Int = GLFW.GLFW_KEY_H
    //$$ const val KEY_I: Int = GLFW.GLFW_KEY_I
    //$$ const val KEY_J: Int = GLFW.GLFW_KEY_J
    //$$ const val KEY_K: Int = GLFW.GLFW_KEY_K
    //$$ const val KEY_L: Int = GLFW.GLFW_KEY_L
    //$$ const val KEY_M: Int = GLFW.GLFW_KEY_M
    //$$ const val KEY_N: Int = GLFW.GLFW_KEY_N
    //$$ const val KEY_O: Int = GLFW.GLFW_KEY_O
    //$$ const val KEY_P: Int = GLFW.GLFW_KEY_P
    //$$ const val KEY_Q: Int = GLFW.GLFW_KEY_Q
    //$$ const val KEY_R: Int = GLFW.GLFW_KEY_R
    //$$ const val KEY_S: Int = GLFW.GLFW_KEY_S
    //$$ const val KEY_T: Int = GLFW.GLFW_KEY_T
    //$$ const val KEY_U: Int = GLFW.GLFW_KEY_U
    //$$ const val KEY_V: Int = GLFW.GLFW_KEY_V
    //$$ const val KEY_W: Int = GLFW.GLFW_KEY_W
    //$$ const val KEY_X: Int = GLFW.GLFW_KEY_X
    //$$ const val KEY_Y: Int = GLFW.GLFW_KEY_Y
    //$$ const val KEY_Z: Int = GLFW.GLFW_KEY_Z
    //#else
    const val KEY_LMETA: Int = Keyboard.KEY_LMETA
    const val KEY_RMETA: Int = Keyboard.KEY_RMETA
    const val KEY_LCONTROL: Int = Keyboard.KEY_LCONTROL
    const val KEY_RCONTROL: Int = Keyboard.KEY_RCONTROL
    const val KEY_LSHIFT: Int = Keyboard.KEY_LSHIFT
    const val KEY_RSHIFT: Int = Keyboard.KEY_RSHIFT
    const val KEY_LMENU: Int = Keyboard.KEY_LMENU
    const val KEY_RMENU: Int = Keyboard.KEY_RMENU
    const val KEY_A: Int = Keyboard.KEY_A
    const val KEY_B: Int = Keyboard.KEY_B
    const val KEY_C: Int = Keyboard.KEY_C
    const val KEY_D: Int = Keyboard.KEY_D
    const val KEY_E: Int = Keyboard.KEY_E
    const val KEY_F: Int = Keyboard.KEY_F
    const val KEY_G: Int = Keyboard.KEY_G
    const val KEY_H: Int = Keyboard.KEY_H
    const val KEY_I: Int = Keyboard.KEY_I
    const val KEY_J: Int = Keyboard.KEY_J
    const val KEY_K: Int = Keyboard.KEY_K
    const val KEY_L: Int = Keyboard.KEY_L
    const val KEY_M: Int = Keyboard.KEY_M
    const val KEY_N: Int = Keyboard.KEY_N
    const val KEY_O: Int = Keyboard.KEY_O
    const val KEY_P: Int = Keyboard.KEY_P
    const val KEY_Q: Int = Keyboard.KEY_Q
    const val KEY_R: Int = Keyboard.KEY_R
    const val KEY_S: Int = Keyboard.KEY_S
    const val KEY_T: Int = Keyboard.KEY_T
    const val KEY_U: Int = Keyboard.KEY_U
    const val KEY_V: Int = Keyboard.KEY_V
    const val KEY_W: Int = Keyboard.KEY_W
    const val KEY_X: Int = Keyboard.KEY_X
    const val KEY_Y: Int = Keyboard.KEY_Y
    const val KEY_Z: Int = Keyboard.KEY_Z
    //#endif

    @JvmStatic
    fun allowRepeatEvents(enabled: Boolean) {
        //#if FABRIC
        //$$ UniversalMinecraft.getMinecraft().keyboard.setRepeatEvents(enabled)
        //#elseif MC>=11502
        //$$ UniversalMinecraft.getMinecraft().keyboardListener.enableRepeatEvents(enabled)
        //#else
        Keyboard.enableRepeatEvents(enabled)
        //#endif
    }

    @JvmStatic
    fun isCtrlKeyDown() = if (UniversalMinecraft.isRunningOnMac) {
        isKeyDown(KEY_LMENU) || isKeyDown(KEY_RMENU)
    } else isKeyDown(KEY_LCONTROL) || isKeyDown(KEY_RCONTROL)

    @JvmStatic
    fun isShiftKeyDown() = isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT)

    @JvmStatic
    fun isAltKeyDown() = isKeyDown(KEY_LMENU) || isKeyDown(KEY_RMENU)

    @JvmStatic
    fun getModifiers() = Modifiers(isCtrlKeyDown(), isShiftKeyDown(), isAltKeyDown())

    @JvmStatic
    fun isKeyComboCtrlA(key: Int) = key == KEY_A && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlC(key: Int) = key == KEY_C && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlV(key: Int) = key == KEY_V && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlX(key: Int) = key == KEY_X && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlY(key: Int) = key == KEY_Y && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlZ(key: Int) = key == KEY_Z && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlShiftZ(key: Int) = key == KEY_Z && isCtrlKeyDown() && isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyDown(key: Int): Boolean {
        //#if FABRIC
        //$$ return InputUtil.isKeyPressed(UniversalMinecraft.getMinecraft().window.handle, key)
        //#elseif MC>=11502
        //$$ return InputMappings.isKeyDown(UniversalMinecraft.getMinecraft().mainWindow.handle, key)
        //#else
        return Keyboard.isKeyDown(key)
        //#endif
    }

    data class Modifiers(val isCtrl: Boolean, val isShift: Boolean, val isAlt: Boolean)
}
