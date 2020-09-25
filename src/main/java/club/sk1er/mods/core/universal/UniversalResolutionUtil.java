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
        //#elseif MC>=11502
        //$$ return UniversalMinecraft.getMinecraft().getMainWindow().getWidth();
        //#else
        return UniversalMinecraft.getMinecraft().displayWidth;
        //#endif
    }

    public int getWindowHeight() {
        //#if FABRIC
        //$$ return UniversalMinecraft.getMinecraft().getWindow().getHeight();
        //#elseif MC>=11502
        //$$ return UniversalMinecraft.getMinecraft().getMainWindow().getHeight();
        //#else
        return UniversalMinecraft.getMinecraft().displayHeight;
        //#endif
    }

    //#if MC<11502
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
        //#elseif MC>=11502
        //$$ return UniversalMinecraft.getMinecraft().getMainWindow().getScaledWidth();
        //#else
        return get().getScaledWidth();
        //#endif
    }

    public int getScaledHeight() {
        //#if FABRIC
        //$$ return UniversalMinecraft.getMinecraft().getWindow().getScaledHeight();
        //#elseif MC>=11502
        //$$ return UniversalMinecraft.getMinecraft().getMainWindow().getScaledHeight();
        //#else
        return get().getScaledHeight();
        //#endif
    }

    public int getScaleFactor() {
        //#if FABRIC
        //$$ return (int) UniversalMinecraft.getMinecraft().getWindow().getScaleFactor();
        //#elseif MC>=11502
        //$$ return (int)UniversalMinecraft.getMinecraft().getMainWindow().getGuiScaleFactor();
        //#else
        return get().getScaleFactor();
        //#endif
    }
}
