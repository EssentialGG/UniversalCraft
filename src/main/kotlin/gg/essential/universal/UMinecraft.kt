package gg.essential.universal

import gg.essential.universal.utils.*

//#if MC>=11502
//$$ import net.minecraft.client.util.NativeUtil
//#endif

object UMinecraft {
    @JvmStatic
    var guiScale: Int
        get() = getSettings().guiScale
        set(value) {
            getSettings().guiScale = value
            //#if MC>=11502
            //$$ val mc = getMinecraft()
            //$$ val window = mc.mainWindow
            //$$ val scaleFactor = window.calcGuiScale(value, mc.forceUnicodeFont)
            //$$ window.setGuiScale(scaleFactor.toDouble())
            //#endif
        }

    @JvmField
    val isRunningOnMac: Boolean =
        MCMinecraft.isRunningOnMac

    @JvmStatic
    fun getMinecraft(): MCMinecraft {
        return MCMinecraft.getMinecraft()
    }

    @JvmStatic
    fun getWorld(): MCWorld? {
        return getMinecraft().theWorld
    }

    @JvmStatic
    fun getNetHandler(): MCClientNetworkHandler? {
        return getMinecraft().netHandler
    }

    @JvmStatic
    fun getPlayer(): MCEntityPlayerSP? {
        return getMinecraft().thePlayer
    }

    @JvmStatic
    fun getFontRenderer(): MCFontRenderer {
        return getMinecraft().fontRendererObj
    }

    @JvmStatic
    fun getTime(): Long {
        //#if MC>=11502
        //$$ return (NativeUtil.getTime() * 1000).toLong()
        //#else
        return MCMinecraft.getSystemTime()
        //#endif
    }

    @JvmStatic
    fun getChatGUI(): MCChatScreen? = getMinecraft().ingameGUI?.chatGUI

    @JvmStatic
    fun getSettings(): MCSettings = getMinecraft().gameSettings
}
