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
    //$$ const val KEY_ESCAPE = GLFW.GLFW_KEY_ESCAPE
    //$$ const val KEY_LMETA = GLFW.GLFW_KEY_LEFT_SUPER // TODO: Correct?
    //$$ const val KEY_RMETA = GLFW.GLFW_KEY_RIGHT_SUPER // TODO: Correct?
    //$$ const val KEY_LCONTROL = GLFW.GLFW_KEY_LEFT_CONTROL
    //$$ const val KEY_RCONTROL = GLFW.GLFW_KEY_RIGHT_CONTROL
    //$$ const val KEY_LSHIFT = GLFW.GLFW_KEY_LEFT_SHIFT
    //$$ const val KEY_RSHIFT = GLFW.GLFW_KEY_RIGHT_SHIFT
    //$$ const val KEY_LMENU = GLFW.GLFW_KEY_LEFT_ALT
    //$$ const val KEY_RMENU = GLFW.GLFW_KEY_RIGHT_ALT
    //$$ const val KEY_MINUS = GLFW.GLFW_KEY_MINUS
    //$$ const val KEY_EQUALS = GLFW.GLFW_KEY_EQUAL
    //$$ const val KEY_BACKSPACE = GLFW.GLFW_KEY_BACKSPACE
    //$$ const val KEY_ENTER = GLFW.GLFW_KEY_ENTER
    //$$ const val KEY_TAB = GLFW.GLFW_KEY_TAB
    //$$ const val KEY_LBRACKET = GLFW.GLFW_KEY_LEFT_BRACKET
    //$$ const val KEY_RBRACKET = GLFW.GLFW_KEY_RIGHT_BRACKET
    //$$ const val KEY_SEMICOLON = GLFW.GLFW_KEY_SEMICOLON
    //$$ const val KEY_APOSTROPHE = GLFW.GLFW_KEY_APOSTROPHE
    //$$ const val KEY_GRAVE = GLFW.GLFW_KEY_GRAVE_ACCENT
    //$$ const val KEY_BACKSLASH = GLFW.GLFW_KEY_BACKSLASH
    //$$ const val KEY_COMMA = GLFW.GLFW_KEY_COMMA
    //$$ const val KEY_PERIOD = GLFW.GLFW_KEY_PERIOD
    //$$ const val KEY_SLASH = GLFW.GLFW_KEY_SLASH
    //$$ const val KEY_MULTIPLY = GLFW.GLFW_KEY_KP_MULTIPLY
    //$$ const val KEY_SPACE = GLFW.GLFW_KEY_SPACE
    //$$ const val KEY_CAPITAL = GLFW.GLFW_KEY_CAPS_LOCK
    //$$ const val KEY_LEFT = GLFW.GLFW_KEY_LEFT
    //$$ const val KEY_UP = GLFW.GLFW_KEY_UP
    //$$ const val KEY_RIGHT = GLFW.GLFW_KEY_RIGHT
    //$$ const val KEY_DOWN = GLFW.GLFW_KEY_DOWN
    //$$ const val KEY_NUMLOCK = GLFW.GLFW_KEY_NUM_LOCK
    //$$ const val KEY_SCROLL = GLFW.GLFW_KEY_SCROLL_LOCK
    //$$ const val KEY_SUBTRACT = GLFW.GLFW_KEY_KP_SUBTRACT
    //$$ const val KEY_ADD = GLFW.GLFW_KEY_KP_ADD
    //$$ const val KEY_DIVIDE = GLFW.GLFW_KEY_KP_DIVIDE
    //$$ const val KEY_DECIMAL = GLFW.GLFW_KEY_KP_DECIMAL
    //$$ const val KEY_NUMPAD0 = GLFW.GLFW_KEY_KP_0
    //$$ const val KEY_NUMPAD1 = GLFW.GLFW_KEY_KP_1
    //$$ const val KEY_NUMPAD2 = GLFW.GLFW_KEY_KP_2
    //$$ const val KEY_NUMPAD3 = GLFW.GLFW_KEY_KP_3
    //$$ const val KEY_NUMPAD4 = GLFW.GLFW_KEY_KP_4
    //$$ const val KEY_NUMPAD5 = GLFW.GLFW_KEY_KP_5
    //$$ const val KEY_NUMPAD6 = GLFW.GLFW_KEY_KP_6
    //$$ const val KEY_NUMPAD7 = GLFW.GLFW_KEY_KP_7
    //$$ const val KEY_NUMPAD8 = GLFW.GLFW_KEY_KP_8
    //$$ const val KEY_NUMPAD9 = GLFW.GLFW_KEY_KP_9
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
    //$$ const val KEY_0 = GLFW.GLFW_KEY_0
    //$$ const val KEY_1 = GLFW.GLFW_KEY_1
    //$$ const val KEY_2 = GLFW.GLFW_KEY_2
    //$$ const val KEY_3 = GLFW.GLFW_KEY_3
    //$$ const val KEY_4 = GLFW.GLFW_KEY_4
    //$$ const val KEY_5 = GLFW.GLFW_KEY_5
    //$$ const val KEY_6 = GLFW.GLFW_KEY_6
    //$$ const val KEY_7 = GLFW.GLFW_KEY_7
    //$$ const val KEY_8 = GLFW.GLFW_KEY_8
    //$$ const val KEY_9 = GLFW.GLFW_KEY_9
    //$$ const val KEY_F1 = GLFW.GLFW_KEY_F1
    //$$ const val KEY_F2 = GLFW.GLFW_KEY_F2
    //$$ const val KEY_F3 = GLFW.GLFW_KEY_F3
    //$$ const val KEY_F4 = GLFW.GLFW_KEY_F4
    //$$ const val KEY_F5 = GLFW.GLFW_KEY_F5
    //$$ const val KEY_F6 = GLFW.GLFW_KEY_F6
    //$$ const val KEY_F7 = GLFW.GLFW_KEY_F7
    //$$ const val KEY_F8 = GLFW.GLFW_KEY_F8
    //$$ const val KEY_F9 = GLFW.GLFW_KEY_F9
    //$$ const val KEY_F10 = GLFW.GLFW_KEY_F10
    //$$ const val KEY_F11 = GLFW.GLFW_KEY_F11
    //$$ const val KEY_F12 = GLFW.GLFW_KEY_F12
    //$$ const val KEY_F13 = GLFW.GLFW_KEY_F13
    //$$ const val KEY_F14 = GLFW.GLFW_KEY_F14
    //$$ const val KEY_F15 = GLFW.GLFW_KEY_F15
    //$$ const val KEY_F16 = GLFW.GLFW_KEY_F16
    //$$ const val KEY_F17 = GLFW.GLFW_KEY_F17
    //$$ const val KEY_F18 = GLFW.GLFW_KEY_F18
    //$$ const val KEY_F19 = GLFW.GLFW_KEY_F19
    //#else
    const val KEY_ESCAPE = Keyboard.KEY_ESCAPE
    const val KEY_LMETA = Keyboard.KEY_LMETA
    const val KEY_RMETA = Keyboard.KEY_RMETA
    const val KEY_LCONTROL = Keyboard.KEY_LCONTROL
    const val KEY_RCONTROL = Keyboard.KEY_RCONTROL
    const val KEY_LSHIFT = Keyboard.KEY_LSHIFT
    const val KEY_RSHIFT = Keyboard.KEY_RSHIFT
    const val KEY_LMENU = Keyboard.KEY_LMENU
    const val KEY_RMENU = Keyboard.KEY_RMENU
    const val KEY_MINUS = Keyboard.KEY_MINUS
    const val KEY_EQUALS = Keyboard.KEY_EQUALS
    const val KEY_BACKSPACE = Keyboard.KEY_BACK
    const val KEY_ENTER = Keyboard.KEY_RETURN
    const val KEY_TAB = Keyboard.KEY_TAB
    const val KEY_LBRACKET = Keyboard.KEY_LBRACKET
    const val KEY_RBRACKET = Keyboard.KEY_RBRACKET
    const val KEY_SEMICOLON = Keyboard.KEY_SEMICOLON
    const val KEY_APOSTROPHE = Keyboard.KEY_APOSTROPHE
    const val KEY_GRAVE = Keyboard.KEY_GRAVE
    const val KEY_BACKSLASH = Keyboard.KEY_BACKSLASH
    const val KEY_COMMA = Keyboard.KEY_COMMA
    const val KEY_PERIOD = Keyboard.KEY_PERIOD
    const val KEY_SLASH = Keyboard.KEY_SLASH
    const val KEY_MULTIPLY = Keyboard.KEY_MULTIPLY
    const val KEY_SPACE = Keyboard.KEY_SPACE
    const val KEY_CAPITAL = Keyboard.KEY_CAPITAL
    const val KEY_LEFT = Keyboard.KEY_LEFT
    const val KEY_UP = Keyboard.KEY_UP
    const val KEY_RIGHT = Keyboard.KEY_RIGHT
    const val KEY_DOWN = Keyboard.KEY_DOWN
    const val KEY_NUMLOCK = Keyboard.KEY_NUMLOCK
    const val KEY_SCROLL = Keyboard.KEY_SCROLL
    const val KEY_SUBTRACT = Keyboard.KEY_SUBTRACT
    const val KEY_ADD = Keyboard.KEY_ADD
    const val KEY_DIVIDE = Keyboard.KEY_DIVIDE
    const val KEY_DECIMAL = Keyboard.KEY_DECIMAL
    const val KEY_NUMPAD0 = Keyboard.KEY_NUMPAD0
    const val KEY_NUMPAD1 = Keyboard.KEY_NUMPAD1
    const val KEY_NUMPAD2 = Keyboard.KEY_NUMPAD2
    const val KEY_NUMPAD3 = Keyboard.KEY_NUMPAD3
    const val KEY_NUMPAD4 = Keyboard.KEY_NUMPAD4
    const val KEY_NUMPAD5 = Keyboard.KEY_NUMPAD5
    const val KEY_NUMPAD6 = Keyboard.KEY_NUMPAD6
    const val KEY_NUMPAD7 = Keyboard.KEY_NUMPAD7
    const val KEY_NUMPAD8 = Keyboard.KEY_NUMPAD8
    const val KEY_NUMPAD9 = Keyboard.KEY_NUMPAD9
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
    const val KEY_0 = Keyboard.KEY_0
    const val KEY_1 = Keyboard.KEY_1
    const val KEY_2 = Keyboard.KEY_2
    const val KEY_3 = Keyboard.KEY_3
    const val KEY_4 = Keyboard.KEY_4
    const val KEY_5 = Keyboard.KEY_5
    const val KEY_6 = Keyboard.KEY_6
    const val KEY_7 = Keyboard.KEY_7
    const val KEY_8 = Keyboard.KEY_8
    const val KEY_9 = Keyboard.KEY_9
    const val KEY_F1 = Keyboard.KEY_F1
    const val KEY_F2 = Keyboard.KEY_F2
    const val KEY_F3 = Keyboard.KEY_F3
    const val KEY_F4 = Keyboard.KEY_F4
    const val KEY_F5 = Keyboard.KEY_F5
    const val KEY_F6 = Keyboard.KEY_F6
    const val KEY_F7 = Keyboard.KEY_F7
    const val KEY_F8 = Keyboard.KEY_F8
    const val KEY_F9 = Keyboard.KEY_F9
    const val KEY_F10 = Keyboard.KEY_F10
    const val KEY_F11 = Keyboard.KEY_F11
    const val KEY_F12 = Keyboard.KEY_F12
    const val KEY_F13 = Keyboard.KEY_F13
    const val KEY_F14 = Keyboard.KEY_F14
    const val KEY_F15 = Keyboard.KEY_F15
    const val KEY_F16 = Keyboard.KEY_F16
    const val KEY_F17 = Keyboard.KEY_F17
    const val KEY_F18 = Keyboard.KEY_F18
    const val KEY_F19 = Keyboard.KEY_F19
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

    @JvmStatic
    fun getKeyName(keyCode: Int): String? {
        //#if FABRIC
        //$$ return InputUtil.fromKeyCode(keyCode, -1).translationKey
        //#elseif MC>=11502
        //$$ return GLFW.glfwGetKeyName(keyCode, -1)
        //#else
        return Keyboard.getKeyName(keyCode)
        //#endif
    }

    data class Modifiers(val isCtrl: Boolean, val isShift: Boolean, val isAlt: Boolean)
}
