package club.sk1er.mods.core.universal;

import club.sk1er.mods.core.universal.wrappers.message.UniversalTextComponent;
import club.sk1er.mods.core.universal.wrappers.UniversalPlayer;

//#if MC<=10809
import net.minecraft.network.play.server.S02PacketChat;
//#else
//#if MC<=11202
//$$ import net.minecraft.network.play.server.SPacketChat;
//$$ import net.minecraft.util.text.ChatType;
//#else
//#if FORGE
//$$ import net.minecraft.network.play.server.SChatPacket;
//$$ import net.minecraft.util.text.ChatType;
//#endif
//#endif
//#endif

public class UniversalPacket {
    public static void sendChatMessage(UniversalTextComponent component) {
        //#if MC<=10809
        UniversalMinecraft.getNetHandler().handleChat(new S02PacketChat(component, (byte) 0));
        //#else
        //#if MC<=11202
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SPacketChat(component, ChatType.CHAT));
        //#else
        //#if MC<=11502
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SChatPacket(component, ChatType.CHAT));
        //#else
        //#if FORGE
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SChatPacket(component, ChatType.CHAT, UniversalPlayer.getUUID()));
        //#else
        //$$ UniversalPlayer.getPlayer().sendChatMessage(component.getText());
        //#endif
        //#endif
        //#endif
        //#endif
    }

    public static void sendActionBarMessage(UniversalTextComponent component) {
        //#if MC<=10809
        UniversalMinecraft.getNetHandler().handleChat(new S02PacketChat(component, (byte) 2));
        //#else
        //#if MC<=11202
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SPacketChat(component, ChatType.GAME_INFO));
        //#else
        //#if MC<=11502
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SChatPacket(component, ChatType.GAME_INFO));
        //#else
        //#if FORGE
        //$$ UniversalMinecraft.getNetHandler().handleChat(new SChatPacket(component, ChatType.GAME_INFO, UniversalPlayer.getUUID()));
        //#else
        //#endif
        //#endif
        //#endif
        //#endif
    }
}
