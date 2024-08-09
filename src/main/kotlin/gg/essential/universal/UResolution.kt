package gg.essential.universal

//#if MC<=11202
import net.minecraft.client.gui.ScaledResolution
//#endif

object UResolution {
    //#if MC<=11202
    private var scaledResolution: ScaledResolution? = null
    private data class ScaledResolutionInputs(val width: Int, val height: Int, val scale: Int, val unicode: Boolean)
    private var cachedScaledResolutionInputs: ScaledResolutionInputs? = null
    //#endif

    //#if STANDALONE
    //$$ @JvmStatic
    //$$ var windowWidth: Int = 1
    //$$     internal set
    //$$ @JvmStatic
    //$$ var windowHeight: Int = 1
    //$$     internal set
    //$$ @JvmStatic
    //$$ var viewportWidth: Int = 1
    //$$     internal set
    //$$ @JvmStatic
    //$$ var viewportHeight: Int = 1
    //$$     internal set
    //#else
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
    //#endif

    //#if MC<=11202
    private fun get(): ScaledResolution {
        val mc = UMinecraft.getMinecraft()
        val inputs = ScaledResolutionInputs(viewportWidth, viewportHeight, mc.gameSettings.guiScale, mc.isUnicode)
        if (cachedScaledResolutionInputs != inputs) {
            cachedScaledResolutionInputs = inputs
            scaledResolution = ScaledResolution(mc)
        }
        return scaledResolution!!
    }
    //#endif

    @JvmStatic
    val scaledWidth: Int
        get() {
            //#if STANDALONE
            //$$ return (viewportWidth / scaleFactor).toInt()
            //#elseif MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.scaledWidth
            //#else
            return get().scaledWidth
            //#endif
        }

    @JvmStatic
    val scaledHeight: Int
        get() {
            //#if STANDALONE
            //$$ return (viewportHeight / scaleFactor).toInt()
            //#elseif MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.scaledHeight
            //#else
            return get().scaledHeight
            //#endif
        }

    @JvmStatic
    val scaleFactor: Double
        get() {
            //#if STANDALONE
            //$$ return UMinecraft.guiScale.toDouble()
            //#elseif MC>=11502
            //$$ return UMinecraft.getMinecraft().mainWindow.guiScaleFactor
            //#else
            return get().scaleFactor.toDouble()
            //#endif
        }
}
