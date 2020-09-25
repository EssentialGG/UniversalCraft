package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.Mouse;
//#elseif MC<=11202
import org.lwjgl.input.Mouse;
//#endif

public class UniversalMouse {
    public static int getTrueX() {
        //#if FABRIC
        //$$ return (int) UniversalMinecraft.getMinecraft().mouse.getX();
        //#elseif MC>=11502
        //$$ return (int) UniversalMinecraft.getMinecraft().mouseHelper.getMouseX();
        //#else
        return Mouse.getX();
        //#endif
    }

    public static int getScaledX() {
        int trueX = getTrueX();
        UniversalResolutionUtil instance = UniversalResolutionUtil.getInstance();
        return trueX * instance.getScaledWidth() / Math.max(1, instance.getWindowWidth());
    }

    public static int getTrueY() {
        //#if FABRIC
        //$$ return (int) UniversalMinecraft.getMinecraft().mouse.getX();
        //#elseif MC>=11502
        //$$ return UniversalResolutionUtil.getInstance().getWindowHeight() - (int) UniversalMinecraft.getMinecraft().mouseHelper.getMouseY();
        //#else
        return Mouse.getY();
        //#endif
    }

    public static int getScaledY() {
        UniversalResolutionUtil instance = UniversalResolutionUtil.getInstance();
        return getTrueY() * instance.getScaledHeight() / Math.max(1, instance.getWindowHeight());
    }
}
