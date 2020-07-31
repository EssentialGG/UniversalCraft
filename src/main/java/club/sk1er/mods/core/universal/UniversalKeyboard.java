package club.sk1er.mods.core.universal;

import net.minecraft.client.Minecraft;
//#if MC<=11202
import org.lwjgl.input.Keyboard;

import javax.swing.text.JTextComponent;
import java.security.Key;
//#else
//$$ import net.minecraft.client.util.InputMappings;
//#endif

public class UniversalKeyboard {
    public static void enableRepeatEvents(boolean enable) {
        //#if MC<=11202
        Keyboard.enableRepeatEvents(enable);
        //#else
        //$$ UniversalMinecraft.getMinecraft().keyboardListener.enableRepeatEvents(enable);
        //#endif
    }

    /**
     * Get current state of the ctrl key
     * @return true if either control (or command on mac) is pressed
     */
    public static boolean isCtrlKeyDown() {
        //#if MC<=11202
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA) : Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
        //#else
        //$$  return Minecraft.IS_RUNNING_ON_MAC ?
        //$$      InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 343) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 347)
        //$$      :
        //$$      InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 341) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 345);
        //#endif

    }


    /**
     * Get current state of the shift key
     * @return true if either shift key is pressed
     */
    public static boolean isShiftKeyDown() {
        //#if MC<=11202
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
        //#else
        //$$ return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344);
        //#endif
    }


    /**
     * Get current state of the alt key
     * @return true if either alt key is pressed
     */
    public static boolean isAltKeyDown() {
        //#if MC<=11202
        return Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU);
        //#else
        //$$       return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 342) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 346);
        //#endif

    }


    /**
     * Decide if an input is a cut function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlX(int keyID) {
        return keyID == Keyboard.KEY_X && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }


    /**
     * Decide if an input is a paste function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlV(int keyID) {
        return keyID == Keyboard.KEY_V && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Decide if an input is a copy function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlC(int keyID) {
        return keyID == Keyboard.KEY_C && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Decide if an input is a select all function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlA(int keyID) {
        return keyID == Keyboard.KEY_A && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }


    /**
     * Decide if an input is a undo function
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlZ(int keyID) {
        return keyID == Keyboard.KEY_Z && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Decide if an input is a redo function (Control + Y)
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlY(int keyID) {
        return keyID == Keyboard.KEY_Y && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    /**
     * Decide if an input is a redo function (Control + Shift + Z)
     * @return true if the appropriate keys are down and the right key was pressed
     */
    public static boolean isKeyComboCtrlShiftZ(int keyID) {
        return keyID == Keyboard.KEY_Z && isCtrlKeyDown() && isShiftKeyDown() && !isAltKeyDown();
    }



}
