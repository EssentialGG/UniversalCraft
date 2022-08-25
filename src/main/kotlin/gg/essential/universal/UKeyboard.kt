package gg.essential.universal

import net.minecraft.client.settings.KeyBinding

//#if MC>=11600
//$$ import gg.essential.universal.wrappers.message.UTextComponent
//#endif

//#if MC>=11502
//$$ import org.lwjgl.glfw.GLFW
//$$ import net.minecraft.client.Minecraft
//$$ import net.minecraft.client.util.InputMappings
//#else
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse

//#endif

object UKeyboard {
    //#if MC>=11502
    //$$ @JvmField val KEY_NONE: Int = noInline { InputMappings.INPUT_INVALID.keyCode }
    //$$ @JvmField val KEY_ESCAPE: Int = noInline { GLFW.GLFW_KEY_ESCAPE }
    //$$ @JvmField val KEY_LMETA: Int = noInline { GLFW.GLFW_KEY_LEFT_SUPER } // TODO: Correct?
    //$$ @JvmField val KEY_RMETA: Int = noInline { GLFW.GLFW_KEY_RIGHT_SUPER } // TODO: Correct?
    //$$ @JvmField val KEY_LCONTROL: Int = noInline { GLFW.GLFW_KEY_LEFT_CONTROL }
    //$$ @JvmField val KEY_RCONTROL: Int = noInline { GLFW.GLFW_KEY_RIGHT_CONTROL }
    //$$ @JvmField val KEY_LSHIFT: Int = noInline { GLFW.GLFW_KEY_LEFT_SHIFT }
    //$$ @JvmField val KEY_RSHIFT: Int = noInline { GLFW.GLFW_KEY_RIGHT_SHIFT }
    //$$ @JvmField val KEY_LMENU: Int = noInline { GLFW.GLFW_KEY_LEFT_ALT }
    //$$ @JvmField val KEY_RMENU: Int = noInline { GLFW.GLFW_KEY_RIGHT_ALT }
    //$$ @JvmField val KEY_MINUS: Int = noInline { GLFW.GLFW_KEY_MINUS }
    //$$ @JvmField val KEY_EQUALS: Int = noInline { GLFW.GLFW_KEY_EQUAL }
    //$$ @JvmField val KEY_BACKSPACE: Int = noInline { GLFW.GLFW_KEY_BACKSPACE }
    //$$ @JvmField val KEY_ENTER: Int = noInline { GLFW.GLFW_KEY_ENTER }
    //$$ @JvmField val KEY_TAB: Int = noInline { GLFW.GLFW_KEY_TAB }
    //$$ @JvmField val KEY_LBRACKET: Int = noInline { GLFW.GLFW_KEY_LEFT_BRACKET }
    //$$ @JvmField val KEY_RBRACKET: Int = noInline { GLFW.GLFW_KEY_RIGHT_BRACKET }
    //$$ @JvmField val KEY_SEMICOLON: Int = noInline { GLFW.GLFW_KEY_SEMICOLON }
    //$$ @JvmField val KEY_APOSTROPHE: Int = noInline { GLFW.GLFW_KEY_APOSTROPHE }
    //$$ @JvmField val KEY_GRAVE: Int = noInline { GLFW.GLFW_KEY_GRAVE_ACCENT }
    //$$ @JvmField val KEY_BACKSLASH: Int = noInline { GLFW.GLFW_KEY_BACKSLASH }
    //$$ @JvmField val KEY_COMMA: Int = noInline { GLFW.GLFW_KEY_COMMA }
    //$$ @JvmField val KEY_PERIOD: Int = noInline { GLFW.GLFW_KEY_PERIOD }
    //$$ @JvmField val KEY_SLASH: Int = noInline { GLFW.GLFW_KEY_SLASH }
    //$$ @JvmField val KEY_MULTIPLY: Int = noInline { GLFW.GLFW_KEY_KP_MULTIPLY }
    //$$ @JvmField val KEY_SPACE: Int = noInline { GLFW.GLFW_KEY_SPACE }
    //$$ @JvmField val KEY_CAPITAL: Int = noInline { GLFW.GLFW_KEY_CAPS_LOCK }
    //$$ @JvmField val KEY_LEFT: Int = noInline { GLFW.GLFW_KEY_LEFT }
    //$$ @JvmField val KEY_UP: Int = noInline { GLFW.GLFW_KEY_UP }
    //$$ @JvmField val KEY_RIGHT: Int = noInline { GLFW.GLFW_KEY_RIGHT }
    //$$ @JvmField val KEY_DOWN: Int = noInline { GLFW.GLFW_KEY_DOWN }
    //$$ @JvmField val KEY_NUMLOCK: Int = noInline { GLFW.GLFW_KEY_NUM_LOCK }
    //$$ @JvmField val KEY_SCROLL: Int = noInline { GLFW.GLFW_KEY_SCROLL_LOCK }
    //$$ @JvmField val KEY_SUBTRACT: Int = noInline { GLFW.GLFW_KEY_KP_SUBTRACT }
    //$$ @JvmField val KEY_ADD: Int = noInline { GLFW.GLFW_KEY_KP_ADD }
    //$$ @JvmField val KEY_DIVIDE: Int = noInline { GLFW.GLFW_KEY_KP_DIVIDE }
    //$$ @JvmField val KEY_DECIMAL: Int = noInline { GLFW.GLFW_KEY_KP_DECIMAL }
    //$$ @JvmField val KEY_NUMPAD0: Int = noInline { GLFW.GLFW_KEY_KP_0 }
    //$$ @JvmField val KEY_NUMPAD1: Int = noInline { GLFW.GLFW_KEY_KP_1 }
    //$$ @JvmField val KEY_NUMPAD2: Int = noInline { GLFW.GLFW_KEY_KP_2 }
    //$$ @JvmField val KEY_NUMPAD3: Int = noInline { GLFW.GLFW_KEY_KP_3 }
    //$$ @JvmField val KEY_NUMPAD4: Int = noInline { GLFW.GLFW_KEY_KP_4 }
    //$$ @JvmField val KEY_NUMPAD5: Int = noInline { GLFW.GLFW_KEY_KP_5 }
    //$$ @JvmField val KEY_NUMPAD6: Int = noInline { GLFW.GLFW_KEY_KP_6 }
    //$$ @JvmField val KEY_NUMPAD7: Int = noInline { GLFW.GLFW_KEY_KP_7 }
    //$$ @JvmField val KEY_NUMPAD8: Int = noInline { GLFW.GLFW_KEY_KP_8 }
    //$$ @JvmField val KEY_NUMPAD9: Int = noInline { GLFW.GLFW_KEY_KP_9 }
    //$$ @JvmField val KEY_A: Int = noInline { GLFW.GLFW_KEY_A }
    //$$ @JvmField val KEY_B: Int = noInline { GLFW.GLFW_KEY_B }
    //$$ @JvmField val KEY_C: Int = noInline { GLFW.GLFW_KEY_C }
    //$$ @JvmField val KEY_D: Int = noInline { GLFW.GLFW_KEY_D }
    //$$ @JvmField val KEY_E: Int = noInline { GLFW.GLFW_KEY_E }
    //$$ @JvmField val KEY_F: Int = noInline { GLFW.GLFW_KEY_F }
    //$$ @JvmField val KEY_G: Int = noInline { GLFW.GLFW_KEY_G }
    //$$ @JvmField val KEY_H: Int = noInline { GLFW.GLFW_KEY_H }
    //$$ @JvmField val KEY_I: Int = noInline { GLFW.GLFW_KEY_I }
    //$$ @JvmField val KEY_J: Int = noInline { GLFW.GLFW_KEY_J }
    //$$ @JvmField val KEY_K: Int = noInline { GLFW.GLFW_KEY_K }
    //$$ @JvmField val KEY_L: Int = noInline { GLFW.GLFW_KEY_L }
    //$$ @JvmField val KEY_M: Int = noInline { GLFW.GLFW_KEY_M }
    //$$ @JvmField val KEY_N: Int = noInline { GLFW.GLFW_KEY_N }
    //$$ @JvmField val KEY_O: Int = noInline { GLFW.GLFW_KEY_O }
    //$$ @JvmField val KEY_P: Int = noInline { GLFW.GLFW_KEY_P }
    //$$ @JvmField val KEY_Q: Int = noInline { GLFW.GLFW_KEY_Q }
    //$$ @JvmField val KEY_R: Int = noInline { GLFW.GLFW_KEY_R }
    //$$ @JvmField val KEY_S: Int = noInline { GLFW.GLFW_KEY_S }
    //$$ @JvmField val KEY_T: Int = noInline { GLFW.GLFW_KEY_T }
    //$$ @JvmField val KEY_U: Int = noInline { GLFW.GLFW_KEY_U }
    //$$ @JvmField val KEY_V: Int = noInline { GLFW.GLFW_KEY_V }
    //$$ @JvmField val KEY_W: Int = noInline { GLFW.GLFW_KEY_W }
    //$$ @JvmField val KEY_X: Int = noInline { GLFW.GLFW_KEY_X }
    //$$ @JvmField val KEY_Y: Int = noInline { GLFW.GLFW_KEY_Y }
    //$$ @JvmField val KEY_Z: Int = noInline { GLFW.GLFW_KEY_Z }
    //$$ @JvmField val KEY_0: Int = noInline { GLFW.GLFW_KEY_0 }
    //$$ @JvmField val KEY_1: Int = noInline { GLFW.GLFW_KEY_1 }
    //$$ @JvmField val KEY_2: Int = noInline { GLFW.GLFW_KEY_2 }
    //$$ @JvmField val KEY_3: Int = noInline { GLFW.GLFW_KEY_3 }
    //$$ @JvmField val KEY_4: Int = noInline { GLFW.GLFW_KEY_4 }
    //$$ @JvmField val KEY_5: Int = noInline { GLFW.GLFW_KEY_5 }
    //$$ @JvmField val KEY_6: Int = noInline { GLFW.GLFW_KEY_6 }
    //$$ @JvmField val KEY_7: Int = noInline { GLFW.GLFW_KEY_7 }
    //$$ @JvmField val KEY_8: Int = noInline { GLFW.GLFW_KEY_8 }
    //$$ @JvmField val KEY_9: Int = noInline { GLFW.GLFW_KEY_9 }
    //$$ @JvmField val KEY_F1: Int = noInline { GLFW.GLFW_KEY_F1 }
    //$$ @JvmField val KEY_F2: Int = noInline { GLFW.GLFW_KEY_F2 }
    //$$ @JvmField val KEY_F3: Int = noInline { GLFW.GLFW_KEY_F3 }
    //$$ @JvmField val KEY_F4: Int = noInline { GLFW.GLFW_KEY_F4 }
    //$$ @JvmField val KEY_F5: Int = noInline { GLFW.GLFW_KEY_F5 }
    //$$ @JvmField val KEY_F6: Int = noInline { GLFW.GLFW_KEY_F6 }
    //$$ @JvmField val KEY_F7: Int = noInline { GLFW.GLFW_KEY_F7 }
    //$$ @JvmField val KEY_F8: Int = noInline { GLFW.GLFW_KEY_F8 }
    //$$ @JvmField val KEY_F9: Int = noInline { GLFW.GLFW_KEY_F9 }
    //$$ @JvmField val KEY_F10: Int = noInline { GLFW.GLFW_KEY_F10 }
    //$$ @JvmField val KEY_F11: Int = noInline { GLFW.GLFW_KEY_F11 }
    //$$ @JvmField val KEY_F12: Int = noInline { GLFW.GLFW_KEY_F12 }
    //$$ @JvmField val KEY_F13: Int = noInline { GLFW.GLFW_KEY_F13 }
    //$$ @JvmField val KEY_F14: Int = noInline { GLFW.GLFW_KEY_F14 }
    //$$ @JvmField val KEY_F15: Int = noInline { GLFW.GLFW_KEY_F15 }
    //$$ @JvmField val KEY_F16: Int = noInline { GLFW.GLFW_KEY_F16 }
    //$$ @JvmField val KEY_F17: Int = noInline { GLFW.GLFW_KEY_F17 }
    //$$ @JvmField val KEY_F18: Int = noInline { GLFW.GLFW_KEY_F18 }
    //$$ @JvmField val KEY_F19: Int = noInline { GLFW.GLFW_KEY_F19 }
    //$$ @JvmField val KEY_DELETE: Int = noInline { GLFW.GLFW_KEY_DELETE }
    //$$ @JvmField val KEY_HOME: Int = noInline { GLFW.GLFW_KEY_HOME }
    //$$ @JvmField val KEY_END: Int = noInline { GLFW.GLFW_KEY_END }
    //#else
    @JvmField val KEY_NONE: Int = noInline { Keyboard.KEY_NONE }
    @JvmField val KEY_ESCAPE: Int = noInline { Keyboard.KEY_ESCAPE }
    @JvmField val KEY_LMETA: Int = noInline { Keyboard.KEY_LMETA }
    @JvmField val KEY_RMETA: Int = noInline { Keyboard.KEY_RMETA }
    @JvmField val KEY_LCONTROL: Int = noInline { Keyboard.KEY_LCONTROL }
    @JvmField val KEY_RCONTROL: Int = noInline { Keyboard.KEY_RCONTROL }
    @JvmField val KEY_LSHIFT: Int = noInline { Keyboard.KEY_LSHIFT }
    @JvmField val KEY_RSHIFT: Int = noInline { Keyboard.KEY_RSHIFT }
    @JvmField val KEY_LMENU: Int = noInline { Keyboard.KEY_LMENU }
    @JvmField val KEY_RMENU: Int = noInline { Keyboard.KEY_RMENU }
    @JvmField val KEY_MINUS: Int = noInline { Keyboard.KEY_MINUS }
    @JvmField val KEY_EQUALS: Int = noInline { Keyboard.KEY_EQUALS }
    @JvmField val KEY_BACKSPACE: Int = noInline { Keyboard.KEY_BACK }
    @JvmField val KEY_ENTER: Int = noInline { Keyboard.KEY_RETURN }
    @JvmField val KEY_TAB: Int = noInline { Keyboard.KEY_TAB }
    @JvmField val KEY_LBRACKET: Int = noInline { Keyboard.KEY_LBRACKET }
    @JvmField val KEY_RBRACKET: Int = noInline { Keyboard.KEY_RBRACKET }
    @JvmField val KEY_SEMICOLON: Int = noInline { Keyboard.KEY_SEMICOLON }
    @JvmField val KEY_APOSTROPHE: Int = noInline { Keyboard.KEY_APOSTROPHE }
    @JvmField val KEY_GRAVE: Int = noInline { Keyboard.KEY_GRAVE }
    @JvmField val KEY_BACKSLASH: Int = noInline { Keyboard.KEY_BACKSLASH }
    @JvmField val KEY_COMMA: Int = noInline { Keyboard.KEY_COMMA }
    @JvmField val KEY_PERIOD: Int = noInline { Keyboard.KEY_PERIOD }
    @JvmField val KEY_SLASH: Int = noInline { Keyboard.KEY_SLASH }
    @JvmField val KEY_MULTIPLY: Int = noInline { Keyboard.KEY_MULTIPLY }
    @JvmField val KEY_SPACE: Int = noInline { Keyboard.KEY_SPACE }
    @JvmField val KEY_CAPITAL: Int = noInline { Keyboard.KEY_CAPITAL }
    @JvmField val KEY_LEFT: Int = noInline { Keyboard.KEY_LEFT }
    @JvmField val KEY_UP: Int = noInline { Keyboard.KEY_UP }
    @JvmField val KEY_RIGHT: Int = noInline { Keyboard.KEY_RIGHT }
    @JvmField val KEY_DOWN: Int = noInline { Keyboard.KEY_DOWN }
    @JvmField val KEY_NUMLOCK: Int = noInline { Keyboard.KEY_NUMLOCK }
    @JvmField val KEY_SCROLL: Int = noInline { Keyboard.KEY_SCROLL }
    @JvmField val KEY_SUBTRACT: Int = noInline { Keyboard.KEY_SUBTRACT }
    @JvmField val KEY_ADD: Int = noInline { Keyboard.KEY_ADD }
    @JvmField val KEY_DIVIDE: Int = noInline { Keyboard.KEY_DIVIDE }
    @JvmField val KEY_DECIMAL: Int = noInline { Keyboard.KEY_DECIMAL }
    @JvmField val KEY_NUMPAD0: Int = noInline { Keyboard.KEY_NUMPAD0 }
    @JvmField val KEY_NUMPAD1: Int = noInline { Keyboard.KEY_NUMPAD1 }
    @JvmField val KEY_NUMPAD2: Int = noInline { Keyboard.KEY_NUMPAD2 }
    @JvmField val KEY_NUMPAD3: Int = noInline { Keyboard.KEY_NUMPAD3 }
    @JvmField val KEY_NUMPAD4: Int = noInline { Keyboard.KEY_NUMPAD4 }
    @JvmField val KEY_NUMPAD5: Int = noInline { Keyboard.KEY_NUMPAD5 }
    @JvmField val KEY_NUMPAD6: Int = noInline { Keyboard.KEY_NUMPAD6 }
    @JvmField val KEY_NUMPAD7: Int = noInline { Keyboard.KEY_NUMPAD7 }
    @JvmField val KEY_NUMPAD8: Int = noInline { Keyboard.KEY_NUMPAD8 }
    @JvmField val KEY_NUMPAD9: Int = noInline { Keyboard.KEY_NUMPAD9 }
    @JvmField val KEY_A: Int = noInline { Keyboard.KEY_A }
    @JvmField val KEY_B: Int = noInline { Keyboard.KEY_B }
    @JvmField val KEY_C: Int = noInline { Keyboard.KEY_C }
    @JvmField val KEY_D: Int = noInline { Keyboard.KEY_D }
    @JvmField val KEY_E: Int = noInline { Keyboard.KEY_E }
    @JvmField val KEY_F: Int = noInline { Keyboard.KEY_F }
    @JvmField val KEY_G: Int = noInline { Keyboard.KEY_G }
    @JvmField val KEY_H: Int = noInline { Keyboard.KEY_H }
    @JvmField val KEY_I: Int = noInline { Keyboard.KEY_I }
    @JvmField val KEY_J: Int = noInline { Keyboard.KEY_J }
    @JvmField val KEY_K: Int = noInline { Keyboard.KEY_K }
    @JvmField val KEY_L: Int = noInline { Keyboard.KEY_L }
    @JvmField val KEY_M: Int = noInline { Keyboard.KEY_M }
    @JvmField val KEY_N: Int = noInline { Keyboard.KEY_N }
    @JvmField val KEY_O: Int = noInline { Keyboard.KEY_O }
    @JvmField val KEY_P: Int = noInline { Keyboard.KEY_P }
    @JvmField val KEY_Q: Int = noInline { Keyboard.KEY_Q }
    @JvmField val KEY_R: Int = noInline { Keyboard.KEY_R }
    @JvmField val KEY_S: Int = noInline { Keyboard.KEY_S }
    @JvmField val KEY_T: Int = noInline { Keyboard.KEY_T }
    @JvmField val KEY_U: Int = noInline { Keyboard.KEY_U }
    @JvmField val KEY_V: Int = noInline { Keyboard.KEY_V }
    @JvmField val KEY_W: Int = noInline { Keyboard.KEY_W }
    @JvmField val KEY_X: Int = noInline { Keyboard.KEY_X }
    @JvmField val KEY_Y: Int = noInline { Keyboard.KEY_Y }
    @JvmField val KEY_Z: Int = noInline { Keyboard.KEY_Z }
    @JvmField val KEY_0: Int = noInline { Keyboard.KEY_0 }
    @JvmField val KEY_1: Int = noInline { Keyboard.KEY_1 }
    @JvmField val KEY_2: Int = noInline { Keyboard.KEY_2 }
    @JvmField val KEY_3: Int = noInline { Keyboard.KEY_3 }
    @JvmField val KEY_4: Int = noInline { Keyboard.KEY_4 }
    @JvmField val KEY_5: Int = noInline { Keyboard.KEY_5 }
    @JvmField val KEY_6: Int = noInline { Keyboard.KEY_6 }
    @JvmField val KEY_7: Int = noInline { Keyboard.KEY_7 }
    @JvmField val KEY_8: Int = noInline { Keyboard.KEY_8 }
    @JvmField val KEY_9: Int = noInline { Keyboard.KEY_9 }
    @JvmField val KEY_F1: Int = noInline { Keyboard.KEY_F1 }
    @JvmField val KEY_F2: Int = noInline { Keyboard.KEY_F2 }
    @JvmField val KEY_F3: Int = noInline { Keyboard.KEY_F3 }
    @JvmField val KEY_F4: Int = noInline { Keyboard.KEY_F4 }
    @JvmField val KEY_F5: Int = noInline { Keyboard.KEY_F5 }
    @JvmField val KEY_F6: Int = noInline { Keyboard.KEY_F6 }
    @JvmField val KEY_F7: Int = noInline { Keyboard.KEY_F7 }
    @JvmField val KEY_F8: Int = noInline { Keyboard.KEY_F8 }
    @JvmField val KEY_F9: Int = noInline { Keyboard.KEY_F9 }
    @JvmField val KEY_F10: Int = noInline { Keyboard.KEY_F10 }
    @JvmField val KEY_F11: Int = noInline { Keyboard.KEY_F11 }
    @JvmField val KEY_F12: Int = noInline { Keyboard.KEY_F12 }
    @JvmField val KEY_F13: Int = noInline { Keyboard.KEY_F13 }
    @JvmField val KEY_F14: Int = noInline { Keyboard.KEY_F14 }
    @JvmField val KEY_F15: Int = noInline { Keyboard.KEY_F15 }
    @JvmField val KEY_F16: Int = noInline { Keyboard.KEY_F16 }
    @JvmField val KEY_F17: Int = noInline { Keyboard.KEY_F17 }
    @JvmField val KEY_F18: Int = noInline { Keyboard.KEY_F18 }
    @JvmField val KEY_F19: Int = noInline { Keyboard.KEY_F19 }
    @JvmField val KEY_DELETE: Int = noInline { Keyboard.KEY_DELETE }
    @JvmField val KEY_HOME: Int = noInline { Keyboard.KEY_HOME }
    @JvmField val KEY_END: Int = noInline { Keyboard.KEY_END }
    //#endif

    // Prevents the given initializer from being inlined into the field initializer.
    // If we don't use this to initialize the KEY_ field, then consumers of the lib will in turn also inline the
    // constants. But these constants are different between Minecraft versions, so code built against one version will
    // not work properly with a different version if these constants are inlined.
    // Funnily enough, the function named `noInline` can actually be `inline` in the Kotlin sense. It'll only be inlined
    // into the static initializer, not into the field itself.
    private inline fun <T> noInline(init: () -> T): T = init()

    @JvmStatic
    fun allowRepeatEvents(enabled: Boolean) {
        //#if MC>=11502
        //$$ UMinecraft.getMinecraft().keyboardListener.enableRepeatEvents(enabled)
        //#else
        Keyboard.enableRepeatEvents(enabled)
        //#endif
    }

    @JvmStatic
    fun isCtrlKeyDown(): Boolean = if (UMinecraft.isRunningOnMac) {
        isKeyDown(KEY_LMETA) || isKeyDown(KEY_RMETA)
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
        if (key == KEY_NONE) return false
        //#if MC>=11502
        //$$ val window = UMinecraft.getMinecraft().mainWindow.handle
        //$$ val state = if (key < 20) GLFW.glfwGetMouseButton(window, key) else GLFW.glfwGetKey(window, key)
        //$$ return state == GLFW.GLFW_PRESS
        //#else
        return if (key < 0) {
            Mouse.isButtonDown(key + 100)
        } else if (key < Keyboard.KEYBOARD_SIZE) {
            Keyboard.isKeyDown(key)
        } else {
            false
        }
        //#endif
    }

    /**
     * Returns the name of the key assigned to the specified binding as appropriate for display to the user.
     *
     * May (or may not) return `null` if the key is not bound or the name is unknown. Do not rely on any specific
     * behavior. If `null` is returned, assume the key to be unknown; the reverse is not true. If it makes a difference
     * for you, check whether there is a key bound separately before calling this method.
     */
    @JvmStatic
    fun getKeyName(keyBinding: KeyBinding): String? {
        //#if MC>=11400
        //#if MC>=11600
        //$$ return UTextComponent(keyBinding.func_238171_j_()).unformattedText.let {
        //#else
        //$$ return keyBinding.localizedName?.let {
        //#endif
        //$$     // If it's a single character, GLFW will give us a lowercase version but that's very weird and
        //$$     // inconsistent with old versions, so we uppercase it. Longer ones are already fine (e.g. "Space").
        //$$     if (it.length == 1) it.uppercase() else it
        //$$ }
        //#else
        //#if MC==11202 && FORGE==1 || MC>=11300
        //$$ return keyBinding.getDisplayName()
        //#else
        //todo preprocessor bug, doesnt remap this automatically
        //#if FABRIC==1
        //$$ return net.minecraft.client.options.GameOptions.getFormattedNameForKeyCode(keyBinding.code)
        //#else
        return net.minecraft.client.settings.GameSettings.getKeyDisplayString(keyBinding.keyCode)
        //#endif
        //#endif
        //#endif
    }

    @Deprecated("Does not work for mouse bindings", replaceWith = ReplaceWith("getKeyName(keyBinding)"))
    @JvmStatic
    fun getKeyName(keyCode: Int, scanCode: Int): String? {
        //#if MC>=11502
        //$$ return GLFW.glfwGetKeyName(keyCode, scanCode)?.let {
        //$$     // If it's a single character, GLFW will give us a lowercase version but that's very weird and
        //$$     // inconsistent with old versions, so we uppercase it. Longer ones are already fine (e.g. "Space").
        //$$     if (it.length == 1) it.uppercase() else it
        //$$ }
        //#else
        return Keyboard.getKeyName(keyCode)
        //#endif
    }

    @Deprecated("Does not work for mouse or scanCode-type bindings", replaceWith = ReplaceWith("getKeyName(keyCode, -1)"))
    @JvmStatic
    fun getKeyName(keyCode: Int): String? = getKeyName(keyCode, -1)

    data class Modifiers(val isCtrl: Boolean, val isShift: Boolean, val isAlt: Boolean)

    //#if MC>=11502
    //$$ internal fun Modifiers?.toInt() = listOf(
    //$$     this?.isCtrl to GLFW.GLFW_MOD_CONTROL,
    //$$     this?.isShift to GLFW.GLFW_MOD_SHIFT,
    //$$     this?.isAlt to GLFW.GLFW_MOD_ALT,
    //$$ ).sumOf { (modifier, value) -> if (modifier == true) value else 0 }
    //$$
    //$$ internal fun Int.toModifiers() = Modifiers(
    //$$     isCtrl = (this and GLFW.GLFW_MOD_CONTROL) != 0,
    //$$     isShift = (this and GLFW.GLFW_MOD_SHIFT) != 0,
    //$$     isAlt = (this and GLFW.GLFW_MOD_ALT) != 0,
    //$$ )
    //#endif
}
