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
        }

    @JvmField
    val isRunningOnMac: Boolean =
        //#if MC>=11202
        //$$ MCMinecraft.IS_RUNNING_ON_MAC
        //#else
        MCMinecraft.isRunningOnMac
        //#endif

    @JvmStatic
    fun getMinecraft(): MCMinecraft {
        //#if MC>=11502
        //$$ return MCMinecraft.getInstance()
        //#else
        return MCMinecraft.getMinecraft()
        //#endif
    }

    @JvmStatic
    fun getWorld(): MCWorld? {
        //#if MC>=11202
        //$$ return getMinecraft().world
        //#else
        return getMinecraft().theWorld
        //#endif
    }

    @JvmStatic
    fun getNetHandler(): MCClientNetworkHandler? {
        //#if MC>=11202
        //$$ return getMinecraft().getConnection()
        //#else
        return getMinecraft().netHandler
        //#endif
    }

    @JvmStatic
    fun getPlayer(): MCEntityPlayerSP? {
        //#if MC>=11202
        //$$ return getMinecraft().player
        //#else
        return getMinecraft().thePlayer
        //#endif
    }

    @JvmStatic
    fun getFontRenderer(): MCFontRenderer {
        //#if MC>=11202
        //$$ return getMinecraft().fontRenderer
        //#else
        return getMinecraft().fontRendererObj
        //#endif
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
