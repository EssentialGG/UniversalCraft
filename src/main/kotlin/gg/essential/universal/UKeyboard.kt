package gg.essential.universal

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
    //$$ const val KEY_NONE: Int = 0
    //$$ const val KEY_ESCAPE: Int = GLFW.GLFW_KEY_ESCAPE
    //$$ const val KEY_LMETA: Int = GLFW.GLFW_KEY_LEFT_SUPER // TODO: Correct?
    //$$ const val KEY_RMETA: Int = GLFW.GLFW_KEY_RIGHT_SUPER // TODO: Correct?
    //$$ const val KEY_LCONTROL: Int = GLFW.GLFW_KEY_LEFT_CONTROL
    //$$ const val KEY_RCONTROL: Int = GLFW.GLFW_KEY_RIGHT_CONTROL
    //$$ const val KEY_LSHIFT: Int = GLFW.GLFW_KEY_LEFT_SHIFT
    //$$ const val KEY_RSHIFT: Int = GLFW.GLFW_KEY_RIGHT_SHIFT
    //$$ const val KEY_LMENU: Int = GLFW.GLFW_KEY_LEFT_ALT
    //$$ const val KEY_RMENU: Int = GLFW.GLFW_KEY_RIGHT_ALT
    //$$ const val KEY_MINUS: Int = GLFW.GLFW_KEY_MINUS
    //$$ const val KEY_EQUALS: Int = GLFW.GLFW_KEY_EQUAL
    //$$ const val KEY_BACKSPACE: Int = GLFW.GLFW_KEY_BACKSPACE
    //$$ const val KEY_ENTER: Int = GLFW.GLFW_KEY_ENTER
    //$$ const val KEY_TAB: Int = GLFW.GLFW_KEY_TAB
    //$$ const val KEY_LBRACKET: Int = GLFW.GLFW_KEY_LEFT_BRACKET
    //$$ const val KEY_RBRACKET: Int = GLFW.GLFW_KEY_RIGHT_BRACKET
    //$$ const val KEY_SEMICOLON: Int = GLFW.GLFW_KEY_SEMICOLON
    //$$ const val KEY_APOSTROPHE: Int = GLFW.GLFW_KEY_APOSTROPHE
    //$$ const val KEY_GRAVE: Int = GLFW.GLFW_KEY_GRAVE_ACCENT
    //$$ const val KEY_BACKSLASH: Int = GLFW.GLFW_KEY_BACKSLASH
    //$$ const val KEY_COMMA: Int = GLFW.GLFW_KEY_COMMA
    //$$ const val KEY_PERIOD: Int = GLFW.GLFW_KEY_PERIOD
    //$$ const val KEY_SLASH: Int = GLFW.GLFW_KEY_SLASH
    //$$ const val KEY_MULTIPLY: Int = GLFW.GLFW_KEY_KP_MULTIPLY
    //$$ const val KEY_SPACE: Int = GLFW.GLFW_KEY_SPACE
    //$$ const val KEY_CAPITAL: Int = GLFW.GLFW_KEY_CAPS_LOCK
    //$$ const val KEY_LEFT: Int = GLFW.GLFW_KEY_LEFT
    //$$ const val KEY_UP: Int = GLFW.GLFW_KEY_UP
    //$$ const val KEY_RIGHT: Int = GLFW.GLFW_KEY_RIGHT
    //$$ const val KEY_DOWN: Int = GLFW.GLFW_KEY_DOWN
    //$$ const val KEY_NUMLOCK: Int = GLFW.GLFW_KEY_NUM_LOCK
    //$$ const val KEY_SCROLL: Int = GLFW.GLFW_KEY_SCROLL_LOCK
    //$$ const val KEY_SUBTRACT: Int = GLFW.GLFW_KEY_KP_SUBTRACT
    //$$ const val KEY_ADD: Int = GLFW.GLFW_KEY_KP_ADD
    //$$ const val KEY_DIVIDE: Int = GLFW.GLFW_KEY_KP_DIVIDE
    //$$ const val KEY_DECIMAL: Int = GLFW.GLFW_KEY_KP_DECIMAL
    //$$ const val KEY_NUMPAD0: Int = GLFW.GLFW_KEY_KP_0
    //$$ const val KEY_NUMPAD1: Int = GLFW.GLFW_KEY_KP_1
    //$$ const val KEY_NUMPAD2: Int = GLFW.GLFW_KEY_KP_2
    //$$ const val KEY_NUMPAD3: Int = GLFW.GLFW_KEY_KP_3
    //$$ const val KEY_NUMPAD4: Int = GLFW.GLFW_KEY_KP_4
    //$$ const val KEY_NUMPAD5: Int = GLFW.GLFW_KEY_KP_5
    //$$ const val KEY_NUMPAD6: Int = GLFW.GLFW_KEY_KP_6
    //$$ const val KEY_NUMPAD7: Int = GLFW.GLFW_KEY_KP_7
    //$$ const val KEY_NUMPAD8: Int = GLFW.GLFW_KEY_KP_8
    //$$ const val KEY_NUMPAD9: Int = GLFW.GLFW_KEY_KP_9
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
    //$$ const val KEY_0: Int = GLFW.GLFW_KEY_0
    //$$ const val KEY_1: Int = GLFW.GLFW_KEY_1
    //$$ const val KEY_2: Int = GLFW.GLFW_KEY_2
    //$$ const val KEY_3: Int = GLFW.GLFW_KEY_3
    //$$ const val KEY_4: Int = GLFW.GLFW_KEY_4
    //$$ const val KEY_5: Int = GLFW.GLFW_KEY_5
    //$$ const val KEY_6: Int = GLFW.GLFW_KEY_6
    //$$ const val KEY_7: Int = GLFW.GLFW_KEY_7
    //$$ const val KEY_8: Int = GLFW.GLFW_KEY_8
    //$$ const val KEY_9: Int = GLFW.GLFW_KEY_9
    //$$ const val KEY_F1: Int = GLFW.GLFW_KEY_F1
    //$$ const val KEY_F2: Int = GLFW.GLFW_KEY_F2
    //$$ const val KEY_F3: Int = GLFW.GLFW_KEY_F3
    //$$ const val KEY_F4: Int = GLFW.GLFW_KEY_F4
    //$$ const val KEY_F5: Int = GLFW.GLFW_KEY_F5
    //$$ const val KEY_F6: Int = GLFW.GLFW_KEY_F6
    //$$ const val KEY_F7: Int = GLFW.GLFW_KEY_F7
    //$$ const val KEY_F8: Int = GLFW.GLFW_KEY_F8
    //$$ const val KEY_F9: Int = GLFW.GLFW_KEY_F9
    //$$ const val KEY_F10: Int = GLFW.GLFW_KEY_F10
    //$$ const val KEY_F11: Int = GLFW.GLFW_KEY_F11
    //$$ const val KEY_F12: Int = GLFW.GLFW_KEY_F12
    //$$ const val KEY_F13: Int = GLFW.GLFW_KEY_F13
    //$$ const val KEY_F14: Int = GLFW.GLFW_KEY_F14
    //$$ const val KEY_F15: Int = GLFW.GLFW_KEY_F15
    //$$ const val KEY_F16: Int = GLFW.GLFW_KEY_F16
    //$$ const val KEY_F17: Int = GLFW.GLFW_KEY_F17
    //$$ const val KEY_F18: Int = GLFW.GLFW_KEY_F18
    //$$ const val KEY_F19: Int = GLFW.GLFW_KEY_F19
    //$$ const val KEY_DELETE: Int = GLFW.GLFW_KEY_DELETE
    //$$ const val KEY_HOME: Int = GLFW.GLFW_KEY_HOME
    //$$ const val KEY_END: Int = GLFW.GLFW_KEY_END
    //#else
    const val KEY_NONE: Int = Keyboard.KEY_NONE
    const val KEY_ESCAPE: Int = Keyboard.KEY_ESCAPE
    const val KEY_LMETA: Int = Keyboard.KEY_LMETA
    const val KEY_RMETA: Int = Keyboard.KEY_RMETA
    const val KEY_LCONTROL: Int = Keyboard.KEY_LCONTROL
    const val KEY_RCONTROL: Int = Keyboard.KEY_RCONTROL
    const val KEY_LSHIFT: Int = Keyboard.KEY_LSHIFT
    const val KEY_RSHIFT: Int = Keyboard.KEY_RSHIFT
    const val KEY_LMENU: Int = Keyboard.KEY_LMENU
    const val KEY_RMENU: Int = Keyboard.KEY_RMENU
    const val KEY_MINUS: Int = Keyboard.KEY_MINUS
    const val KEY_EQUALS: Int = Keyboard.KEY_EQUALS
    const val KEY_BACKSPACE: Int = Keyboard.KEY_BACK
    const val KEY_ENTER: Int = Keyboard.KEY_RETURN
    const val KEY_TAB: Int = Keyboard.KEY_TAB
    const val KEY_LBRACKET: Int = Keyboard.KEY_LBRACKET
    const val KEY_RBRACKET: Int = Keyboard.KEY_RBRACKET
    const val KEY_SEMICOLON: Int = Keyboard.KEY_SEMICOLON
    const val KEY_APOSTROPHE: Int = Keyboard.KEY_APOSTROPHE
    const val KEY_GRAVE: Int = Keyboard.KEY_GRAVE
    const val KEY_BACKSLASH: Int = Keyboard.KEY_BACKSLASH
    const val KEY_COMMA: Int = Keyboard.KEY_COMMA
    const val KEY_PERIOD: Int = Keyboard.KEY_PERIOD
    const val KEY_SLASH: Int = Keyboard.KEY_SLASH
    const val KEY_MULTIPLY: Int = Keyboard.KEY_MULTIPLY
    const val KEY_SPACE: Int = Keyboard.KEY_SPACE
    const val KEY_CAPITAL: Int = Keyboard.KEY_CAPITAL
    const val KEY_LEFT: Int = Keyboard.KEY_LEFT
    const val KEY_UP: Int = Keyboard.KEY_UP
    const val KEY_RIGHT: Int = Keyboard.KEY_RIGHT
    const val KEY_DOWN: Int = Keyboard.KEY_DOWN
    const val KEY_NUMLOCK: Int = Keyboard.KEY_NUMLOCK
    const val KEY_SCROLL: Int = Keyboard.KEY_SCROLL
    const val KEY_SUBTRACT: Int = Keyboard.KEY_SUBTRACT
    const val KEY_ADD: Int = Keyboard.KEY_ADD
    const val KEY_DIVIDE: Int = Keyboard.KEY_DIVIDE
    const val KEY_DECIMAL: Int = Keyboard.KEY_DECIMAL
    const val KEY_NUMPAD0: Int = Keyboard.KEY_NUMPAD0
    const val KEY_NUMPAD1: Int = Keyboard.KEY_NUMPAD1
    const val KEY_NUMPAD2: Int = Keyboard.KEY_NUMPAD2
    const val KEY_NUMPAD3: Int = Keyboard.KEY_NUMPAD3
    const val KEY_NUMPAD4: Int = Keyboard.KEY_NUMPAD4
    const val KEY_NUMPAD5: Int = Keyboard.KEY_NUMPAD5
    const val KEY_NUMPAD6: Int = Keyboard.KEY_NUMPAD6
    const val KEY_NUMPAD7: Int = Keyboard.KEY_NUMPAD7
    const val KEY_NUMPAD8: Int = Keyboard.KEY_NUMPAD8
    const val KEY_NUMPAD9: Int = Keyboard.KEY_NUMPAD9
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
    const val KEY_0: Int = Keyboard.KEY_0
    const val KEY_1: Int = Keyboard.KEY_1
    const val KEY_2: Int = Keyboard.KEY_2
    const val KEY_3: Int = Keyboard.KEY_3
    const val KEY_4: Int = Keyboard.KEY_4
    const val KEY_5: Int = Keyboard.KEY_5
    const val KEY_6: Int = Keyboard.KEY_6
    const val KEY_7: Int = Keyboard.KEY_7
    const val KEY_8: Int = Keyboard.KEY_8
    const val KEY_9: Int = Keyboard.KEY_9
    const val KEY_F1: Int = Keyboard.KEY_F1
    const val KEY_F2: Int = Keyboard.KEY_F2
    const val KEY_F3: Int = Keyboard.KEY_F3
    const val KEY_F4: Int = Keyboard.KEY_F4
    const val KEY_F5: Int = Keyboard.KEY_F5
    const val KEY_F6: Int = Keyboard.KEY_F6
    const val KEY_F7: Int = Keyboard.KEY_F7
    const val KEY_F8: Int = Keyboard.KEY_F8
    const val KEY_F9: Int = Keyboard.KEY_F9
    const val KEY_F10: Int = Keyboard.KEY_F10
    const val KEY_F11: Int = Keyboard.KEY_F11
    const val KEY_F12: Int = Keyboard.KEY_F12
    const val KEY_F13: Int = Keyboard.KEY_F13
    const val KEY_F14: Int = Keyboard.KEY_F14
    const val KEY_F15: Int = Keyboard.KEY_F15
    const val KEY_F16: Int = Keyboard.KEY_F16
    const val KEY_F17: Int = Keyboard.KEY_F17
    const val KEY_F18: Int = Keyboard.KEY_F18
    const val KEY_F19: Int = Keyboard.KEY_F19
    const val KEY_DELETE: Int = Keyboard.KEY_DELETE
    const val KEY_HOME: Int = Keyboard.KEY_HOME
    const val KEY_END: Int = Keyboard.KEY_END
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
    fun isCtrlKeyDown(): Boolean = if (UMinecraft.isRunningOnMac) {
        isKeyDown(KEY_LMENU) || isKeyDown(KEY_RMENU)
    } else isKeyDown(KEY_LCONTROL) || isKeyDown(KEY_RCONTROL)

    @JvmStatic
    fun isShiftKeyDown(): Boolean = isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT)

    @JvmStatic
    fun isAltKeyDown(): Boolean = isKeyDown(KEY_LMENU) || isKeyDown(KEY_RMENU)

    @JvmStatic
    fun getModifiers(): Modifiers = Modifiers(isCtrlKeyDown(), isShiftKeyDown(), isAltKeyDown())

    @JvmStatic
    fun isKeyComboCtrlA(key: Int): Boolean = key == KEY_A && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlC(key: Int): Boolean = key == KEY_C && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlV(key: Int): Boolean = key == KEY_V && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlX(key: Int): Boolean = key == KEY_X && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlY(key: Int): Boolean = key == KEY_Y && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlZ(key: Int): Boolean = key == KEY_Z && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()

    @JvmStatic
    fun isKeyComboCtrlShiftZ(key: Int): Boolean = key == KEY_Z && isCtrlKeyDown() && isShiftKeyDown() && !isAltKeyDown()

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
