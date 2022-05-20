package gg.essential.universal

import gg.essential.universal.wrappers.message.UTextComponent
import net.minecraft.network.play.server.S02PacketChat

//#if MC>=11602
//$$ import gg.essential.universal.wrappers.UPlayer
//#endif

//#if MC>=11900
//$$ import net.minecraft.network.MessageType as MCMessageType
//$$ import net.minecraft.util.registry.Registry
//$$ import net.minecraft.util.registry.RegistryKey
//$$
//$$ private object MessageType {
//$$     private fun get(key: RegistryKey<MCMessageType>): Int {
//$$         val registry = UMinecraft.getNetHandler()!!.registryManager.get(Registry.MESSAGE_TYPE_KEY)
//$$         return registry.getRawId(registry.get(key))
//$$     }
//$$     val CHAT: Int
//$$         get() = get(MCMessageType.CHAT)
//$$     val GAME_INFO: Int
//$$         get() = get(MCMessageType.GAME_INFO)
//$$ }
//#elseif MC>=11202
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
            //#if MC>=11600 && MC<11900
            //$$ UPlayer.getUUID(),
            //#endif
        ))
    }
    
    @JvmStatic
    fun sendActionBarMessage(message: UTextComponent) {
        UMinecraft.getNetHandler()!!.handleChat(S02PacketChat(
            message,
            ChatType.GAME_INFO,
            //#if MC>=11600 && MC<11900
            //$$ UPlayer.getUUID(),
            //#endif
        ))
    }
}
