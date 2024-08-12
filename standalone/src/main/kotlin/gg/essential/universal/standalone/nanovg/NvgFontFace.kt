package gg.essential.universal.standalone.nanovg

import org.lwjgl.nanovg.NanoVG.*
import org.lwjgl.system.MemoryUtil

/**
 * Parses and registers a new font face with the given NanoVG context.
 *
 * The number of fallback fonts is limited to a fairly small number by NanoVG.
 *
 * Note that this new font will occupy memory in the context even if this [NvgFontFace] object is garbage collected.
 *
 * @see NvgFont
 */
class NvgFontFace(val ctx: NvgContext, fontData: ByteArray, vararg fallbacks: NvgFontFace) {
    private var fallbackFonts: List<NvgFontFace> = emptyList()

    val id: Int = nvgCreateFontMem(
        ctx.ctx,
        "",
        MemoryUtil.memAlloc(fontData.size).apply {
            put(fontData)
            rewind()
        },
        true,
    )

    init {
        setFallbacks(fallbacks.toList())
    }

    fun setFallbacks(fallbacks: List<NvgFontFace>) {
        check(fallbacks.size < 20) { "NanoVG supports at most 20 fallback fonts." }

        fallbackFonts = fallbacks.toList()

        nvgResetFallbackFontsId(ctx.ctx, id)
        for (fallbackFont in fallbackFonts) {
            nvgAddFallbackFontId(ctx.ctx, id, fallbackFont.id)
        }
    }

    fun addFallback(fallback: NvgFontFace) {
        // We must call nvgResetFallbackFontsId to clear the glyph cache, just nvgAddFallbackFontId is not enough
        setFallbacks(fallbackFonts + fallback)
    }
}
