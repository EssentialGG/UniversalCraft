package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.client.gui.screen.Screen;
//$$ import net.minecraft.client.util.math.MatrixStack;
//$$ import net.minecraft.text.LiteralText;
//$$ import org.lwjgl.glfw.GLFW;
//#else
import net.minecraft.client.Minecraft;

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
public class UniversalScreen extends GuiScreen {
//#else
//$$     public class UniversalScreen extends Screen {
//$$         private long lastClick = 0;
//$$         private int lastScanCode = -1;
//$$         private int lastModifierCode = -1;
//$$         private char lastChar = 0;
//$$         private double lastDraggedDx = -1;
//$$         private double lastDraggedDy = -1;
//$$         private double lastScrolledX = -1;
//$$         private double lastScrolledY = -1;
//#endif

    //#if MC<11502
    public UniversalScreen() {
        super();
    }

    @Override
    public final void initGui() {
        initScreen(width, height);
    }

    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        onDrawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected final void keyTyped(char typedChar, int keyCode) {
        onKeyPressed(keyCode, typedChar, UniversalKeyboard.getModifiers());
    }

    @Override
    protected final void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        onMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected final void mouseReleased(int mouseX, int mouseY, int state) {
        onMouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected final void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        onMouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public final void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scrollDelta = Mouse.getEventDWheel();
        if (scrollDelta != 0)
            onMouseScrolled(scrollDelta);
    }

    @Override
    public final void updateScreen() {
        onTick();
    }

    @Override
    public final void onGuiClosed() {
        onScreenClose();
    }

    @Override
    public final void drawBackground(int tint) {
        onDrawBackground(tint);
    }
    //#else
    //$$ private MatrixStack stack = null;
    //$$
    //$$ public UniversalScreen() {
        //#if FABRIC
        //$$ super(new LiteralText(""));
        //#else
        //$$ super(new StringTextComponent(""));
        //#endif
    //$$ }
    //$$
    //$$ protected MatrixStack getMatrixStack() {
    //$$     return stack;
    //$$ }
    //$$
    //$$ @Override
    //$$ protected final void init() {
    //$$     initScreen(width, height);
    //$$ }
    //$$
    //$$ @Override
    //#if MC>=11602
    //$$ public final void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    //$$     stack = matrixStack;
    //$$     onDrawScreen(mouseX, mouseY, partialTicks);
    //#else
    //$$ public final void render(int mouseX, int mouseY, float partialTicks) {
    //$$     onDrawScreen(mouseX, mouseY, partialTicks);
    //$$     super.render(mouseX, mouseY, partialTicks);
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
    //$$     lastScanCode = scanCode;
    //$$     lastModifierCode = modifierCode;
    //$$     onKeyPressed(keyCode, lastChar, modifiers);
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
    //$$     lastScanCode = scanCode;
    //$$     lastModifierCode = modifierCode;
    //$$     onKeyReleased(keyCode, lastChar, modifiers);
    //$$
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean charTyped(char ch, int modifierCode) {
    //$$     lastChar = ch;
    //$$     return super.charTyped(ch, modifierCode);
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    //$$     if (mouseButton == 1)
    //$$         lastClick = UniversalMinecraft.getTime();
    //$$     onMouseClicked(mouseX, mouseY, mouseButton);
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean mouseReleased(double mouseX, double mouseY, int button) {
    //$$     onMouseReleased(mouseX, mouseY, button);
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean mouseDragged(double x, double y, int mouseButton, double dx, double dy) {
    //$$     lastDraggedDx = dx;
    //$$     lastDraggedDy = dy;
    //$$     onMouseDragged(x, y, mouseButton, UniversalMinecraft.getTime() - lastClick);
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final boolean mouseScrolled(double mouseX, double mouseY, double delta) {
    //$$     lastScrolledX = mouseX;
    //$$     lastScrolledY = mouseY;
    //$$     onMouseScrolled(delta);
    //$$     return false;
    //$$ }
    //$$
    //$$ @Override
    //$$ public final void tick() {
    //$$     onTick();
    //$$ }
    //$$
    //$$ @Override
    //$$ public final void onClose() {
    //$$     onScreenClose();
    //$$ }
    //$$
    //$$ @Override
    //#if FABRIC
    //$$ public final void renderBackgroundTexture(int vOffset) {
    //#else
    //$$ public final void renderDirtBackground(int vOffset) {
    //#endif
    //$$     onDrawBackground(vOffset);
    //$$ }
    //#endif

    public void initScreen(int width, int height) {
        //#if MC<11502
        super.initGui();
        //#else
        //$$ super.init();
        //#endif
    }

    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        //#if MC<11502
        super.drawScreen(mouseX, mouseY, partialTicks);
        //#else
        //#if MC>=11602
        //$$ super.render(getMatrixStack(), mouseX, mouseY, partialTicks);
        //#else
        //$$ super.render(mouseX, mouseY, partialTicks);
        //#endif
        //#endif
    }

    public void onKeyPressed(int keyCode, char typedChar, UniversalKeyboard.Modifier modifiers) {
        //#if MC<11502
        try {
            super.keyTyped(typedChar, keyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //#else
        //$$ super.keyPressed(keyCode, lastScanCode, lastModifierCode);
        //#endif
    }

    public void onKeyReleased(int keyCode, char typedChar, UniversalKeyboard.Modifier modifiers) {
        //#if MC>=11502
        //$$ super.keyPressed(keyCode, lastScanCode, lastModifierCode);
        //#endif
    }

    public void onMouseClicked(double mouseX, double mouseY, int mouseButton) {
        //#if MC<11502
        try {
            super.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //#else
        //$$ if (mouseButton == 1)
        //$$     lastClick = UniversalMinecraft.getTime();
        //$$ super.mouseClicked(mouseX, mouseY, mouseButton);
        //#endif
    }

    public void onMouseReleased(double mouseX, double mouseY, int state) {
        //#if MC<11502
        super.mouseReleased((int) mouseX, (int) mouseY, state);
        //#else
        //$$ super.mouseReleased(mouseX, mouseY, state);
        //#endif
    }

    public void onMouseDragged(double x, double y, int clickedButton, long timeSinceLastClick) {
        //#if MC<11502
        super.mouseClickMove((int) x, (int) y, clickedButton, timeSinceLastClick);
        //#else
        //$$ super.mouseDragged(x, y, clickedButton, lastDraggedDx, lastDraggedDy);
        //#endif
    }

    public void onMouseScrolled(double delta) {
        //#if MC>=11502
        //$$ super.mouseScrolled(lastScrolledX, lastScrolledY, delta);
        //#endif
    }

    public void onTick() {
        //#if MC<11502
        super.updateScreen();
        //#else
        //$$ super.tick();
        //#endif
    }

    public void onScreenClose() {
        //#if MC<11502
        super.onGuiClosed();
        //#else
        //$$ super.onClose();
        //#endif
    }

    public void onDrawBackground(int tint) {
        //#if MC<11502
        super.drawDefaultBackground();
        //#else
        //#if MC>=11602
        //$$ super.renderBackground(getMatrixStack(), tint);
        //#else
        //$$ super.renderBackground(tint);
        //#endif
        //#endif
    }
}
