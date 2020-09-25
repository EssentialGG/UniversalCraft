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

//#if MC>=11602
//$$ import net.minecraft.client.entity.player.ClientPlayerEntity;
//$$ import net.minecraft.client.network.play.ClientPlayNetHandler;
//$$ import net.minecraft.client.world.ClientWorld;
//#else
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
//#endif

//#if MC>=11502
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
            //#if MC>=11202
            //$$ Minecraft.IS_RUNNING_ON_MAC;
            //#else
            Minecraft.isRunningOnMac;
            //#endif

    public static Minecraft getMinecraft() {
        //#if MC>=11602
        //$$ return Minecraft.getInstance();
        //#else
        return Minecraft.getMinecraft();
        //#endif
    }

    //#if MC>=11602
    //$$ public static ClientWorld getWorld() {
    //#else
    public static WorldClient getWorld() {
    //#endif
        //#if MC>=11202
        //$$ return getMinecraft().world;
        //#else
        return getMinecraft().theWorld;
        //#endif
    }

    //#if MC>=11602
    //$$ public static ClientPlayNetHandler getNetHandler() {
    //#else
    public static NetHandlerPlayClient getNetHandler() {
    //#endif
        //#if MC>=11202
        //$$ return getMinecraft().getConnection();
        //#else
        return getMinecraft().getNetHandler();
        //#endif
    }

    public static FontRenderer getFontRenderer() {
        //#if MC>=11202
        //$$ return getMinecraft().fontRenderer;
        //#else
        return getMinecraft().fontRendererObj;
        //#endif
    }

    public static long getTime() {
        //#if MC>=11502
        //$$ return (long) NativeUtil.getTime();
        //#else
        return Minecraft.getSystemTime();
        //#endif
    }

    //#if MC>=11502
    //$$ public static NewChatGui getChatGUI() {
    //#else
    public static GuiNewChat getChatGUI() {
    //#endif
        if (getMinecraft().ingameGUI != null)
            return getMinecraft().ingameGUI.getChatGUI();
        return null;
    }
    //#endif
}
