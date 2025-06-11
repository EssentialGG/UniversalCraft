package gg.essential.universal

import com.mojang.blaze3d.systems.ProjectionType
import com.mojang.blaze3d.systems.RenderSystem
import gg.essential.universal.utils.TemporaryTextureAllocator
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.ProjectionMatrix2
import net.minecraft.client.texture.AbstractTexture
import net.minecraft.util.Identifier

internal object AdvancedDrawContext {
    private var allocatedProjectionMatrix: ProjectionMatrix2? = null

    private val textureAllocator = TemporaryTextureAllocator {
        allocatedProjectionMatrix?.close()
        allocatedProjectionMatrix = null
    }

    fun drawImmediate(context: DrawContext, block: (UMatrixStack) -> Unit) {
        val scaleFactor = UResolution.scaleFactor.toFloat()
        val width = UResolution.viewportWidth
        val height = UResolution.viewportHeight

        val texture = textureAllocator.allocate(width, height)

        var projectionMatrix = allocatedProjectionMatrix
        if (projectionMatrix == null) {
            projectionMatrix = ProjectionMatrix2("pre-rendered screen", 1000f, 21000f, true)
            allocatedProjectionMatrix = projectionMatrix
        }
        RenderSystem.setProjectionMatrix(
            projectionMatrix.set(width.toFloat() / scaleFactor, height.toFloat() / scaleFactor),
            ProjectionType.ORTHOGRAPHIC,
        )

        val orgOutputColorTextureOverride = RenderSystem.outputColorTextureOverride
        val orgOutputDepthTextureOverride = RenderSystem.outputDepthTextureOverride
        RenderSystem.outputColorTextureOverride = texture.textureView
        RenderSystem.outputDepthTextureOverride = texture.depthTextureView

        val matrixStack = UMatrixStack()
        matrixStack.translate(0f, 0f, -10000f)
        block(matrixStack)

        RenderSystem.outputColorTextureOverride = orgOutputColorTextureOverride
        RenderSystem.outputDepthTextureOverride = orgOutputDepthTextureOverride

        draw(context, texture)
    }

    fun draw(context: DrawContext, texture: TemporaryTextureAllocator.TextureAllocation) {
        val width = texture.width
        val height = texture.height
        val scaleFactor = UResolution.scaleFactor.toFloat()

        val textureManager = MinecraftClient.getInstance().textureManager
        val identifier = Identifier.of("universalcraft", "__tmp_texture__")
        textureManager.registerTexture(identifier, object : AbstractTexture() {
            init { glTextureView = texture.textureView }
            override fun close() {} // we don't want the later `destroyTexture` to close our texture
        })

        context.matrices.pushMatrix()
        context.matrices.scale(1/scaleFactor, 1/scaleFactor) // drawTexture only accepts `int`s
        context.drawTexture(
            RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA,
            identifier,
            // x, y
            0, 0,
            // u, v
            0f, height.toFloat(),
            // width, height
            width, height,
            // uWidth, vHeight
            width, -height,
            // textureWidth, textureHeight
            width, height,
        )
        context.matrices.popMatrix()

        textureManager.destroyTexture(identifier)
    }
}
