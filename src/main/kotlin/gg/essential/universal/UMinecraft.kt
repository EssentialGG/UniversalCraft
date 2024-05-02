package gg.essential.universal

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.GuiNewChat
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.client.network.NetHandlerPlayClient
import net.minecraft.client.settings.GameSettings

//#if MC>=11502
//$$ import net.minecraft.client.util.NativeUtil
//#endif

object UMinecraft {
    //#if MC>=11900
    //$$ private var guiScaleValue: Int
    //$$     get() = getSettings().guiScale.value
    //$$     set(value) { getSettings().guiScale.value = value }
    //#else
    private var guiScaleValue: Int
        get() = getSettings().guiScale
        set(value) { getSettings().guiScale = value }
    //#endif

    @JvmStatic
    var guiScale: Int
        get() = guiScaleValue
        set(value) {
            guiScaleValue = value
            //#if MC>=11502 && MC<12005
            //$$ val mc = getMinecraft()
            //$$ val window = mc.mainWindow
            //$$ val scaleFactor = window.calcGuiScale(value, mc.forceUnicodeFont)
            //$$ window.setGuiScale(scaleFactor.toDouble())
            //#endif
        }

    @JvmField
    val isRunningOnMac: Boolean =
        Minecraft.isRunningOnMac

    @JvmStatic
    fun getMinecraft(): Minecraft {
        return Minecraft.getMinecraft()
    }

    @JvmStatic
    fun getWorld(): WorldClient? {
        return getMinecraft().theWorld
    }

    @JvmStatic
    fun getNetHandler(): NetHandlerPlayClient? {
        return getMinecraft().netHandler
    }

    @JvmStatic
    fun getPlayer(): EntityPlayerSP? {
        return getMinecraft().thePlayer
    }

    @JvmStatic
    fun getFontRenderer(): FontRenderer {
        return getMinecraft().fontRendererObj
    }

    @JvmStatic
    fun getTime(): Long {
        //#if MC>=11502
        //$$ return (NativeUtil.getTime() * 1000).toLong()
        //#else
        return Minecraft.getSystemTime()
        //#endif
    }

    @JvmStatic
    fun getChatGUI(): GuiNewChat? = getMinecraft().ingameGUI?.chatGUI

    @JvmStatic
    fun getSettings(): GameSettings = getMinecraft().gameSettings
}
