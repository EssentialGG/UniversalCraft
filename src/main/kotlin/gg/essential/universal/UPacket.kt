package gg.essential.universal

import net.minecraft.network.play.server.S02PacketChat
import net.minecraft.util.IChatComponent

//#if MC>=11602
//$$ import gg.essential.universal.wrappers.UPlayer
//#endif

//#if MC>=11901
//#else
//#if MC>=11900
//$$ import net.minecraft.network.message.MessageType as MCMessageType
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
//#endif

object UPacket {
    //#if MC<12105
    @Suppress("DEPRECATION")
    @JvmStatic
    fun sendChatMessage(message: gg.essential.universal.wrappers.message.UTextComponent) = sendChatMessage(message as IChatComponent)
    //#endif
    @JvmStatic
    fun sendChatMessage(message: IChatComponent) {
        UMinecraft.getNetHandler()!!.handleChat(S02PacketChat(
            message,
            //#if MC>=11901
            //$$ false,
            //#else
            ChatType.CHAT,
            //#endif
            //#if MC>=11600 && MC<11900
            //$$ UPlayer.getUUID(),
            //#endif
        ))
    }

    //#if MC<12105
    @Suppress("DEPRECATION")
    @JvmStatic
    fun sendActionBarMessage(message: gg.essential.universal.wrappers.message.UTextComponent) = sendActionBarMessage(message as IChatComponent)
    //#endif
    @JvmStatic
    fun sendActionBarMessage(message: IChatComponent) {
        UMinecraft.getNetHandler()!!.handleChat(S02PacketChat(
            message,
            //#if MC>=11901
            //$$ true,
            //#else
            ChatType.GAME_INFO,
            //#endif
            //#if MC>=11600 && MC<11900
            //$$ UPlayer.getUUID(),
            //#endif
        ))
    }
}
