package club.sk1er.mods.core.universal.wrappers

import club.sk1er.mods.core.universal.UMinecraft
import club.sk1er.mods.core.universal.utils.MCEntityPlayerSP
import club.sk1er.mods.core.universal.wrappers.message.UTextComponent
import java.util.*

object UPlayer {
    @JvmStatic
    fun getPlayer(): MCEntityPlayerSP? {
        //#if MC>=11202
        //$$ return UMinecraft.getMinecraft().player
        //#else
        return UMinecraft.getMinecraft().thePlayer
        //#endif
    }

    @JvmStatic
    fun hasPlayer() = getPlayer() != null

    @JvmStatic
    fun sendClientSideMessage(message: UTextComponent) {
        //#if FABRIC
        //$$ getPlayer()!!.sendMessage(message, false)
        //#elseif MC>=11602
        //$$ getPlayer()!!.sendMessage(message, null)
        //#elseif MC>=11202
        //$$ getPlayer()!!.sendMessage(message)
        //#else
        getPlayer()!!.addChatMessage(message)
        //#endif
    }

    @JvmStatic
    fun getUUID(): UUID {
        return UMinecraft.getMinecraft().session.profile.id
    }

    @JvmStatic
    fun getPosX(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.pos?.x
        //#else
        return getPlayer()?.posX
        //#endif
            ?: throw NullPointerException("UPlayer.getPosX() called with no existing Player")
    }

    @JvmStatic
    fun getPosY(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.pos?.y
        //#else
        return getPlayer()?.posY
        //#endif
            ?: throw NullPointerException("UPlayer.getPosY() called with no existing Player")
    }

    @JvmStatic
    fun getPosZ(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.pos?.z
        //#else
        return getPlayer()?.posZ
        //#endif
            ?: throw NullPointerException("UPlayer.getPosZ() called with no existing Player")
    }

    @JvmStatic
    fun getPrevPosX(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.prevX
        //#else
        return getPlayer()?.prevPosX
        //#endif
            ?: throw NullPointerException("UPlayer.getPrevPosX() called with no existing Player")
    }

    @JvmStatic
    fun getPrevPosY(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.prevY
        //#else
        return getPlayer()?.prevPosY
        //#endif
            ?: throw NullPointerException("UPlayer.getPrevPosY() called with no existing Player")
    }

    @JvmStatic
    fun getPrevPosZ(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.prevZ
        //#else
        return getPlayer()?.prevPosZ
        //#endif
            ?: throw NullPointerException("UPlayer.getPrevPosZ() called with no existing Player")
    }
}
