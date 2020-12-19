package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.client.gui.screen.Screen;
//$$ import net.minecraft.client.util.math.MatrixStack;
//$$ import net.minecraft.text.LiteralText;
//$$ import org.lwjgl.glfw.GLFW;
//#elseif MC>=11502
//$$ import net.minecraft.util.text.StringTextComponent;
//$$ import net.minecraft.client.gui.screen.Screen;
//$$ import com.mojang.blaze3d.matrix.MatrixStack;
//$$ import org.lwjgl.glfw.GLFW;
//#else
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
//#endif

//#if MC>=11502
//$$ public class UniversalScreen extends Screen {
//$$     private long lastClick = 0;
//$$     private int lastScanCode = -1;
//$$     private int lastModifierCode = -1;
//$$     private char lastChar = 0;
//$$     private double lastDraggedDx = -1;
//$$     private double lastDraggedDy = -1;
//$$     private double lastScrolledX = -1;
//$$     private double lastScrolledY = -1;
//#else
public class UniversalScreen extends GuiScreen {
//#endif
    private int guiScaleToRestore = -1;
    private final int newGuiScale;

    //#if MC>=11502
    //$$ private MatrixStack stack = null;
    //$$ private Screen screenToRestore = null;
    //$$
    //$$ public UniversalScreen() {
    //$$     this(false, -1);
    //$$ }
    //$$
    //$$ public UniversalScreen(boolean restoreCurrentGuiOnClose) {
    //$$     this(restoreCurrentGuiOnClose, -1);
    //$$ }
    //$$
    //$$ public UniversalScreen(boolean restoreCurrentGuiOnClose, GuiScale newGuiScale) {
    //$$     this(restoreCurrentGuiOnClose, newGuiScale.getNumber());
    //$$ }
    //$$
    //$$ public UniversalScreen(boolean restoreCurrentGuiOnClose, int newGuiScale) {
        //#if FABRIC
        //$$ super(new LiteralText(""));
        //#else
        //$$ super(new StringTextComponent(""));
        //#endif
    //$$     this.newGuiScale = newGuiScale;
    //$$     if (restoreCurrentGuiOnClose)
    //$$         screenToRestore = getOpenedScreen();
    //$$ }
    //$$
    //$$ public static Screen getOpenedScreen() {
    //$$     return UniversalMinecraft.getMinecraft().currentScreen;
    //$$ }
    //$$
    //$$ protected MatrixStack getMatrixStack() {
    //$$     return stack;
    //$$ }
    //$$
    //$$ @Override
    //$$ protected final void init() {
    //$$     initScreen(width, height);
    //$$
    //$$     if (newGuiScale != -1) {
    //$$         guiScaleToRestore = UniversalMinecraft.getMinecraft().gameSettings.guiScale;
    //$$         UniversalMinecraft.getMinecraft().gameSettings.guiScale = newGuiScale;
    //$$     }
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
    //$$     UniversalKeyboard.Modifiers modifiers = new UniversalKeyboard.Modifiers(
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
    //$$     UniversalKeyboard.Modifiers modifiers = new UniversalKeyboard.Modifiers(
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
    //$$     if (screenToRestore != null)
    //$$         UniversalMinecraft.getMinecraft().displayGuiScreen(screenToRestore);
    //$$     if (guiScaleToRestore != -1)
    //$$         UniversalMinecraft.getMinecraft().gameSettings.guiScale = guiScaleToRestore;
    //$$ }
    //$$
    //$$ @Override
    //#if MC>=11602
    //$$ public final void renderBackground(MatrixStack matrices, int vOffset) {
    //$$     stack = matrices;
    //#else
    //$$ public final void renderBackground(int vOffset) {
    //#endif
    //$$     onDrawBackground(vOffset);
    //$$ }
    //#else
    private GuiScreen screenToRestore = null;

    public UniversalScreen() {
        this(false, -1);
    }

    public UniversalScreen(boolean restoreCurrentGuiOnClose) {
        this(restoreCurrentGuiOnClose, -1);
    }

    public UniversalScreen(boolean restoreCurrentGuiOnClose, GuiScale newGuiScale) {
        this(restoreCurrentGuiOnClose, newGuiScale.getNumber());
    }

    public UniversalScreen(boolean restoreCurrentGuiOnClose, int newGuiScale) {
        super();
        this.newGuiScale = newGuiScale;
        if (restoreCurrentGuiOnClose)
            screenToRestore = getOpenedScreen();
    }

    public static GuiScreen getOpenedScreen() {
        return UniversalMinecraft.getMinecraft().currentScreen;
    }

    @Override
    public final void initGui() {
        initScreen(width, height);

        if (newGuiScale != -1) {
            guiScaleToRestore = UniversalMinecraft.getMinecraft().gameSettings.guiScale;
            UniversalMinecraft.getMinecraft().gameSettings.guiScale = newGuiScale;
        }
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
        if (screenToRestore != null) {
            //#if FORGE
            net.minecraftforge.client.event.GuiOpenEvent event = new net.minecraftforge.client.event.GuiOpenEvent(screenToRestore);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                return;
            //#endif
            UniversalMinecraft.getMinecraft().currentScreen = screenToRestore;
        }
        if (guiScaleToRestore != -1)
            UniversalMinecraft.getMinecraft().gameSettings.guiScale = guiScaleToRestore;
    }

    @Override
    public void drawWorldBackground(int tint) {
        onDrawBackground(tint);
    }
    //#endif

    public void initScreen(int width, int height) {
        //#if MC>=11502
        //$$ super.init();
        //#else
        super.initGui();
        //#endif
    }

    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        //#if MC>=11602
        //$$ super.render(getMatrixStack(), mouseX, mouseY, partialTicks);
        //#elseif MC>=11502
        //$$ super.render(mouseX, mouseY, partialTicks);
        //#else
        super.drawScreen(mouseX, mouseY, partialTicks);
        //#endif
    }

    public void onKeyPressed(int keyCode, char typedChar, UniversalKeyboard.Modifiers modifiers) {
        //#if MC>=11502
        //$$ super.keyPressed(keyCode, lastScanCode, lastModifierCode);
        //#else
        try {
            super.keyTyped(typedChar, keyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //#endif
    }

    public void onKeyReleased(int keyCode, char typedChar, UniversalKeyboard.Modifiers modifiers) {
        //#if MC>=11502
        //$$ super.keyPressed(keyCode, lastScanCode, lastModifierCode);
        //#endif
    }

    public void onMouseClicked(double mouseX, double mouseY, int mouseButton) {
        //#if MC>=11502
        //$$ if (mouseButton == 1)
        //$$     lastClick = UniversalMinecraft.getTime();
        //$$ super.mouseClicked(mouseX, mouseY, mouseButton);
        //#else
        try {
            super.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //#endif
    }

    public void onMouseReleased(double mouseX, double mouseY, int state) {
        //#if MC>=11502
        //$$ super.mouseReleased(mouseX, mouseY, state);
        //#else
        super.mouseReleased((int) mouseX, (int) mouseY, state);
        //#endif
    }

    public void onMouseDragged(double x, double y, int clickedButton, long timeSinceLastClick) {
        //#if MC>=11502
        //$$ super.mouseDragged(x, y, clickedButton, lastDraggedDx, lastDraggedDy);
        //#else
        super.mouseClickMove((int) x, (int) y, clickedButton, timeSinceLastClick);
        //#endif
    }

    public void onMouseScrolled(double delta) {
        //#if MC>=11502
        //$$ super.mouseScrolled(lastScrolledX, lastScrolledY, delta);
        //#endif
    }

    public void onTick() {
        //#if MC>=11502
        //$$ super.tick();
        //#else
        super.updateScreen();
        //#endif
    }

    public void onScreenClose() {
        //#if MC>=11502
        //$$ super.onClose();
        //#else
        super.onGuiClosed();
        //#endif
    }

    public void onDrawBackground(int tint) {
        //#if MC>=11602
        //$$ super.renderBackground(getMatrixStack(), tint);
        //#elseif MC>=11502
        //$$ super.renderBackground(tint);
        //#else
        super.drawWorldBackground(tint);
        //#endif
    }
}
