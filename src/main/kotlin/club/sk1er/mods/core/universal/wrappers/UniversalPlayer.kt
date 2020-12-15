package club.sk1er.mods.core.universal.wrappers

import club.sk1er.mods.core.universal.UniversalMinecraft
import club.sk1er.mods.core.universal.utils.MCEntityPlayerSP
import club.sk1er.mods.core.universal.wrappers.message.UniversalTextComponent
import java.util.*

object UniversalPlayer {
    @JvmStatic
    fun getPlayer(): MCEntityPlayerSP? {
        //#if MC>=11202
        //$$ return UniversalMinecraft.getMinecraft().player
        //#else
        return UniversalMinecraft.getMinecraft().thePlayer
        //#endif
    }

    @JvmStatic
    fun hasPlayer() = getPlayer() != null

    @JvmStatic
    fun sendClientSideMessage(message: UniversalTextComponent) {
        //#if FABRIC
        //$$ getPlayer()!!.sendMessage(message, false)
        //#elseif MC>=11602
        //$$ getPlayer()!!.sendMessage(message, null)
        //#elseif MC>=11202
        //$$ getPlayer()!!.sendChatMessage(message.formattedText)
        //#else
        getPlayer()!!.addChatMessage(message)
        //#endif
    }

    @JvmStatic
    fun getUUID(): UUID {
        return UniversalMinecraft.getMinecraft().session.profile.id
    }

    @JvmStatic
    fun getPosX(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.pos?.x
        //#else
        return getPlayer()?.posX
        //#endif
            ?: throw NullPointerException("UniversalPlayer.getPosX() called with no existing Player")
    }

    @JvmStatic
    fun getPosY(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.pos?.y
        //#else
        return getPlayer()?.posY
        //#endif
            ?: throw NullPointerException("UniversalPlayer.getPosY() called with no existing Player")
    }

    @JvmStatic
    fun getPosZ(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.pos?.z
        //#else
        return getPlayer()?.posZ
        //#endif
            ?: throw NullPointerException("UniversalPlayer.getPosZ() called with no existing Player")
    }

    @JvmStatic
    fun getPrevPosX(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.prevX
        //#else
        return getPlayer()?.prevPosX
        //#endif
            ?: throw NullPointerException("UniversalPlayer.getPrevPosX() called with no existing Player")
    }

    @JvmStatic
    fun getPrevPosY(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.prevY
        //#else
        return getPlayer()?.prevPosY
        //#endif
            ?: throw NullPointerException("UniversalPlayer.getPrevPosY() called with no existing Player")
    }

    @JvmStatic
    fun getPrevPosZ(): Double {
        //#if FABRIC
        //$$ return getPlayer()?.prevZ
        //#else
        return getPlayer()?.prevPosZ
        //#endif
            ?: throw NullPointerException("UniversalPlayer.getPrevPosZ() called with no existing Player")
    }
}
