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
        Minecraft.isRunningOnMac
    @JvmStatic
    val isOnMainThread: Boolean
        get() = getMinecraft().isCallingFromMinecraftThread

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

    /**
     * Based on Skytils
     * Licensed under AGPL-3.0
     * Modified
     */
    @JvmStatic
    fun enqueueOnMainThread(run: () -> Unit) {
        if (!isOnMainThread) {
            getMinecraft().addScheduledTask(run)
        } else run()
    }
}
