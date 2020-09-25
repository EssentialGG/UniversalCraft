package club.sk1er.mods.core.universal.wrappers;

import club.sk1er.mods.core.universal.UniversalMinecraft;
import club.sk1er.mods.core.universal.wrappers.message.UniversalTextComponent;

import java.util.UUID;

//#if MC>=11502
//$$ import net.minecraft.client.entity.player.ClientPlayerEntity;
//#else
import net.minecraft.client.entity.EntityPlayerSP;
//#endif

public class UniversalPlayer {
    public static boolean hasPlayer() {
        return getPlayer() != null;
    }

    //#if MC>=11502
    //$$ public static ClientPlayerEntity getPlayer() {
    //#else
    public static EntityPlayerSP getPlayer() {
    //#endif
        //#if MC>=11202
        //$$ return UniversalMinecraft.getMinecraft().player;
        //#else
        return UniversalMinecraft.getMinecraft().thePlayer;
        //#endif
    }

    public static void sendClientSideMessage(UniversalTextComponent chatMessage) {
        //#if MC>=11602
        //$$ getPlayer().sendMessage(chatMessage, null);
        //#else
        getPlayer().addChatMessage(chatMessage);
        //#endif
    }

    public static UUID getUUID() {
        return UniversalMinecraft.getMinecraft().getSession().getProfile().getId();
    }

    public static double getPosX() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPosX() called with no existing Player");

        //#if MC>=11502
        //$$ return getPlayer().getPosX();
        //#else
        return getPlayer().posX;
        //#endif
    }

    public static double getPosY() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPosY() called with no existing Player");

        //#if MC>=11502
        //$$ return getPlayer().getPosY();
        //#else
        return getPlayer().posY;
        //#endif
    }

    public static double getPosZ() {
        if (!hasPlayer())
            throw new NullPointerException("UniversalPlayer.getPosZ() called with no existing Player");

        //#if MC>=11502
        //$$ return getPlayer().getPosZ();
        //#else
        return getPlayer().posZ;
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
