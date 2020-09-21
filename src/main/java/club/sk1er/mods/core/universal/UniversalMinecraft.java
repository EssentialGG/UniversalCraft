package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.client.font.TextRenderer;
//$$ import net.minecraft.client.network.ClientPlayNetworkHandler;
//$$ import net.minecraft.client.network.ClientPlayerEntity;
//$$ import net.minecraft.client.world.ClientWorld;
//$$ import org.lwjgl.glfw.GLFW;
//#else
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
//$$ import net.minecraft.client.gui.NewChatGui;
//#else
import net.minecraft.client.gui.GuiNewChat;
//#endif
//#endif

public class UniversalMinecraft {
    //#if FABRIC
    //$$ public static boolean isRunningOnMac = MinecraftClient.IS_SYSTEM_MAC;
    //$$
    //$$ public static MinecraftClient getMinecraft() {
    //$$     return MinecraftClient.getInstance();
    //$$ }
    //$$
    //$$ public static ClientWorld getWorld() {
    //$$     return getMinecraft().world;
    //$$ }
    //$$
    //$$ public static ClientPlayNetworkHandler getNetHandler() {
    //$$     return getMinecraft().getNetworkHandler();
    //$$ }
    //$$
    //$$ public static ClientPlayerEntity getPlayer() {
    //$$     return getMinecraft().player;
    //$$ }
    //$$
    //$$ public static TextRenderer getFontRenderer() {
    //$$     return getMinecraft().textRenderer;
    //$$ }
    //$$
    //$$ public static long getTime() {
    //$$     return (long) GLFW.glfwGetTime();
    //$$ }
    //#else
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

    //#if MC<=11202
    public static GuiNewChat getChatGUI() {
    //#else
    //$$ public static NewChatGui getChatGUI() {
    //#endif
        if (getMinecraft().ingameGUI != null)
            return getMinecraft().ingameGUI.getChatGUI();
        return null;
    }
    //#endif
}
