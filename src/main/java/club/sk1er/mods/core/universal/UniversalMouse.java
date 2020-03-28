package club.sk1er.mods.core.universal;
import net.minecraft.client.Minecraft;
//#if MC<=11202
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

//#endif
public class UniversalMouse {

    public static int getTrueX() {
        //#if MC<=11202
        return Mouse.getX();
        //#else
        //$$ return (int) Minecraft.getInstance().mouseHelper.getMouseX();
        //#endif

    }

    public static int getScaledX() {

        int trueX = getTrueX();
        //#if MC<=11202
        return trueX / new ScaledResolution(UniversalMinecraft.getMinecraft()).getScaledWidth();
        //#else
        //$$ return (int)(trueX * (double) UniversalMinecraft.getMinecraft().getMainWindow().getScaledWidth() / (double) UniversalMinecraft.getMinecraft().getMainWindow().getWidth());
        //#endif
    }

    public static int getTrueY() {
        //#if MC<=11202
        return Mouse.getX();
        //#else
        //$$ return (int) Minecraft.getInstance().mouseHelper.getMouseX();
        //#endif

    }

    public static int getScaledY(){
        int trueY = getTrueY();
        //#if MC<=11202
        return trueY / new ScaledResolution(UniversalMinecraft.getMinecraft()).getScaledHeight();
        //#else
        //$$ return (int)(trueY * (double) UniversalMinecraft.getMinecraft().getMainWindow().getScaledHeight() / (double) UniversalMinecraft.getMinecraft().getMainWindow().getHeight());
        //#endif
    }
}
