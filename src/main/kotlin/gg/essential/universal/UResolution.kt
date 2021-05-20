package gg.essential.universal

//#if MC<=11202
import net.minecraft.client.gui.ScaledResolution
//#endif

object UResolution {
    //#if MC<=11202
    private var scaledResolution: ScaledResolution? = null
    private var cachedWidth: Int? = null
    private var cachedHeight: Int? = null
    //#endif

    @JvmStatic
    val windowWidth: Int
        get() {
            //#if FABRIC
            //$$ return UMinecraft.getMinecraft().window.width
            //#elseif MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.width
            //#else
            return UMinecraft.getMinecraft().displayWidth
            //#endif
        }

    @JvmStatic
    val windowHeight: Int
        get() {
            //#if FABRIC
            //$$ return UMinecraft.getMinecraft().window.height
            //#elseif MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.height
            //#else
            return UMinecraft.getMinecraft().displayHeight
            //#endif
        }

    //#if MC<=11202
    private fun get(): ScaledResolution {
        if (cachedHeight != windowHeight || cachedWidth != windowWidth || scaledResolution == null)
            //#if MC>=10809
            scaledResolution = ScaledResolution(UMinecraft.getMinecraft())
            //#else
            //$$ scaledResolution = ScaledResolution(UMinecraft.getMinecraft(), UMinecraft.getMinecraft().displayWidth, UMinecraft.getMinecraft().displayHeight)
            //#endif
        return scaledResolution!!
    }
    //#endif

    @JvmStatic
    val scaledWidth: Int
        get() {
            //#if FABRIC
            //$$ return UMinecraft.getMinecraft().window.scaledWidth
            //#elseif MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.scaledWidth
            //#else
            return get().scaledWidth
            //#endif
        }

    @JvmStatic
    val scaledHeight: Int
        get() {
            //#if FABRIC
            //$$ return UMinecraft.getMinecraft().window.scaledHeight
            //#elseif MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.scaledHeight
            //#else
            return get().scaledHeight
            //#endif
        }

    @JvmStatic
    val scaleFactor: Double
        get() {
            //#if FABRIC
            //$$ return UMinecraft.getMinecraft().window.scaleFactor
            //#elseif MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.guiScaleFactor
            //#else
            return get().scaleFactor.toDouble()
            //#endif
        }
}
