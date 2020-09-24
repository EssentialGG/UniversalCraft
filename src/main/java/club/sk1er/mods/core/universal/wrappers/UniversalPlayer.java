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

    public static double getPosX() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPosX() called with no existing Player");

        //#if MC<11502
        return getPlayer().posX;
        //#else
        //$$ return getPlayer().getPosX();
        //#endif
    }

    public static double getPosY() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPosY() called with no existing Player");

        //#if MC<11502
        return getPlayer().posY;
        //#else
        //$$ return getPlayer().getPosY();
        //#endif
    }

    public static double getPosZ() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPosZ() called with no existing Player");

        //#if MC<11502
        return getPlayer().posZ;
        //#else
        //$$ return getPlayer().getPosZ();
        //#endif
    }

    public static double getPrevPosX() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPrevPosX() called with no existing Player");

        return getPlayer().prevPosX;
    }

    public static double getPrevPosY() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPrevPosY() called with no existing Player");

        return getPlayer().prevPosY;
    }

    public static double getPrevPosZ() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPrevPosZ() called with no existing Player");

        return getPlayer().prevPosZ;
    }
}
