package gg.essential.universal

import gg.essential.universal.wrappers.message.UTextComponent
import net.minecraft.network.play.server.S02PacketChat

//#if MC>=11602
//$$ import gg.essential.universal.wrappers.UPlayer
//#endif

//#if MC>=11202
//$$ import net.minecraft.util.text.ChatType
//$$
private object ChatType {
    const val CHAT: Byte = 0
    const val GAME_INFO: Byte = 2
}
//#endif

object UPacket {
    @JvmStatic
    fun sendChatMessage(message: UTextComponent) {
        UMinecraft.getNetHandler()!!.handleChat(S02PacketChat(
            message,
            ChatType.CHAT,
            //#if MC>=11600
            //$$ UPlayer.getUUID(),
            //#endif
        ))
    }
    
    @JvmStatic
    fun sendActionBarMessage(message: UTextComponent) {
        UMinecraft.getNetHandler()!!.handleChat(S02PacketChat(
            message,
            ChatType.GAME_INFO,
            //#if MC>=11600
            //$$ UPlayer.getUUID(),
            //#endif
        ))
    }
}
