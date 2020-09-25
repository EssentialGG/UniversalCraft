package club.sk1er.mods.core.universal;

import club.sk1er.mods.core.universal.wrappers.message.UniversalTextComponent;

//#if MC>=11602
//$$ import club.sk1er.mods.core.universal.wrappers.UniversalPlayer;
//#endif

//#if MC>=11502
//$$ import net.minecraft.network.play.server.SChatPacket;
//$$ import net.minecraft.util.text.ChatType;
//#elseif MC>=11202
//$$ import net.minecraft.network.play.server.SPacketChat;
//$$ import net.minecraft.util.text.ChatType;
//#else
import net.minecraft.network.play.server.S02PacketChat;
//#endif

public class UniversalPacket {
    public static void sendChatMessage(UniversalTextComponent component) {
        //#if FABRIC
        //$$ UniversalPlayer.getPlayer().sendChatMessage(component.getText());
        //#elseif MC>=11602
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SChatPacket(component, ChatType.CHAT, UniversalPlayer.getUUID()));
        //#elseif MC>=11502
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SChatPacket(component, ChatType.CHAT));
        //#elseif MC>=11202
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SPacketChat(component, ChatType.CHAT));
        //#else
        UniversalMinecraft.getNetHandler().handleChat(new S02PacketChat(component, (byte) 0));
        //#endif
    }

    public static void sendActionBarMessage(UniversalTextComponent component) {
        //#if FABRIC
        //$$ throw new UnsupportedOperationException("TODO");
        //#elseif MC>=11602
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SChatPacket(component, ChatType.GAME_INFO, UniversalPlayer.getUUID()));
        //#elseif MC>=11502
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SChatPacket(component, ChatType.GAME_INFO));
        //#elseif MC>=11202
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SPacketChat(component, ChatType.GAME_INFO));
        //#else
        UniversalMinecraft.getNetHandler().handleChat(new S02PacketChat(component, (byte) 2));
        //#endif
    }
}
