package gg.essential.universal

import net.minecraft.client.gui.GuiScreen

//#if MC>=12106
//$$ import com.mojang.blaze3d.systems.RenderSystem
//#endif

//#if MC>=12000
//$$ import net.minecraft.client.gui.DrawContext
//#endif

//#if MC>=11502
//$$ import gg.essential.universal.UKeyboard.toInt
//$$ import gg.essential.universal.UKeyboard.toModifiers
//$$ import com.mojang.blaze3d.matrix.MatrixStack
//$$ import net.minecraft.util.text.ITextComponent
//#if MC<11900
//$$ import net.minecraft.util.text.TranslationTextComponent
//#endif
//#else
import org.lwjgl.input.Mouse
import java.io.IOException

//#endif

abstract class UScreen(
    val restoreCurrentGuiOnClose: Boolean = false,
    open var newGuiScale: Int = -1,
    open var unlocalizedName: String? = null
) :
//#if MC>=11900
//$$     Screen(Text.translatable(unlocalizedName ?: ""))
//#elseif MC>=11502
//$$     Screen(TranslationTextComponent(unlocalizedName ?: ""))
//#else
    GuiScreen()
//#endif
{
    @JvmOverloads
    constructor(
        restoreCurrentGuiOnClose: Boolean = false,
        newGuiScale: Int = -1,
    ) : this(restoreCurrentGuiOnClose, newGuiScale, null)

    private var guiScaleToRestore = -1
    private var restoringGuiScale = false
    private val screenToRestore: GuiScreen? = if (restoreCurrentGuiOnClose) currentScreen else null
    //#if MC>=12106
    //$$ // Background is now draw from the final `renderWithTooltip` method, before we ever get control, so we need
    //$$ // to suppress by default and can only allow during `onDrawScreen`.
    //$$ private var suppressBackground = true
    //#else
    private var suppressBackground = false
    //#endif

    //#if MC>=12106
    //$$ private val advancedDrawContext = AdvancedDrawContext()
    //#endif

    //#if MC>=12000
    //$$ private var drawContexts = mutableListOf<DrawContext>()
    //$$ private inline fun <R> withDrawContext(matrixStack: UMatrixStack, block: (DrawContext) -> R) {
    //#if MC>=12106
    //$$     val context = drawContexts.last()
    //$$     context.matrices.pushMatrix()
    //$$     matrixStack.to3x2Joml(context.matrices)
    //$$     block(context)
    //$$     context.matrices.popMatrix()
    //#else
    //$$     val client = this.client!!
    //$$     val context = drawContexts.lastOrNull()
    //$$         ?: DrawContext(client, client.bufferBuilders.entityVertexConsumers)
    //$$     context.matrices.push()
    //$$     val mc = context.matrices.peek()
    //$$     val uc = matrixStack.peek()
    //$$     mc.positionMatrix.set(uc.model)
    //$$     mc.normalMatrix.set(uc.normal)
    //$$     block(context)
        //#if MC>=12102
        //$$ context.draw()
        //#endif
    //$$     context.matrices.pop()
    //#endif
    //$$ }
    //#endif

    //#if MC>=11502
    //$$ private var lastClick = 0L
    //$$ private var lastDraggedDx = -1.0
    //$$ private var lastDraggedDy = -1.0
    //$$ private var lastScrolledX = -1.0
    //$$ private var lastScrolledY = -1.0
    //$$ private var lastScrolledDX = 0.0
    //$$
    //$$ final override fun init() {
    //$$     updateGuiScale()
    //$$     initScreen(width, height)
    //$$ }
    //$$
    //#if MC>=11900
    //$$ override fun getTitle(): Text = Text.translatable(unlocalizedName ?: "")
    //#else
    //$$ override fun getTitle(): ITextComponent = TranslationTextComponent(unlocalizedName ?: "")
    //#endif
    //$$
    //#if MC>=12000
    //$$ final override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
    //$$     drawContexts.add(context)
        //#if MC>=12106
        //$$ advancedDrawContext.nextFrame()
        //$$ advancedDrawContext.drawImmediate(context) { stack ->
        //$$     suppressBackground = false
        //$$     onDrawScreenCompat(stack, mouseX, mouseY, delta)
        //$$     suppressBackground = true
        //$$ }
        //#else
        //$$ onDrawScreenCompat(UMatrixStack(context.matrices), mouseX, mouseY, delta)
        //#endif
    //$$     drawContexts.removeLast()
    //#elseif MC>=11602
    //$$ final override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
    //$$     onDrawScreenCompat(UMatrixStack(matrixStack), mouseX, mouseY, partialTicks)
    //#else
    //$$ final override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
    //$$     onDrawScreenCompat(UMatrixStack(), mouseX, mouseY, partialTicks)
    //#endif
    //$$ }
    //$$
    //$$ final override fun keyPressed(keyCode: Int, scanCode: Int, modifierCode: Int): Boolean {
    //$$     onKeyPressed(keyCode, 0.toChar(), modifierCode.toModifiers())
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun keyReleased(keyCode: Int, scanCode: Int, modifierCode: Int): Boolean {
    //$$     onKeyReleased(keyCode, 0.toChar(), modifierCode.toModifiers())
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun charTyped(char: Char, modifierCode: Int): Boolean {
    //$$     onKeyPressed(0, char, modifierCode.toModifiers())
    //$$     return false
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
    //#if MC>=12002
    //$$ override fun mouseScrolled(mouseX: Double, mouseY: Double, horizontalAmount: Double, delta: Double): Boolean {
    //$$     lastScrolledDX = horizontalAmount
    //#else
    //$$ final override fun mouseScrolled(mouseX: Double, mouseY: Double, delta: Double): Boolean {
    //#endif
    //$$     lastScrolledX = mouseX
    //$$     lastScrolledY = mouseY
    //$$     onMouseScrolled(delta)
    //$$     return false
    //$$ }
    //$$
    //$$ final override fun tick(): Unit = onTick()
    //$$
    //$$ final override fun onClose() {
        //#if MC>=12106
        //$$ advancedDrawContext.close()
        //#endif
    //$$     onScreenClose()
    //$$     restoreGuiScale()
    //$$ }
    //$$
    //#if MC>=12000
    //#if MC>=12002
    //$$ private var lastBackgroundMouseX = 0
    //$$ private var lastBackgroundMouseY = 0
    //$$ private var lastBackgroundDelta = 0f
    //$$ final override fun renderBackground(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
    //$$     lastBackgroundMouseX = mouseX
    //$$     lastBackgroundMouseY = mouseY
    //$$     lastBackgroundDelta = delta
    //$$     if (suppressBackground) return
    //#else
    //$$ final override fun renderBackground(context: DrawContext) {
    //#endif
    //$$     drawContexts.add(context)
    //$$     onDrawBackgroundCompat(UMatrixStack(context.matrices), 0)
    //$$     drawContexts.removeLast()
    //$$ }
    //#elseif MC>=11904
    //$$ final override fun renderBackground(matrixStack: MatrixStack) {
    //$$     onDrawBackgroundCompat(UMatrixStack(matrixStack), 0)
    //$$ }
    //#elseif MC>=11602
    //$$ final override fun renderBackground(matrixStack: MatrixStack, vOffset: Int) {
    //$$     onDrawBackgroundCompat(UMatrixStack(matrixStack), vOffset)
    //$$ }
    //#else
    //$$ final override fun renderBackground(vOffset: Int) {
    //$$     onDrawBackgroundCompat(UMatrixStack(), vOffset)
    //$$ }
    //#endif
    //#else
    final override fun initGui() {
        updateGuiScale()
        initScreen(width, height)
    }

    final override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        onDrawScreenCompat(UMatrixStack(), mouseX, mouseY, partialTicks)
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
        restoreGuiScale()
    }

    final override fun drawWorldBackground(tint: Int) {
        onDrawBackgroundCompat(UMatrixStack(), tint)
    }
    //#endif

    constructor(restoreCurrentGuiOnClose: Boolean, newGuiScale: GuiScale) : this(
        restoreCurrentGuiOnClose,
        newGuiScale.ordinal
    )

    fun restorePreviousScreen() {
        displayScreen(screenToRestore)
    }

    open fun updateGuiScale() {
        if (newGuiScale != -1 && !restoringGuiScale) {
            if (guiScaleToRestore == -1)
                guiScaleToRestore = UMinecraft.guiScale
            UMinecraft.guiScale = newGuiScale
            width = UResolution.scaledWidth
            height = UResolution.scaledHeight
        }
    }

    private fun restoreGuiScale() {
        if (guiScaleToRestore != -1) {
            // This flag is necessary since on 1.20.5 setting the gui scale causes the screen's resize
            // method to be called due to an option change callback. This resize causes the screen to reinitialize,
            // which calls updateGuiScale. To prevent that method for changing the gui scale back,
            // we suppress its behavior with a flag.
            restoringGuiScale = true
            UMinecraft.guiScale = guiScaleToRestore
            restoringGuiScale = false
            guiScaleToRestore = -1
        }
    }

    open fun initScreen(width: Int, height: Int) {
        //#if MC>=11502
        //$$ super.init()
        //#else
        super.initGui()
        //#endif
    }

    open fun onDrawScreen(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        //#if MC<12106
        suppressBackground = true
        //#endif
        //#if MC>=12000
        //$$ withDrawContext(matrixStack) { drawContext ->
        //$$     super.render(drawContext, mouseX, mouseY, partialTicks)
        //$$ }
        //#elseif MC>=11602
        //$$ super.render(matrixStack.toMC(), mouseX, mouseY, partialTicks)
        //#else
        matrixStack.runWithGlobalState {
            //#if MC>=11502
            //$$ super.render(mouseX, mouseY, partialTicks)
            //#else
            super.drawScreen(mouseX, mouseY, partialTicks)
            //#endif
        }
        //#endif
        //#if MC<12106
        suppressBackground = false
        //#endif
    }

    @Deprecated(
        UMatrixStack.Compat.DEPRECATED,
        ReplaceWith("onDrawScreen(matrixStack, mouseX, mouseY, partialTicks)")
    )
    open fun onDrawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        onDrawScreen(UMatrixStack.Compat.get(), mouseX, mouseY, partialTicks)
    }

    // Calls the deprecated method (for backwards compat) which then calls the new method (read the deprecation message)
    private fun onDrawScreenCompat(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) = UMatrixStack.Compat.runLegacyMethod(matrixStack) {
        @Suppress("DEPRECATION")
        onDrawScreen(mouseX, mouseY, partialTicks)
    }

    open fun onKeyPressed(keyCode: Int, typedChar: Char, modifiers: UKeyboard.Modifiers?) {
        //#if MC>=11502
        //$$ if (keyCode != 0) {
        //$$     super.keyPressed(keyCode, 0, modifiers.toInt())
        //$$ }
        //$$ if (typedChar != 0.toChar()) {
        //$$     super.charTyped(typedChar, modifiers.toInt())
        //$$ }
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
        //$$ if (keyCode != 0) {
        //$$     super.keyReleased(keyCode, 0, modifiers.toInt())
        //$$ }
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
        //#if MC>=12002
        //$$ super.mouseScrolled(lastScrolledX, lastScrolledY, lastScrolledDX, delta)
        //#elseif MC>=11502
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

    open fun onDrawBackground(matrixStack: UMatrixStack, tint: Int) {
        //#if MC>=12000
        //$$ withDrawContext(matrixStack) { drawContext ->
            //#if MC>=12106
            //$$ drawContext.createNewRootLayer()
            //#endif
            //#if MC>=12106
            //$$ val orgProjectionMatrixBuffer = RenderSystem.getProjectionMatrixBuffer()
            //$$ val orgProjectionType = RenderSystem.getProjectionType()
            //$$ super.renderBackground(drawContext, lastBackgroundMouseX, lastBackgroundMouseY, lastBackgroundDelta)
            //$$ @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            //$$ RenderSystem.setProjectionMatrix(orgProjectionMatrixBuffer, orgProjectionType)
            //#elseif MC>=12002
            //$$ super.renderBackground(drawContext, lastBackgroundMouseX, lastBackgroundMouseY, lastBackgroundDelta)
            //#else
            //$$ super.renderBackground(drawContext)
            //#endif
            //#if MC>=12106
            //$$ drawContext.createNewRootLayer()
            //#endif
        //$$ }
        //#elseif MC>=11904
        //$$ super.renderBackground(matrixStack.toMC())
        //#elseif MC>=11602
        //$$ super.renderBackground(matrixStack.toMC(), tint)
        //#else
        matrixStack.runWithGlobalState {
            //#if MC>=11502
            //$$ super.renderBackground(tint)
            //#else
            super.drawWorldBackground(tint)
            //#endif
        }
        //#endif
    }

    @Deprecated(
        UMatrixStack.Compat.DEPRECATED,
        ReplaceWith("onDrawBackground(matrixStack, tint)")
    )
    open fun onDrawBackground(tint: Int) {
        onDrawBackground(UMatrixStack.Compat.get(), tint)
    }

    // Calls the deprecated method (for backwards compat) which then calls the new method (read the deprecation message)
    fun onDrawBackgroundCompat(matrixStack: UMatrixStack, tint: Int) = UMatrixStack.Compat.runLegacyMethod(matrixStack) {
        @Suppress("DEPRECATION")
        onDrawBackground(tint)
    }

    companion object {
        @JvmStatic
        val currentScreen: GuiScreen?
            get() = UMinecraft.getMinecraft().currentScreen

        @JvmStatic
        fun displayScreen(screen: GuiScreen?) {
            //#if MC<11200
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            //#endif
            UMinecraft.getMinecraft().displayGuiScreen(screen)
        }
    }
}
