package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.util.InputUtil;
//#else
//$$ import net.minecraft.client.Minecraft;
//#if MC<=11202
import org.lwjgl.input.Keyboard;
//#else
//$$ import net.minecraft.client.util.InputMappings;
//#endif
//#endif


public class UniversalKeyboard {
    // These are taken from LWJGL2's Keyboard class for compatibility between MC versions.
    public static final int KEY_LMETA = 0xDB;
    public static final int KEY_RMETA = 0xDC;
    public static final int KEY_LCONTROL = 0x1D;
    public static final int KEY_RCONTROL = 0x9D;
    public static final int KEY_LSHIFT = 0x2A;
    public static final int KEY_RSHIFT = 0x36;
    public static final int KEY_LMENU = 0x38;
    public static final int KEY_RMENU = 0xB8;
    public static final int KEY_X = 0x2D;
    public static final int KEY_V = 0x2F;
    public static final int KEY_C = 0x2E;
    public static final int KEY_A = 0x1E;
    public static final int KEY_Z = 0x2C;
    public static final int KEY_Y = 0x15;

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
