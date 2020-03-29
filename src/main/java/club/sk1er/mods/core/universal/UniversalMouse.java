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
        UniversalResolutionUtil instance = UniversalResolutionUtil.getInstance();
        return trueX * instance.getScaledWidth() / instance.getWindowWidth();
    }

    public static int getTrueY() {

        //#if MC<=11202
        return Mouse.getY();
        //#else
        //$$ return UniversalResolutionUtil.getInstance().getWindowHeight() - (int) Minecraft.getInstance().mouseHelper.getMouseY();
        //#endif

    }

    public static int getScaledY(){
        UniversalResolutionUtil instance = UniversalResolutionUtil.getInstance();
        return getTrueY() * instance.getScaledHeight() / instance.getWindowHeight();
    }
}
