package club.sk1er.mods.core.universal

import club.sk1er.mods.core.universal.utils.MCSChatPacket
import club.sk1er.mods.core.universal.wrappers.message.UniversalTextComponent
import club.sk1er.mods.core.universal.wrappers.UniversalPlayer

//#if FORGE && MC>=11202
//$$ import net.minecraft.util.text.ChatType
//#endif

object UniversalPacket {
    @JvmStatic
    fun sendChatMessage(message: UniversalTextComponent) {
        //#if FABRIC
        //$$ UniversalPlayer.getPlayer()!!.sendChatMessage(message.text)
        //#elseif MC>=11602
        //$$ UniversalMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.CHAT, UniversalPlayer.getUUID()))
        //#elseif MC>=11502
        //$$ UniversalMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.CHAT))
        //#elseif MC>=11202
        //$$ UniversalMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.CHAT))
        //#else
        UniversalMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, 0))
        //#endif
    }
    
    @JvmStatic
    fun sendActionBarMessage(message: UniversalTextComponent) {
        //#if FABRIC
        //$$ TODO("Figure out how to do this with Fabric")
        //#elseif MC>=11602
        //$$ UniversalMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.GAME_INFO, UniversalPlayer.getUUID()))
        //#elseif MC>=11502
        //$$ UniversalMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.GAME_INFO))
        //#elseif MC>=11202
        //$$ UniversalMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.GAME_INFO))
        //#else
        UniversalMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, 2))
        //#endif
    }
}
