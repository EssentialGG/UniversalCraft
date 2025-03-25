package gg.essential.universal.render

import gg.essential.universal.vertex.UBuiltBuffer
import gg.essential.universal.vertex.UBuiltBufferInternal

//#if STANDALONE
//#else
//#if MC>=12105
//$$ import com.mojang.blaze3d.systems.RenderPass
//$$ import com.mojang.blaze3d.systems.RenderSystem
//$$ import com.mojang.blaze3d.textures.TextureFormat
//$$ import gg.essential.universal.vertex.UBufferBuilder
//$$ import net.minecraft.client.MinecraftClient
//$$ import net.minecraft.client.texture.GlTexture
//$$ import java.util.OptionalInt
//$$ import java.util.OptionalDouble
//#endif
//#endif

// Kept internal for now because I'm not yet sure how Mojang will evolve their RenderPass.
// At the moment we can't keep it open across draw calls because MC prevents uploading any new buffers while the render
// pass is active. Not sure if that's intentional.
// For older versions it would definitely be useful to issue multiple draw calls via one URenderPass object because
// we can cache the global GL state between calls and don't need to re-query and reset everything on every draw.
internal class URenderPass : AutoCloseable {
    //#if MC>=12105 && !STANDALONE
    //#else
    private val prevGlState = ManagedGlState.active()
    private val currGlState = ManagedGlState(prevGlState)
    private var currPipeline: URenderPipeline? = null
    //#endif

    override fun close() {
        //#if MC>=12105 && !STANDALONE
        //#else
        prevGlState.activate(currGlState, prevGlState, true)
        //#endif
    }

    fun draw(builtBuffer: UBuiltBuffer, pipeline: URenderPipeline, configure: (DrawCallBuilder) -> Unit) {
        val builder = DrawCallBuilderImpl(pipeline, builtBuffer as UBuiltBufferInternal)
        configure(builder)
        builder.submit()
    }

    internal inner class DrawCallBuilderImpl(
        private val pipeline: URenderPipeline,
        private val builtBuffer: UBuiltBufferInternal,
    ) : DrawCallBuilder {
        //#if MC>=12105 && !STANDALONE
        //$$ val mc: RenderPass
        //$$ init {
        //$$     val builtBuffer = builtBuffer.mc
        //$$     val vertexBuffer = pipeline.format.uploadImmediateVertexBuffer(builtBuffer.buffer)
        //$$     val sortedBuffer = builtBuffer.sortedBuffer
        //$$     val (indexBuffer, indexType) = if (sortedBuffer != null) {
        //$$         pipeline.format.uploadImmediateIndexBuffer(sortedBuffer) to builtBuffer.drawParameters.indexType()
        //$$     } else {
        //$$         val shapeIndexBuffer = RenderSystem.getSequentialBuffer(builtBuffer.drawParameters.mode())
        //$$         shapeIndexBuffer.getIndexBuffer(builtBuffer.drawParameters.indexCount()) to shapeIndexBuffer.indexType
        //$$     }
        //$$     mc = MinecraftClient.getInstance().framebuffer.let { fb ->
        //$$         RenderSystem.getDevice().createCommandEncoder().createRenderPass(
        //$$             fb.colorAttachment!!, OptionalInt.empty(), fb.depthAttachment, OptionalDouble.empty()
        //$$         )
        //$$     }
        //$$     mc.setVertexBuffer(0, vertexBuffer)
        //$$     mc.setIndexBuffer(indexBuffer, indexType)
        //$$ }
        //#else
        init {
            pipeline.bind()
        }
        //#endif

        private var scissor: ScissorState? = null

        override fun noScissor(): DrawCallBuilder = apply {
            scissor = ScissorState.DISABLED
        }

        override fun scissor(x: Int, y: Int, width: Int, height: Int) = apply {
            scissor = ScissorState(true, x, y, width, height)
        }

        override fun uniform(name: String, vararg values: Float): DrawCallBuilder = apply {
            //#if MC>=12105 && !STANDALONE
            //$$ mc.setUniform(name, *values)
            //#else
            pipeline.uniform(name, *values)
            //#endif
        }

        override fun uniform(name: String, vararg values: Int): DrawCallBuilder = apply {
            //#if MC>=12105 && !STANDALONE
            //$$ mc.setUniform(name, *values)
            //#else
            pipeline.uniform(name, *values)
            //#endif
        }

        override fun texture(name: String, textureGlId: Int): DrawCallBuilder = apply {
            //#if MC>=12105 && !STANDALONE
            //$$ mc.bindSampler(name, object : GlTexture("", TextureFormat.RGBA8, 0, 0, 0, textureGlId) {
            //$$     init {
            //$$         needsReinit = false
            //$$     }
            //$$ })
            //#else
            pipeline.texture(name, textureGlId)
            //#endif
        }

        override fun texture(index: Int, textureGlId: Int): DrawCallBuilder = apply {
            //#if MC>=12105 && !STANDALONE
            //$$ texture(pipeline.mcRenderPipeline.samplers[index], textureGlId)
            //#else
            pipeline.texture(index, textureGlId)
            //#endif
        }

        fun submit() {
            //#if MC>=12105 && !STANDALONE
            //$$ val scissor = scissor ?: ScissorState.active()
            //$$ if (scissor.enabled) {
            //$$     mc.enableScissor(scissor.x, scissor.y, scissor.width, scissor.height)
            //$$ } else {
            //$$     mc.disableScissor()
            //$$ }
            //$$
            //$$ pipeline.draw(mc, builtBuffer.mc)
            //$$
            //$$ mc.close()
            //#else
            if (currPipeline != pipeline) {
                currPipeline = pipeline
                pipeline.glState.activate(currGlState, prevGlState, false)
            }

            var prevScissor: ScissorState? = null
            if (scissor != null) {
                prevScissor = ScissorState.active()
                scissor?.activate()
            }

            pipeline.draw(builtBuffer)

            prevScissor?.activate()

            pipeline.unbind()
            //#endif
        }
    }
}
