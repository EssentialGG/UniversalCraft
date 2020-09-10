package club.sk1er.mods.core.universal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

//#if MC<11602
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
//#else
//$$ import net.minecraft.client.entity.player.ClientPlayerEntity;
//$$ import net.minecraft.client.network.play.ClientPlayNetHandler;
//$$ import net.minecraft.client.world.ClientWorld;
//#endif

//#if MC>11202
//$$ import net.minecraft.client.util.NativeUtil;
//#endif

public class UniversalMinecraft {
    public static boolean isRunningOnMac =
            //#if MC<=10809
            Minecraft.isRunningOnMac;
            //#else
            //$$ Minecraft.IS_RUNNING_ON_MAC;
            //#endif

    public static Minecraft getMinecraft() {
        //#if MC<11602
        return Minecraft.getMinecraft();
        //#else
        //$$ return Minecraft.getInstance();
        //#endif
    }

    //#if MC<11602
    public static WorldClient getWorld() {
    //#else
    //$$ public static ClientWorld getWorld() {
    //#endif
        //#if MC<=10809
        return getMinecraft().theWorld;
        //#else
        //$$ return getMinecraft().world;
        //#endif
    }

    //#if MC<11602
    public static NetHandlerPlayClient getNetHandler() {
    //#else
    //$$ public static ClientPlayNetHandler getNetHandler() {
    //#endif
        //#if MC<=10809
        return getMinecraft().getNetHandler();
        //#else
        //$$ return getMinecraft().getConnection();
        //#endif
    }

    //#if MC<11602
    public static EntityPlayerSP getPlayer() {
    //#else
    //$$ public static ClientPlayerEntity getPlayer() {
    //#endif
        //#if MC<=10809
        return getMinecraft().thePlayer;
        //#else
        //$$ return getMinecraft().player;
        //#endif
    }

    public static FontRenderer getFontRenderer() {
        //#if MC<=10809
        return getMinecraft().fontRendererObj;
        //#else
        //$$ return getMinecraft().fontRenderer;
        //#endif
    }

    public static long getTime() {
        //#if MC<=11202
        return Minecraft.getSystemTime();
        //#else
        //$$ return (long) NativeUtil.getTime();
        //#endif
    }
}
