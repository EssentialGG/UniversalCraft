package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.gui.widget.AbstractButtonWidget;
//#elseif MC>=11602
//$$ import net.minecraft.client.gui.widget.button.Button;
//#else
import net.minecraft.client.gui.GuiButton;
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
    //#if MC>=11602
    //$$ public static int getX(Button button) {
    //#else
    public static int getX(GuiButton button) {
    //#endif
        //#if MC>10809
        //$$ return button.x;
        //#else
        return button.xPosition;
        //#endif
    }

    //#if MC>=11602
    //$$ public static int getY(Button button) {
    //#else
    public static int getY(GuiButton button) {
    //#endif
        //#if MC>10809
        //$$ return button.y;
        //#else
        return button.yPosition;
        //#endif
    }
    //#endif
}
