package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.client.gui.screen.Screen;
//$$ import net.minecraft.client.util.math.MatrixStack;
//$$ import net.minecraft.text.LiteralText;
//$$ import org.lwjgl.glfw.GLFW;
//#else
import net.minecraft.client.Minecraft;
//$$
//#if MC<=11202
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
//#else
//$$ import net.minecraft.util.text.StringTextComponent;
//$$ import net.minecraft.client.gui.screen.Screen;
//$$ import com.mojang.blaze3d.matrix.MatrixStack;
//$$ import org.lwjgl.glfw.GLFW;
//#endif
//#endif

//#if MC<=11202
public class UniversalScreen extends GuiScreen implements IUniversalScreen {
//#else
//$$ public class UniversalScreen extends Screen implements IUniversalScreen {
//$$     private long lastClick = 0;
//$$     private int lastKeyCode = 0;
//#endif

    //#if MC<11500
    public UniversalScreen() {
        super();
    }

    @Override
    public void initGui() {
        if (initScreen(width, height))
            super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (onDrawScreen(mouseX, mouseY, partialTicks))
            super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (onKeyPressed(keyCode, UniversalKeyboard.getModifiers()))
            super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (onMouseClicked(mouseX, mouseY, mouseButton))
            super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (onMouseReleased(mouseX, mouseY, state))
            super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (onMouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick))
            super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scrollDelta = Mouse.getEventDWheel();
        if (scrollDelta != 0)
            onMouseScrolled(scrollDelta);
    }

    @Override
    public void updateScreen() {
        if (onTick())
            super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        if (onScreenClose())
            super.onGuiClosed();
    }

    @Override
    public void drawWorldBackground(int tint) {
        if (onDrawWorldBackground(tint))
            super.drawWorldBackground(tint);
    }

    @Override
    public void drawDefaultBackground() {
        if (onDrawDefaultBackground(0))
            super.drawDefaultBackground();
    }
    //#else
    //$$ private MatrixStack stack = null;
    //$$
    //$$ public UniversalScreen() {
    //#if FABRIC
    //$$     super(new LiteralText(""));
    //#else
    //$$     super(new StringTextComponent(""));
    //#endif
    //$$ }
    //$$
    //$$ protected MatrixStack getMatrixStack() {
    //$$     return stack;
    //$$ }
    //$$
    //$$ @Override
    //$$ protected final void init() {
    //$$     if (initScreen(width, height))
    //$$         super.init();
    //$$ }
    //$$
    //$$ @Override
    //#if MC>=11602
    //$$ public final void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    //$$     stack = matrixStack;
    //$$     if (onDrawScreen(mouseX, mouseY, partialTicks))
    //$$         render(matrixStack, mouseX, mouseY, partialTicks);
    //#else
    //$$ public final void render(int mouseX, int mouseY, float partialTicks) {
    //$$     if (onDrawScreen(mouseX, mouseY, partialTicks))
    //$$         render(mouseX, mouseY, partialTicks);
    //#endif
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean keyPressed(int keyCode, int scanCode, int modifierCode) {
    //$$     UniversalKeyboard.Modifier modifiers = new UniversalKeyboard.Modifier(
    //$$             (modifierCode & GLFW.GLFW_MOD_CONTROL) != 0,
    //$$             (modifierCode & GLFW.GLFW_MOD_SHIFT) != 0,
    //$$             (modifierCode & GLFW.GLFW_MOD_ALT) != 0
    //$$     );
    //$$
    //$$     if (onKeyPressed(keyCode, modifiers))
    //$$         return super.keyPressed(keyCode, scanCode, modifierCode);
    //$$
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean keyReleased(int keyCode, int scanCode, int modifierCode) {
    //$$     UniversalKeyboard.Modifier modifiers = new UniversalKeyboard.Modifier(
    //$$             (modifierCode & GLFW.GLFW_MOD_CONTROL) != 0,
    //$$             (modifierCode & GLFW.GLFW_MOD_SHIFT) != 0,
    //$$             (modifierCode & GLFW.GLFW_MOD_ALT) != 0
    //$$     );
    //$$
    //$$     if (onKeyReleased(keyCode, modifiers))
    //$$         return super.keyReleased(keyCode, scanCode, modifierCode);
    //$$
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean charTyped(char ch, int modifierCode) {
    //$$     UniversalKeyboard.Modifier modifiers = new UniversalKeyboard.Modifier(
    //$$             (modifierCode & GLFW.GLFW_MOD_CONTROL) != 0,
    //$$             (modifierCode & GLFW.GLFW_MOD_SHIFT) != 0,
    //$$             (modifierCode & GLFW.GLFW_MOD_ALT) != 0
    //$$     );
    //$$
    //$$     if (onCharTyped(ch, modifiers))
    //$$         return super.charTyped(ch, modifierCode);
    //$$
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    //$$     if (mouseButton == 1)
    //$$         lastClick = UniversalMinecraft.getTime();
    //$$     if (onMouseClicked(mouseX, mouseY, mouseButton))
    //$$         return super.mouseClicked(mouseX, mouseY, mouseButton);
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean mouseReleased(double mouseX, double mouseY, int button) {
    //$$     if (onMouseReleased(mouseX, mouseY, button))
    //$$         return super.mouseReleased(mouseX, mouseY, button);
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean mouseDragged(double x, double y, int mouseButton, double dx, double dy) {
    //$$     if (onMouseDragged(x, y, mouseButton, UniversalMinecraft.getTime() - lastClick))
    //$$         return super.mouseDragged(x, y, mouseButton, dx, dy);
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
    //$$     if (onMouseScrolled(delta))
    //$$         return super.mouseScrolled(mouseX, mouseY, delta);
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final void tick() {
    //$$     if (onTick())
    //$$         super.tick();
    //$$ }
    //$$
    //$$ @Override
    //$$ public void onClose() {
    //$$     if (onScreenClose())
    //$$         super.onClose();
    //$$ }
    //$$
    //$$ @Override
    //#if FABRIC
    //$$ public void renderBackgroundTexture(int vOffset) {
    //$$     if (onDrawDefaultBackground(vOffset))
    //$$         super.renderBackgroundTexture(vOffset);
    //#else
    //$$ public void renderDirtBackground(int vOffset) {
    //$$    if (onDrawDefaultBackground(vOffset))
    //$$        super.renderDirtBackground(vOffset);
    //#endif
    //$$ }
    //$$
    //$$ @Override
    //#if MC>=11602
    //$$ public void renderBackground(MatrixStack matrixStack, int vOffset) {
    //$$     stack = matrixStack;
    //$$     if (onDrawWorldBackground(vOffset))
    //$$         super.renderBackground(matrixStack, vOffset);
    //#else
    //$$ public void renderBackground(int vOffset) {
    //$$     if (onDrawWorldBackground(vOffset))
    //$$         super.renderBackground(vOffset);
    //#endif
    //$$ }
    //#endif

    @Override
    public boolean initScreen(int width, int height) { return true; }

    @Override
    public boolean onDrawScreen(int mouseX, int mouseY, float partialTicks) { return true; }

    @Override
    public boolean onKeyPressed(int keyCode, UniversalKeyboard.Modifier modifiers) { return true; }

    @Override
    public boolean onKeyReleased(int keyCode, UniversalKeyboard.Modifier modifiers) { return true; }

    @Override
    public boolean onCharTyped(char ch, UniversalKeyboard.Modifier modifiers) { return true; }

    @Override
    public boolean onMouseClicked(double mouseX, double mouseY, int mouseButton) { return true; }

    @Override
    public boolean onMouseReleased(double mouseX, double mouseY, int state) { return true; }

    @Override
    public boolean onMouseDragged(double x, double y, int clickedButton, long timeSinceLastClick) { return true; }

    @Override
    public boolean onMouseScrolled(double delta) { return true; }

    @Override
    public boolean onTick() { return true; }

    @Override
    public boolean onScreenClose() { return true; }

    @Override
    public boolean onDrawDefaultBackground(int tint) { return true; }

    @Override
    public boolean onDrawWorldBackground(int tint) { return true; }
}
