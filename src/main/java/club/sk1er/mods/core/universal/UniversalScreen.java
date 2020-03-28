package club.sk1er.mods.core.universal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
//#if MC<=11202
//#else
//$$ import net.minecraft.util.text.StringTextComponent;
//$$ import net.minecraft.client.gui.screen.Screen;
//#endif

//#if MC<=11202
public class UniversalScreen extends GuiScreen {
    //#else
//$$ public class UniversalScreen extends Screen {
//#endif
    private long lastClick = 0;

    //#if MC<11500
    public UniversalScreen() {
        super();
    }
    //#else
    //$$ public UniversalScreen() {
    //$$     super(new StringTextComponent(""));
    //$$ }
    //#endif

    //#if MC>=11500
    //$$ public void render(int mouseX, int mouseY, float partialTicks) {
    //$$     drawScreen(mouseX, mouseY, partialTicks);
    //$$ }
    //#endif
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //#if MC>=11500
        //super.render(mouseX,mouseY,partialTicks);
        //#else
        super.drawScreen(mouseX, mouseY, partialTicks);
        //#endif
    }

    //#if MC>=11500
    //$$ public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)  {
    //$$    try {
    //$$         mouseClicked((int)mouseX,(int)mouseY,mouseButton);
    //$$    } catch (IOException e) {
    //$$        e.printStackTrace();
    //$$    }
    //$$    return false;
    //$$ }
    //#endif

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 1) lastClick = UniversalMinecraft.getTime();
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    //#if MC>=11500
    //$$ public boolean charTyped(char typedChar, int keyCode) {
    //$$     try {
    //$$         keyTyped(typedChar, keyCode);
    //$$     } catch (IOException e) {
    //$$         e.printStackTrace();
    //$$     }
    //$$     return false;
    //$$ }
    //#endif
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        //#if MC>=11500
        //$$ super.charTyped(typedChar, keyCode);
        //#else
        super.keyTyped(typedChar, keyCode);
        //#endif
    }

    //#if MC>=11500
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        mouseReleased((int) mouseX, (int) mouseY, state);
        return false;
    }
    //#endif

    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
    //#if MC>=11500
    //$$ public boolean mouseDragged(double x, double y, int buttonId, double dx, double dy) {
    //$$     mouseClickMove((int)x,(int)y,buttonId,UniversalMinecraft.getTime()-lastClick);
    //$$     return false;
    //$$ }
    //#endif

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        //#if MC>=11500
        //#else
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        //#endif

    }

    //TODO see if we can do this in 1.15
    //#if MC<11500
    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }
    //#endif

    //#if MC>=11500
    //$$ public void init(Minecraft mc, int width, int height) {
    //$$     setWorldAndResolution(mc,width,height);
    //$$ }
    //#endif

    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        //#if MC>=11500
        //$$ super.init(mc, width, height);
        //#else
        super.setWorldAndResolution(mc, width, height);
        //#endif
    }
    //#if MC>=11500
    //$$ public void setSize(int w, int h) {
    //$$     setGuiSize(w, h);
    //$$ }
    //#endif

    public void setGuiSize(int w, int h) {
        //#if MC>=11500
        //$$ super.setSize(w,h);
        //#else
        super.setGuiSize(w, h);
        //#endif
    }

    //#if MC>=11500
    //$$protected void init() {
    //$$   initGui();
    //$$}
    //#endif
    public void initGui() {
        //#if MC>=11500
        //$$ super.init();
        //#else
        super.initGui();
        //#endif
    }


    //#if MC>11500
    //$$ public void tick() {
    //$$     super.tick();
    //$$ }
    //#endif

    public void updateScreen() {
        //#if MC>11500
        //$$ super.tick();
        //#else
        super.updateScreen();
        //#endif

    }

    //#if MC>=11500
    //$$ public void onClose() {
    //$$    onGuiClosed();
    //$$ }
    //#endif

    public void onGuiClosed() {
        //#if MC>=11500
        //$$  super.onClose();
        //#else
        super.onGuiClosed();
        //#endif
    }


    public void drawDefaultBackground() {
        //#if MC>=11500
        //$$ super.renderBackground();
        //#else
        super.drawDefaultBackground();
        //#endif
    }
    //#if MC>11500
    //$$ public void renderBackground() {
    //$$     drawWorldBackground(0);
    //$$ }
    //#endif

    public void drawWorldBackground(int tint) {
        //#if MC>=11500
        //$$ super.renderBackground(tint);
        //#else
        super.drawWorldBackground(tint);
        //#endif
    }

    //#if MC>=11500
    //$$ public void resize(Minecraft mcIn, int w, int h) {
    //$$     onResize(mcIn, w, h);
    //$$ }
    //#endif

    public void onResize(Minecraft mcIn, int w, int h) {
        //#if MC>=11500
        //$$ resize(mcIn, w, h);
        //#else
        super.onResize(mcIn, w, h);
        //#endif
    }


}
