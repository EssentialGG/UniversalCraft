package gg.essential.universal.standalone.nanovg

import gg.essential.universal.UMatrixStack
import gg.essential.universal.UResolution
import org.lwjgl.BufferUtils
import org.lwjgl.nanovg.NVGColor
import org.lwjgl.nanovg.NVGGlyphPosition
import org.lwjgl.nanovg.NanoVG.*
import org.lwjgl.opengl.GL20C
import org.lwjgl.system.MemoryStack
import java.awt.Color

/**
 * Provides methods for rendering a given [NvgFontFace] at a given size.
 */
class NvgFont(
    private val fontFace: NvgFontFace,
    private val fontSize: Float,
    baseLineHeightOverride: Float? = null,
    belowLineHeightOverride: Float? = null,
) {
    private val ctx: Long
        get() = fontFace.ctx.ctx

    private val baseLineHeight: Float
    private val belowLineHeight: Float

    init {
        nvgFontFaceId(ctx, fontFace.id)
        nvgFontSize(ctx, fontSize)

        val buf = BufferUtils.createFloatBuffer(4)
        nvgTextBounds(ctx, -1f, 0f, "", buf)
        val (_, y1, _, y2) = (0..3).map { buf.get(it) }
        baseLineHeight = baseLineHeightOverride ?: -y1
        belowLineHeight = belowLineHeightOverride ?: y2
    }

    @Suppress("UNUSED_PARAMETER") // signature intentionally matches Elementa's FontProvider
    fun drawString(
        matrixStack: UMatrixStack,
        string: String,
        color: Color,
        x: Float,
        y: Float,
        originalPointSize: Float,
        scale: Float,
        shadow: Boolean,
        shadowColor: Color?
    ) {
        val scissorEnabled = GL20C.glIsEnabled(GL20C.GL_SCISSOR_TEST)

        val scaleFactor = UResolution.scaleFactor.toFloat()
        nvgBeginFrame(
            ctx,
            UResolution.viewportWidth.toFloat() / scaleFactor,
            UResolution.viewportHeight.toFloat() / scaleFactor,
            scaleFactor,
        )

        nvgResetTransform(ctx)
        nvgResetScissor(ctx)

        if (scissorEnabled) {
            val box = IntArray(4)
            GL20C.glGetIntegerv(GL20C.GL_SCISSOR_BOX, box)
            var (sx, sy, sw, sh) = box
            sy = UResolution.viewportHeight - sy - sh
            nvgScissor(ctx, sx / scaleFactor, sy / scaleFactor, sw / scaleFactor, sh / scaleFactor)
        }

        with(UMatrixStack.GLOBAL_STACK.peek().model) {
            nvgTransform(ctx, m00, m10, m01, m11, m03, m13)
        }
        with(matrixStack.peek().model) {
            nvgTransform(ctx, m00, m10, m01, m11, m03, m13)
        }

        nvgFontFaceId(ctx, fontFace.id)
        nvgFontSize(ctx, fontSize * scale)

        if (shadow) {
            nvgFillColor(
                ctx,
                (shadowColor ?: Color(color.red shr 2, color.green shr 2, color.blue shr 2, 0xff)).toNVG()
            )
            nvgText(ctx, x + 1, y + 1 + baseLineHeight * scale, string)
        }
        nvgFillColor(ctx, color.toNVG())
        nvgText(ctx, x, y + baseLineHeight * scale, string)

        nvgEndFrame(ctx)

        if (scissorEnabled) {
            GL20C.glEnable(GL20C.GL_SCISSOR_TEST)
        }
    }

    fun getBaseLineHeight(): Float {
        return baseLineHeight
    }

    fun getBelowLineHeight(): Float {
        return belowLineHeight
    }

    fun getShadowHeight(): Float {
        return 1f
    }

    fun getStringWidth(string: String, pointSize: Float): Float {
        nvgFontFaceId(ctx, fontFace.id)
        nvgFontSize(ctx, fontSize * pointSize / 10)
        // Ideally we'd use the following, but nvgTextBounds is broken: https://github.com/memononen/nanovg/issues/636
        //   val buf = BufferUtils.createFloatBuffer(4)
        //   nvgTextBounds(ctx, 0f, 0f, string, buf)
        //   return buf.get(2)
        // so we'll have to work around it by using nvgTextGlyphPositions instead:
        var capacity = 256
        MemoryStack.stackPush().use { stack ->
            val buf = NVGGlyphPosition.malloc(capacity, stack)
            val count = nvgTextGlyphPositions(ctx, 0f, 0f, string, buf)
            if (count < capacity) {
                if (count == 0) {
                    return 0f
                }
                return buf.get(count - 1).maxx()
            }
        }
        while (true) {
            capacity *= 2
            val buf = NVGGlyphPosition.malloc(capacity)
            try {
                val count = nvgTextGlyphPositions(ctx, 0f, 0f, string, buf)
                if (count < capacity) {
                    return buf.get(count - 1).maxx()
                }
            } finally {
                buf.free()
            }
        }
    }

    private fun Color.toNVG() = NVGColor.create().also {
        nvgRGBA(red.toByte(), green.toByte(), blue.toByte(), alpha.toByte(), it)
    }
}
