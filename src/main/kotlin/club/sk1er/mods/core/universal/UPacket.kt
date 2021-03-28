package club.sk1er.mods.core.universal

import club.sk1er.mods.core.universal.utils.MCSChatPacket
import club.sk1er.mods.core.universal.wrappers.message.UTextComponent

//#if FABRIC
//$$ import net.minecraft.text.LiteralText
//#endif

//#if MC>=11602
//$$ import club.sk1er.mods.core.universal.wrappers.UPlayer
//#endif

//#if FORGE && MC>=11202
//$$ import net.minecraft.util.text.ChatType
//#endif

object UPacket {
    @JvmStatic
    fun sendChatMessage(message: UTextComponent) {
        //#if FABRIC
        //$$ UPlayer.getPlayer()!!.sendChatMessage(message.text)
        //#elseif MC>=11602
        //$$ UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.CHAT, UPlayer.getUUID()))
        //#elseif MC>=11502
        //$$ UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.CHAT))
        //#elseif MC>=11202
        //$$ UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.CHAT))
        //#else
        UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, 0))
        //#endif
    }
    
    @JvmStatic
    fun sendActionBarMessage(message: UTextComponent) {
        //#if FABRIC
        //$$ UPlayer.getPlayer()!!.sendMessage(LiteralText(message.text), true)
        //#elseif MC>=11602
        //$$ UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.GAME_INFO, UPlayer.getUUID()))
        //#elseif MC>=11502
        //$$ UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.GAME_INFO))
        //#elseif MC>=11202
        //$$ UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, ChatType.GAME_INFO))
        //#else
        UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, 2))
        //#endif
    }
}
