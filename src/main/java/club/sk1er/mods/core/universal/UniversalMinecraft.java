package club.sk1er.mods.core.universal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;

//#if MC>11500
//$$ import net.minecraft.client.util.NativeUtil;
//#endif
public class UniversalMinecraft {

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static WorldClient getWorld() {
        return getMinecraft().theWorld;
    }
    
    public static NetHandlerPlayClient getNetHandler() {
        //#if MC<=10809
        return getMinecraft().getNetHandler();
        //#else
        //$$ return getMinecraft().getConnection();
        //#endif
    }

    public static EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }

    public static FontRenderer getFontRenderer() {
        return getMinecraft().fontRendererObj;
    }

    public static long getTime() {
        //#if MC<=11202
        return Minecraft.getSystemTime();
        //#else
        //$$ return (long) NativeUtil.getTime();
        //#endif
    }
}
