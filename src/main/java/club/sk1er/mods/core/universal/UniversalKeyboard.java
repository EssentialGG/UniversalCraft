package club.sk1er.mods.core.universal;

//#if MC<=11202
import org.lwjgl.input.Keyboard;
//#endif

public class UniversalKeyboard {
    public static void enableRepeatEvents(boolean enable) {
        //#if MC<=11202
        Keyboard.enableRepeatEvents(enable);
        //#else
        //$$ UniversalMinecraft.getMinecraft().keyboardListener.enableRepeatEvents(enable);
        //#endif
    }
}
