package gg.essential.universal

import gg.essential.universal.utils.MCScreen

//#if FABRIC
//$$ import gg.essential.universal.utils.MCStringTextComponent
//$$ import net.minecraft.client.MinecraftClient
//$$ import net.minecraft.client.util.math.MatrixStack
//$$ import net.minecraft.text.LiteralText
//$$ import org.lwjgl.glfw.GLFW
//#elseif MC>=11502
//$$ import gg.essential.universal.utils.MCStringTextComponent
//$$ import net.minecraft.util.text.StringTextComponent
//$$ import com.mojang.blaze3d.matrix.MatrixStack
//$$ import org.lwjgl.glfw.GLFW
//#else
import org.lwjgl.input.Mouse
import java.io.IOException

//#endif

abstract class UScreen @JvmOverloads constructor(
    val restoreCurrentGuiOnClose: Boolean = false,
    val newGuiScale: Int = -1
) :
//#if MC>=11502
//$$     MCScreen(MCStringTextComponent(""))
//#else
    MCScreen()
//#endif
{
    private var guiScaleToRestore = -1
    private val screenToRestore: MCScreen? = if (restoreCurrentGuiOnClose) currentScreen else null

    //#if MC>=11502
    //$$ private var lastClick = 0L
    //$$ private var lastScanCode = -1
    //$$ private var lastModifierCode = -1
    //$$ private var lastChar = 0.toChar()
    //$$ private var lastDraggedDx = -1.0
    //$$ private var lastDraggedDy = -1.0
    //$$ private var lastScrolledX = -1.0
    //$$ private var lastScrolledY = -1.0
    //$$
    //$$ protected lateinit var matrixStack: MatrixStack
    //$$
    //$$ final override fun init() {
    //$$     if (newGuiScale != -1) {
    //$$         guiScaleToRestore = UMinecraft.guiScale
    //$$         UMinecraft.guiScale = newGuiScale
    //$$         width = UResolution.scaledWidth
    //$$         height = UResolution.scaledHeight
    //$$     }
    //$$     initScreen(width, height)
    //$$ }
    //$$
    //#if MC>=11602
    //$$ final override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
    //$$     this.matrixStack = matrixStack
    //$$     onDrawScreen(mouseX, mouseY, partialTicks)
    //#else
    //$$ final override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
    //$$     onDrawScreen(mouseX, mouseY, partialTicks)
    //$$     super.render(mouseX, mouseY, partialTicks)
    //#endif
    //$$ }
    //$$
    //$$ final override fun keyPressed(keyCode: Int, scanCode: Int, modifierCode: Int): Boolean {
    //$$     val modifiers = UKeyboard.Modifiers(
    //$$         (modifierCode and GLFW.GLFW_MOD_CONTROL) != 0,
    //$$         (modifierCode and GLFW.GLFW_MOD_SHIFT) != 0,
    //$$         (modifierCode and GLFW.GLFW_MOD_ALT) != 0
    //$$     )
    //$$
    //$$     lastScanCode = scanCode
    //$$     lastModifierCode = modifierCode
    //$$     onKeyPressed(keyCode, lastChar, modifiers)
    //$$
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun keyReleased(keyCode: Int, scanCode: Int, modifierCode: Int): Boolean {
    //$$     val modifiers = UKeyboard.Modifiers(
    //$$         (modifierCode and GLFW.GLFW_MOD_CONTROL) != 0,
    //$$         (modifierCode and GLFW.GLFW_MOD_SHIFT) != 0,
    //$$         (modifierCode and GLFW.GLFW_MOD_ALT) != 0
    //$$     )
    //$$
    //$$     lastScanCode = scanCode
    //$$     lastModifierCode = modifierCode
    //$$     onKeyReleased(keyCode, lastChar, modifiers)
    //$$
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun charTyped(char: Char, modifierCode: Int): Boolean {
    //$$     lastChar = char
    //$$     return super.charTyped(char, modifierCode)
    //$$ }
    //$$
    //$$ final override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
    //$$     if (mouseButton == 1)
    //$$         lastClick = UMinecraft.getTime()
    //$$     onMouseClicked(mouseX, mouseY, mouseButton)
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun mouseReleased(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
    //$$     onMouseReleased(mouseX, mouseY, mouseButton)
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun mouseDragged(x: Double, y: Double, mouseButton: Int, dx: Double, dy: Double): Boolean {
    //$$     lastDraggedDx = dx
    //$$     lastDraggedDy = dy
    //$$     onMouseDragged(x, y, mouseButton, UMinecraft.getTime() - lastClick)
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun mouseScrolled(mouseX: Double, mouseY: Double, delta: Double): Boolean {
    //$$     lastScrolledX = mouseX
    //$$     lastScrolledY = mouseY
    //$$     onMouseScrolled(delta)
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun tick(): Unit = onTick()
    //$$
    //$$ final override fun onClose() {
    //$$     // onScreenClose()
    //$$     if (screenToRestore != null)
    //$$         displayScreen(screenToRestore)
    //$$     if (guiScaleToRestore != -1)
    //$$         UMinecraft.guiScale = guiScaleToRestore
    //$$ }
    //$$
    //#if MC>=11602
    //$$ final override fun renderBackground(matrixStack: MatrixStack, vOffset: Int) {
    //$$     this.matrixStack = matrixStack
    //#else
    //$$ final override fun renderBackground(vOffset: Int) {
    //#endif
    //$$     onDrawBackground(vOffset)
    //$$ }
    //#else
    final override fun initGui() {
        if (newGuiScale != -1) {
            guiScaleToRestore = UMinecraft.guiScale
            UMinecraft.guiScale = newGuiScale
            width = UResolution.scaledWidth
            height = UResolution.scaledHeight
        }
        initScreen(width, height)
    }

    final override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        onDrawScreen(mouseX, mouseY, partialTicks)
    }

    final override fun keyTyped(typedChar: Char, keyCode: Int) {
        onKeyPressed(keyCode, typedChar, UKeyboard.getModifiers())
    }

    final override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        onMouseClicked(mouseX.toDouble(), mouseY.toDouble(), mouseButton)
    }

    final override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        onMouseReleased(mouseX.toDouble(), mouseY.toDouble(), state)
    }

    final override fun mouseClickMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) {
        onMouseDragged(mouseX.toDouble(), mouseY.toDouble(), clickedMouseButton, timeSinceLastClick)
    }

    final override fun handleMouseInput() {
        super.handleMouseInput()
        val scrollDelta = Mouse.getEventDWheel()
        if (scrollDelta != 0)
            onMouseScrolled(scrollDelta.toDouble())
    }

    final override fun updateScreen() {
        onTick()
    }

    final override fun onGuiClosed() {
        onScreenClose()
        if (screenToRestore != null) {
            //#if FORGE
            val event = net.minecraftforge.client.event.GuiOpenEvent(screenToRestore)
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                return
            //#endif
            UMinecraft.getMinecraft().currentScreen = screenToRestore
        }
        if (guiScaleToRestore != -1)
            UMinecraft.guiScale = guiScaleToRestore
    }

    final override fun drawWorldBackground(tint: Int) {
        onDrawBackground(tint)
    }
    //#endif

    constructor(restoreCurrentGuiOnClose: Boolean, newGuiScale: GuiScale) : this(restoreCurrentGuiOnClose, newGuiScale.ordinal)

    open fun initScreen(width: Int, height: Int) {
        //#if MC>=11502
        //$$ super.init()
        //#else
        super.initGui()
        //#endif
    }

    open fun onDrawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        //#if MC>=11602
        //$$ super.render(matrixStack, mouseX, mouseY, partialTicks)
        //#elseif MC>=11502
        //$$ super.render(mouseX, mouseY, partialTicks)
        //#else
        super.drawScreen(mouseX, mouseY, partialTicks)
        //#endif
    }

    open fun onKeyPressed(keyCode: Int, typedChar: Char, modifiers: UKeyboard.Modifiers?) {
        //#if MC>=11502
        //$$ super.keyPressed(keyCode, lastScanCode, lastModifierCode)
        //#else
        try {
            super.keyTyped(typedChar, keyCode)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //#endif
    }

    open fun onKeyReleased(keyCode: Int, typedChar: Char, modifiers: UKeyboard.Modifiers?) {
        //#if MC>=11502
        //$$ super.keyPressed(keyCode, lastScanCode, lastModifierCode)
        //#endif
    }

    open fun onMouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        //#if MC>=11502
        //$$ if (mouseButton == 1)
        //$$     lastClick = UMinecraft.getTime()
        //$$ super.mouseClicked(mouseX, mouseY, mouseButton)
        //#else
        try {
            super.mouseClicked(mouseX.toInt(), mouseY.toInt(), mouseButton)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //#endif
    }

    open fun onMouseReleased(mouseX: Double, mouseY: Double, state: Int) {
        //#if MC>=11502
        //$$ super.mouseReleased(mouseX, mouseY, state)
        //#else
        super.mouseReleased(mouseX.toInt(), mouseY.toInt(), state)
        //#endif
    }

    open fun onMouseDragged(x: Double, y: Double, clickedButton: Int, timeSinceLastClick: Long) {
        //#if MC>=11502
        //$$ super.mouseDragged(x, y, clickedButton, lastDraggedDx, lastDraggedDy)
        //#else
        super.mouseClickMove(x.toInt(), y.toInt(), clickedButton, timeSinceLastClick)
        //#endif
    }

    open fun onMouseScrolled(delta: Double) {
        //#if MC>=11502
        //$$ super.mouseScrolled(lastScrolledX, lastScrolledY, delta)
        //#endif
    }

    open fun onTick() {
        //#if MC>=11502
        //$$ super.tick()
        //#else
        super.updateScreen()
        //#endif
    }

    open fun onScreenClose() {
        //#if MC>=11502
        //$$ super.onClose()
        //#else
        super.onGuiClosed()
        //#endif
    }

    open fun onDrawBackground(tint: Int) {
        //#if MC>=11602
        //$$ super.renderBackground(matrixStack, tint)
        //#elseif MC>=11502
        //$$ super.renderBackground(tint)
        //#else
        super.drawWorldBackground(tint)
        //#endif
    }

    companion object {
        @JvmStatic
        val currentScreen: MCScreen?
            get() = UMinecraft.getMinecraft().currentScreen

        @JvmStatic
        fun displayScreen(screen: MCScreen?) {
            //#if FABRIC
            //$$ UMinecraft.getMinecraft().openScreen(screen)
            //#else
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            UMinecraft.getMinecraft().displayGuiScreen(screen)
            //#endif
        }
    }
}
