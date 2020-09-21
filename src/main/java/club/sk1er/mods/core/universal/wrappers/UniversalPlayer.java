package club.sk1er.mods.core.universal.wrappers;

import club.sk1er.mods.core.universal.UniversalMinecraft;
import club.sk1er.mods.core.universal.wrappers.message.UniversalTextComponent;

import java.util.UUID;

//#if MC<=11202
import net.minecraft.client.entity.EntityPlayerSP;
//#else
//$$ import net.minecraft.client.entity.player.ClientPlayerEntity;
//#endif

public class UniversalPlayer {
    public static boolean hasPlayer() {
        return getPlayer() != null;
    }

    //#if MC<=11202
    public static EntityPlayerSP getPlayer() {
    //#else
    //$$ public static ClientPlayerEntity getPlayer() {
    //#endif
        //#if MC<=10809
        return UniversalMinecraft.getMinecraft().thePlayer;
        //#else
        //$$ return UniversalMinecraft.getMinecraft().player;
        //#endif
    }

    public static void sendClientSideMessage(UniversalTextComponent chatMessage) {
        //#if MC<11602
        getPlayer().addChatMessage(chatMessage);
        //#else
        //$$ getPlayer().sendMessage(chatMessage, null);
        //#endif
    }

    public static UUID getUUID() {
        return UniversalMinecraft.getMinecraft().getSession().getProfile().getId();
    }
}
