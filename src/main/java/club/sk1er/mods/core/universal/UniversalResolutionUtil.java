package club.sk1er.mods.core.universal;

//#if FORGE && MC<11500
import net.minecraft.client.gui.ScaledResolution;
//#endif

public class UniversalResolutionUtil {
    private static final UniversalResolutionUtil INSTANCE = new UniversalResolutionUtil();

    //#if FORGE && MC<11500
    private ScaledResolution scaledResolution;
    private int cachedWidth = 0;
    private int cachedHeight = 0;
    //#endif

    private UniversalResolutionUtil() {
    }

    public static UniversalResolutionUtil getInstance() {
        return INSTANCE;
    }

    public int getWindowWidth() {
        //#if FABRIC
        //$$ return UniversalMinecraft.getMinecraft().getWindow().getWidth();
        //#else
        //#if MC<11500
        return UniversalMinecraft.getMinecraft().displayWidth;
        //#else
        //$$ return UniversalMinecraft.getMinecraft().getMainWindow().getWidth();
        //#endif
        //#endif
    }

    public int getWindowHeight() {
        //#if FABRIC
        //$$ return UniversalMinecraft.getMinecraft().getWindow().getHeight();
        //#else
        //#if MC<11500
        return UniversalMinecraft.getMinecraft().displayHeight;
        //#else
        //$$ return UniversalMinecraft.getMinecraft().getMainWindow().getHeight();
        //#endif
        //#endif
    }

    //#if MC<11500
    private ScaledResolution get() {
        if (cachedHeight != getWindowHeight() || cachedWidth != getWindowWidth() || scaledResolution == null) {
            scaledResolution = new ScaledResolution(UniversalMinecraft.getMinecraft());
        }
        return scaledResolution;
    }
    //#endif

    public int getScaledWidth() {
        //#if FABRIC
        //$$ return UniversalMinecraft.getMinecraft().getWindow().getScaledWidth();
        //#else
        //#if MC<11500
        return get().getScaledWidth();
        //#else
        //$$ return UniversalMinecraft.getMinecraft().getMainWindow().getScaledWidth();
        //#endif
        //#endif
    }

    public int getScaledHeight() {
        //#if FABRIC
        //$$ return UniversalMinecraft.getMinecraft().getWindow().getScaledHeight();
        //#else
        //#if MC<11500
        return get().getScaledHeight();
        //#else
        //$$ return UniversalMinecraft.getMinecraft().getMainWindow().getScaledHeight();
        //#endif
        //#endif
    }

    public int getScaleFactor() {
        //#if FABRIC
        //$$ return (int) UniversalMinecraft.getMinecraft().getWindow().getScaleFactor();
        //#else
        //#if MC<11500
        return get().getScaleFactor();
        //#else
        //$$ return (int)UniversalMinecraft.getMinecraft().getMainWindow().getGuiScaleFactor();
        //#endif
        //#endif
    }
}
