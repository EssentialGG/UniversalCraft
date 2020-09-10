package club.sk1er.mods.core.universal;

//#if MC<11602
import net.minecraft.client.gui.GuiButton;
//#else
//$$ import net.minecraft.client.gui.widget.button.Button;
//#endif

public class UniversalGuiButton {
    //#if MC<11602
    public static int getX(GuiButton button) {
    //#else
    //$$ public static int getX(Button button) {
    //#endif
        //#if MC<=10809
        return button.xPosition;
        //#else
        //$$ return button.x;
        //#endif
    }

    //#if MC<11602
    public static int getY(GuiButton button) {
    //#else
    //$$ public static int getY(Button button) {
    //#endif
        //#if MC<=10809
        return button.yPosition;
        //#else
        //$$ return button.y;
        //#endif
    }
}
