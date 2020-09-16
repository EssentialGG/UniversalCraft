package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.util.InputUtil;
//$$ import org.lwjgl.glfw.GLFW;
//#else
//$$ import net.minecraft.client.Minecraft;
//#if MC<=11202
import org.lwjgl.input.Keyboard;
//#else
//$$ import org.lwjgl.glfw.GLFW;
//$$ import net.minecraft.client.util.InputMappings;
//#endif
//#endif

public class UniversalKeyboard {
    //#if MC>=11502
    //$$ public static final int KEY_LMETA = GLFW.GLFW_KEY_LEFT_SUPER; // TODO: Correct?
    //$$ public static final int KEY_RMETA = GLFW.GLFW_KEY_RIGHT_SUPER; // TODO: Correct?
    //$$ public static final int KEY_LCONTROL = GLFW.GLFW_KEY_LEFT_CONTROL;
    //$$ public static final int KEY_RCONTROL = GLFW.GLFW_KEY_RIGHT_CONTROL;
    //$$ public static final int KEY_LSHIFT = GLFW.GLFW_KEY_LEFT_SHIFT;
    //$$ public static final int KEY_RSHIFT = GLFW.GLFW_KEY_RIGHT_SHIFT;
    //$$ public static final int KEY_LMENU = GLFW.GLFW_KEY_LEFT_ALT;
    //$$ public static final int KEY_RMENU = GLFW.GLFW_KEY_RIGHT_ALT;
    //$$ public static final int KEY_X = GLFW.GLFW_KEY_X;
    //$$ public static final int KEY_V = GLFW.GLFW_KEY_V;
    //$$ public static final int KEY_C = GLFW.GLFW_KEY_C;
    //$$ public static final int KEY_A = GLFW.GLFW_KEY_A;
    //$$ public static final int KEY_Z = GLFW.GLFW_KEY_Z;
    //$$ public static final int KEY_Y = GLFW.GLFW_KEY_Y;
    //#else
    public static final int KEY_LMETA = Keyboard.KEY_LMETA;
    public static final int KEY_RMETA = Keyboard.KEY_RMETA;
    public static final int KEY_LCONTROL = Keyboard.KEY_LCONTROL;
    public static final int KEY_RCONTROL = Keyboard.KEY_RCONTROL;
    public static final int KEY_LSHIFT = Keyboard.KEY_LSHIFT;
    public static final int KEY_RSHIFT = Keyboard.KEY_RSHIFT;
    public static final int KEY_LMENU = Keyboard.KEY_LMENU;
    public static final int KEY_RMENU = Keyboard.KEY_RMENU;
    public static final int KEY_X = Keyboard.KEY_X;
    public static final int KEY_V = Keyboard.KEY_V;
    public static final int KEY_C = Keyboard.KEY_C;
    public static final int KEY_A = Keyboard.KEY_A;
    public static final int KEY_Z = Keyboard.KEY_Z;
    public static final int KEY_Y = Keyboard.KEY_Y;
    //#endif

    public static void enableRepeatEvents(boolean enable) {
        //#if FABRIC
        //$$ UniversalMinecraft.getMinecraft().keyboard.setRepeatEvents(enable);
        //#else
        //#if MC<=11202
        Keyboard.enableRepeatEvents(enable);
        //#else
        //$$ UniversalMinecraft.getMinecraft().keyboardListener.enableRepeatEvents(enable);
        //#endif
        //#endif
    }

    /**
     * Get current state of the ctrl key
     * @return true if either control (or command on mac) is pressed
     */
    public static boolean isCtrlKeyDown() {
        return UniversalMinecraft.isRunningOnMac ? isKeyDown(KEY_LMETA) || isKeyDown(KEY_RMETA) : isKeyDown(KEY_LCONTROL) || isKeyDown(KEY_RCONTROL);
    }

    /**
     * Get current state of the shift key
     * @return true if either shift key is pressed
     */
    public static boolean isShiftKeyDown() {
        return isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT);
    }

    /**
     * Get current state of the alt key
     * @return true if either alt key is pressed
     */
    public static boolean isAltKeyDown() {
        return isKeyDown(KEY_LMENU) || isKeyDown(KEY_RMENU);
    }

    public static Modifier getModifiers() {
        return new Modifier(
                isCtrlKeyDown(),
                isShiftKeyDown(),
                isAltKeyDown()
        );
    }

    public static boolean isKeyDown(int key) {
        //#if FABRIC
        //$$ return InputUtil.isKeyPressed(UniversalMinecraft.getMinecraft().getWindow().getHandle(), key);
        //#else
        //#if MC<=11202
        return Keyboard.isKeyDown(key);
        //#else
        //$$ return InputMappings.isKeyDown(UniversalMinecraft.getMinecraft().getMainWindow().getHandle(), key);
        //#endif
        //#endif
    }

    /**
     * Decide if an input is a cut function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlX(int keyID) {
        return keyID == KEY_X && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }


    /**
     * Decide if an input is a paste function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlV(int keyID) {
        return keyID == KEY_V && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Decide if an input is a copy function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlC(int keyID) {
        return keyID == KEY_C && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Decide if an input is a select all function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlA(int keyID) {
        return keyID == KEY_A && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }


    /**
     * Decide if an input is a undo function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlZ(int keyID) {
        return keyID == KEY_Z && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Decide if an input is a redo function (Control + Y)
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlY(int keyID) {
        return keyID == KEY_Y && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Decide if an input is a redo function (Control + Shift + Z)
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlShiftZ(int keyID) {
        return keyID == KEY_Z && isCtrlKeyDown() && isShiftKeyDown() && !isAltKeyDown();
    }

    public static class Modifier {
        private final boolean isCtrl;
        private final boolean isShift;
        private final boolean isAlt;

        public Modifier(boolean isCtrl, boolean isShift, boolean isAlt) {
            this.isCtrl = isCtrl;
            this.isShift = isShift;
            this.isAlt = isAlt;
        }

        public boolean isCtrl() {
            return isCtrl;
        }

        public boolean isShift() {
            return isShift;
        }

        public boolean isAlt() {
            return isAlt;
        }
    }
}
