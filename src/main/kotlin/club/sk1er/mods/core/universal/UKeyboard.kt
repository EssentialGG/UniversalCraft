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

object UKeyboard {
    //#if MC>=11502
    //$$ const val KEY_LMETA = GLFW.GLFW_KEY_LEFT_SUPER // TODO: Correct?
    //$$ const val KEY_RMETA = GLFW.GLFW_KEY_RIGHT_SUPER // TODO: Correct?
    //$$ const val KEY_LCONTROL = GLFW.GLFW_KEY_LEFT_CONTROL
    //$$ const val KEY_RCONTROL = GLFW.GLFW_KEY_RIGHT_CONTROL
    //$$ const val KEY_LSHIFT = GLFW.GLFW_KEY_LEFT_SHIFT
    //$$ const val KEY_RSHIFT = GLFW.GLFW_KEY_RIGHT_SHIFT
    //$$ const val KEY_LMENU = GLFW.GLFW_KEY_LEFT_ALT
    //$$ const val KEY_RMENU = GLFW.GLFW_KEY_RIGHT_ALT
    //$$ const val KEY_A = GLFW.GLFW_KEY_A
    //$$ const val KEY_B = GLFW.GLFW_KEY_B
    //$$ const val KEY_C = GLFW.GLFW_KEY_C
    //$$ const val KEY_D = GLFW.GLFW_KEY_D
    //$$ const val KEY_E = GLFW.GLFW_KEY_E
    //$$ const val KEY_F = GLFW.GLFW_KEY_F
    //$$ const val KEY_G = GLFW.GLFW_KEY_G
    //$$ const val KEY_H = GLFW.GLFW_KEY_H
    //$$ const val KEY_I = GLFW.GLFW_KEY_I
    //$$ const val KEY_J = GLFW.GLFW_KEY_J
    //$$ const val KEY_K = GLFW.GLFW_KEY_K
    //$$ const val KEY_L = GLFW.GLFW_KEY_L
    //$$ const val KEY_M = GLFW.GLFW_KEY_M
    //$$ const val KEY_N = GLFW.GLFW_KEY_N
    //$$ const val KEY_O = GLFW.GLFW_KEY_O
    //$$ const val KEY_P = GLFW.GLFW_KEY_P
    //$$ const val KEY_Q = GLFW.GLFW_KEY_Q
    //$$ const val KEY_R = GLFW.GLFW_KEY_R
    //$$ const val KEY_S = GLFW.GLFW_KEY_S
    //$$ const val KEY_T = GLFW.GLFW_KEY_T
    //$$ const val KEY_U = GLFW.GLFW_KEY_U
    //$$ const val KEY_V = GLFW.GLFW_KEY_V
    //$$ const val KEY_W = GLFW.GLFW_KEY_W
    //$$ const val KEY_X = GLFW.GLFW_KEY_X
    //$$ const val KEY_Y = GLFW.GLFW_KEY_Y
    //$$ const val KEY_Z = GLFW.GLFW_KEY_Z
    //#else
    const val KEY_LMETA = Keyboard.KEY_LMETA
    const val KEY_RMETA = Keyboard.KEY_RMETA
    const val KEY_LCONTROL = Keyboard.KEY_LCONTROL
    const val KEY_RCONTROL = Keyboard.KEY_RCONTROL
    const val KEY_LSHIFT = Keyboard.KEY_LSHIFT
    const val KEY_RSHIFT = Keyboard.KEY_RSHIFT
    const val KEY_LMENU = Keyboard.KEY_LMENU
    const val KEY_RMENU = Keyboard.KEY_RMENU
    const val KEY_A = Keyboard.KEY_A
    const val KEY_B = Keyboard.KEY_B
    const val KEY_C = Keyboard.KEY_C
    const val KEY_D = Keyboard.KEY_D
    const val KEY_E = Keyboard.KEY_E
    const val KEY_F = Keyboard.KEY_F
    const val KEY_G = Keyboard.KEY_G
    const val KEY_H = Keyboard.KEY_H
    const val KEY_I = Keyboard.KEY_I
    const val KEY_J = Keyboard.KEY_J
    const val KEY_K = Keyboard.KEY_K
    const val KEY_L = Keyboard.KEY_L
    const val KEY_M = Keyboard.KEY_M
    const val KEY_N = Keyboard.KEY_N
    const val KEY_O = Keyboard.KEY_O
    const val KEY_P = Keyboard.KEY_P
    const val KEY_Q = Keyboard.KEY_Q
    const val KEY_R = Keyboard.KEY_R
    const val KEY_S = Keyboard.KEY_S
    const val KEY_T = Keyboard.KEY_T
    const val KEY_U = Keyboard.KEY_U
    const val KEY_V = Keyboard.KEY_V
    const val KEY_W = Keyboard.KEY_W
    const val KEY_X = Keyboard.KEY_X
    const val KEY_Y = Keyboard.KEY_Y
    const val KEY_Z = Keyboard.KEY_Z
    //#endif

    @JvmStatic
    fun allowRepeatEvents(enabled: Boolean) {
        //#if FABRIC
        //$$ UMinecraft.getMinecraft().keyboard.setRepeatEvents(enabled)
        //#elseif MC>=11502
        //$$ UMinecraft.getMinecraft().keyboardListener.enableRepeatEvents(enabled)
        //#else
        Keyboard.enableRepeatEvents(enabled)
        //#endif
    }

    @JvmStatic
    fun isCtrlKeyDown() = if (UMinecraft.isRunningOnMac) {
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
        //$$ return InputUtil.isKeyPressed(UMinecraft.getMinecraft().window.handle, key)
        //#elseif MC>=11502
        //$$ return InputMappings.isKeyDown(UMinecraft.getMinecraft().mainWindow.handle, key)
        //#else
        return Keyboard.isKeyDown(key)
        //#endif
    }

    data class Modifiers(val isCtrl: Boolean, val isShift: Boolean, val isAlt: Boolean)
}
