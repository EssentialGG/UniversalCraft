package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.gui.widget.AbstractButtonWidget;
//#else
//#if MC<11602
import net.minecraft.client.gui.GuiButton;
//#else
//$$ import net.minecraft.client.gui.widget.button.Button;
//#endif
//#endif

public class UniversalGuiButton {
    //#if FABRIC
    //$$ public static int getX(AbstractButtonWidget button) {
    //$$     return button.x;
    //$$ }
    //$$
    //$$ public static int getY(AbstractButtonWidget button) {
    //$$     return button.y;
    //$$ }
    //#else
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
    //#endif
}
