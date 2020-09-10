package club.sk1er.mods.core.universal;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

//#if MC>=11502
//$$ import net.minecraft.client.gui.widget.button.AbstractButton;
//$$ import net.minecraft.entity.player.PlayerEntity;
//$$ import net.minecraft.client.gui.screen.Screen;
//$$ import net.minecraft.client.gui.widget.Widget;
//$$ import net.minecraft.client.gui.widget.button.Button;
//$$ import net.minecraft.client.renderer.entity.PlayerRenderer;
//#else
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
//#endif

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
        public static GuiScreen getGui(GuiScreenEvent event) {
        //#else
        //$$ public static Screen getGui(GuiScreenEvent event) {
        //#endif
            //#if MC<=10809
            return event.gui;
            //#else
            //$$ return event.getGui();
            //#endif
        }

        public static class InitGuiEvent {
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

        //#if MC<11602
        public static class ActionPerformedEvent {
            //#if MC<11502
            public static GuiButton getButton(GuiScreenEvent.ActionPerformedEvent event) {
            //#else
            //$$ public static Button getButton(GuiScreenEvent.ActionPerformedEvent event) {
            //#endif
                //#if MC<=10809
                return event.button;
                //#else
                //$$ return event.getButton();
                //#endif
            }
        }
        //#endif
    }

    public static class URenderPlayerEvent {
        //#if MC<11502
        public static EntityPlayer getPlayer(RenderPlayerEvent event) {
        //#else
        //$$ public static PlayerEntity getPlayer(RenderPlayerEvent event) {
        //#endif
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
            //$$ return getPlayer(event).getPosX();
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
            //$$ return getPlayer(event).getPosY();
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
            //$$ return getPlayer(event).getPosZ();
            //#endif
            //#endif
        }

        //#if MC<11502
        public static RenderPlayer getRenderer(RenderPlayerEvent event) {
        //#else
        //$$ public static PlayerRenderer getRenderer(RenderPlayerEvent event) {
        //#endif
            //#if MC<11202
            return event.renderer;
            //#else
            //$$ return event.getRenderer();
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
