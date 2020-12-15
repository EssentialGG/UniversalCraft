package club.sk1er.mods.core.universal

//#if MC<=11202
import net.minecraft.client.gui.ScaledResolution
//#endif

object UniversalResolutionUtil {
    //#if MC<=11202
    private var scaledResolution: ScaledResolution? = null
    private var cachedWidth: Int? = null
    private var cachedHeight: Int? = null
    //#endif

    @JvmStatic
    val windowWidth: Int
        get() {
            //#if FABRIC
            //$$ return UniversalMinecraft.getMinecraft().window.width
            //#elseif MC>=11502
            //$$ return UniversalMinecraft.getMinecraft().mainWindow.width
            //#else
            return UniversalMinecraft.getMinecraft().displayWidth
            //#endif
        }

    @JvmStatic
    val windowHeight: Int
        get() {
            //#if FABRIC
            //$$ return UniversalMinecraft.getMinecraft().window.height
            //#elseif MC>=11502
            //$$ return UniversalMinecraft.getMinecraft().mainWindow.height
            //#else
            return UniversalMinecraft.getMinecraft().displayHeight
            //#endif
        }

    //#if MC<=11202
    private fun get(): ScaledResolution {
        if (cachedHeight != windowHeight || cachedWidth != windowWidth || scaledResolution == null)
            scaledResolution = ScaledResolution(UniversalMinecraft.getMinecraft())
        return scaledResolution!!
    }
    //#endif

    @JvmStatic
    val scaledWidth: Int
        get() {
            //#if FABRIC
            //$$ return UniversalMinecraft.getMinecraft().window.scaledWidth
            //#elseif MC>=11502
            //$$ return UniversalMinecraft.getMinecraft().mainWindow.scaledWidth
            //#else
            return get().scaledWidth
            //#endif
        }

    @JvmStatic
    val scaledHeight: Int
        get() {
            //#if FABRIC
            //$$ return UniversalMinecraft.getMinecraft().window.scaledHeight
            //#elseif MC>=11502
            //$$ return UniversalMinecraft.getMinecraft().mainWindow.scaledHeight
            //#else
            return get().scaledHeight
            //#endif
        }

    @JvmStatic
    val scaleFactor: Double
        get() {
            //#if FABRIC
            //$$ return UniversalMinecraft.getMinecraft().window.scaleFactor
            //#elseif MC>=11502
            //$$ return UniversalMinecraft.getMinecraft().mainWindow.guiScaleFactor
            //#else
            return get().scaleFactor.toDouble()
            //#endif
        }
}
