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
            //#if MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.width
            //#else
            return UMinecraft.getMinecraft().displayWidth
            //#endif
        }

    @JvmStatic
    val windowHeight: Int
        get() {
            //#if MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.height
            //#else
            return UMinecraft.getMinecraft().displayHeight
            //#endif
        }

    @JvmStatic
    val viewportWidth: Int
        get() {
            //#if MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.framebufferWidth
            //#else
            return UMinecraft.getMinecraft().displayWidth
            //#endif
        }

    @JvmStatic
    val viewportHeight: Int
        get() {
            //#if MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.framebufferHeight
            //#else
            return UMinecraft.getMinecraft().displayHeight
            //#endif
        }

    //#if MC<=11202
    private fun get(): ScaledResolution {
        if (cachedHeight != viewportHeight || cachedWidth != viewportWidth || scaledResolution == null)
            scaledResolution = ScaledResolution(UMinecraft.getMinecraft())
        return scaledResolution!!
    }
    //#endif

    @JvmStatic
    val scaledWidth: Int
        get() {
            //#if MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.scaledWidth
            //#else
            return get().scaledWidth
            //#endif
        }

    @JvmStatic
    val scaledHeight: Int
        get() {
            //#if MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.scaledHeight
            //#else
            return get().scaledHeight
            //#endif
        }

    @JvmStatic
    val scaleFactor: Double
        get() {
            //#if MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.guiScaleFactor
            //#else
            return get().scaleFactor.toDouble()
            //#endif
        }
}
