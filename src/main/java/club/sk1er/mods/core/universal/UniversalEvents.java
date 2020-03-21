package club.sk1er.mods.core.universal;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.List;

public class UniversalEvents {
    public static class UGuiOpenEvent {
        //#if MC<11502
        public static GuiScreen getGui(GuiOpenEvent event) {
            //#else
            //$$ public static Screen getGui(GuiOpenEvent event) {
            //#endif
            //#if MC<=10809
            return event.gui;
            //#else
            //$$ return event.getGui();
            //#endif
        }

    }


    public static class UGuiScreenEvent {
        //#if MC<11502
        public static GuiScreen getGui(GuiOpenEvent event) {
            //#else
            //$$ public static Screen getGui(GuiOpenEvent event) {
            //#endif
            //#if MC<=10809
            return event.gui;
            //#else
            //$$ return event.getGui();
            //#endif
        }
    }
    public static class UGuiScreenEvent$InitGuiEvent {

        //#if MC<11502
        public static List<GuiButton> getButtonList(GuiScreenEvent.InitGuiEvent event) {
            //#else
            //$$ public static List<Widget> getButtonList(GuiScreenEvent.InitGuiEvent event) {
            //#endif
            //#if MC<=10809
            return event.buttonList;
            //#else
                //#if MC<11502
                //$$ return event.getButtonList();
                //#else
                //$$ return event.getWidgetList();
                //#endif
            //#endif
        }
    }
    public static class UGuiScreenEvent$ActionPerformedEvent {

        public static GuiButton getButton(GuiScreenEvent.ActionPerformedEvent event) {
            //#if MC<=10809
            return event.button;
            //#else
            //$$ return event.getButton();
            //#endif
        }
    }
    public static class URenderPlayerEvent {
        public static EntityPlayer getPlayer(RenderPlayerEvent event) {
            //#if MC<=10809
            return event.entityPlayer;
            //#else
                //#if MC<11502
                //$$ return event.getEntityPlayer();
                //#else
                //$$ return event.getPlayer();
                //#endif
            //#endif
        }

        public static double getX(RenderPlayerEvent event) {
            //#if MC<=10809
            return event.x;
            //#else
                //#if MC<11502
                //$$ return event.getX();
                //#else
                //$$ return 0;
                //#endif
            //#endif
        }

        public static double getY(RenderPlayerEvent event) {
            //#if MC<=10809
            return event.y;
            //#else
                //#if MC<11502
                //$$ return event.getY();
                //#else
                //$$ return 0;
                //#endif
            //#endif
        }

        public static double getZ(RenderPlayerEvent event) {
            //#if MC<=10809
            return event.z;
            //#else
                //#if MC<11502
                //$$ return event.getZ();
                //#else
                //$$ return 0;
                //#endif
            //#endif
        }

        public static RenderPlayer getRenderer(RenderPlayerEvent event) {
            //#if MC>10809
            //$$ return event.getRenderer();
            //#else
            return event.renderer;
            //#endif
        }

        public static float getPartialTicks(RenderPlayerEvent event) {
            //#if MC<=10809
            return event.partialRenderTick;
            //#else
            //$$ return event.getPartialRenderTick();
            //#endif
        }
    }

}
