package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.Mouse;
//#else
//$$ import net.minecraft.client.Minecraft;

//#if MC<=11202
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
//#endif
//#endif


public class UniversalMouse {
    public static int getTrueX() {
        //#if FABRIC
        //$$ return (int) UniversalMinecraft.getMinecraft().mouse.getX();
        //#else
        //#if MC<=11202
        return Mouse.getX();
        //#else
        //$$ return (int) Minecraft.getInstance().mouseHelper.getMouseX();
        //#endif
        //#endif
    }

    public static int getScaledX() {
        int trueX = getTrueX();
        UniversalResolutionUtil instance = UniversalResolutionUtil.getInstance();
        return trueX * instance.getScaledWidth() / Math.max(1,instance.getWindowWidth());
    }

    public static int getTrueY() {
        //#if FABRIC
        //$$ return (int) UniversalMinecraft.getMinecraft().mouse.getX();
        //#else
        //#if MC<=11202
        return Mouse.getY();
        //#else
        //$$ return UniversalResolutionUtil.getInstance().getWindowHeight() - (int) Minecraft.getInstance().mouseHelper.getMouseY();
        //#endif
        //#endif
    }

    public static int getScaledY() {
        UniversalResolutionUtil instance = UniversalResolutionUtil.getInstance();
        return getTrueY() * instance.getScaledHeight() / Math.max(1,instance.getWindowHeight());
    }
}
