package club.sk1er.mods.core.universal

import club.sk1er.mods.core.universal.utils.*

//#if FABRIC
//$$ import org.lwjgl.glfw.GLFW
//#endif

//#if FORGE && MC>=11502
//$$ import net.minecraft.client.util.NativeUtil
//#endif

object UMinecraft {
    @JvmStatic
    var guiScale: Int
        get() = getSettings().guiScale
        set(value) {
            getSettings().guiScale = value
        }

    //#if FABRIC
    //$$ @JvmField
    //$$ val isRunningOnMac: Boolean = MCMinecraft.IS_SYSTEM_MAC
    //$$
    //$$ @JvmStatic
    //$$ fun getMinecraft(): MCMinecraft = MCMinecraft.getInstance()
    //$$
    //$$ @JvmStatic
    //$$ fun getWorld(): MCWorld? = getMinecraft().world
    //$$
    //$$ @JvmStatic
    //$$ fun getNetHandler(): MCClientNetworkHandler? = getMinecraft().getNetworkHandler()
    //$$
    //$$ @JvmStatic
    //$$ fun getPlayer(): MCEntityPlayerSP? = getMinecraft().player
    //$$
    //$$ @JvmStatic
    //$$ fun getFontRenderer(): MCFontRenderer = getMinecraft().textRenderer
    //$$
    //$$ @JvmStatic
    //$$ fun getTime() = GLFW.glfwGetTime().toLong()
    //$$
    //$$ @JvmStatic
    //$$ fun getSettings(): MCSettings = getMinecraft().options
    //#else
    @JvmField
    val isRunningOnMac =
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
        //$$ return NativeUtil.getTime() as Long
        //#else
        return MCMinecraft.getSystemTime()
        //#endif
    }

    @JvmStatic
    fun getChatGUI(): MCChatScreen? = getMinecraft().ingameGUI?.chatGUI

    @JvmStatic
    fun getSettings(): MCSettings = getMinecraft().gameSettings
    //#endif
}
