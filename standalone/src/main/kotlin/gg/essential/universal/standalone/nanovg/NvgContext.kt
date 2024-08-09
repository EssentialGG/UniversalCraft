package gg.essential.universal.standalone.nanovg

import org.lwjgl.nanovg.NanoVGGL2
import org.lwjgl.nanovg.NanoVGGL3
import org.lwjgl.opengl.GL
import java.io.Closeable

/**
 * A NanoVG context.
 *
 * Must be created and used on a thread with an active OpenGL context.
 * Must be cleaned up via [close], otherwise native memory will be leaked.
 */
class NvgContext : Closeable {
    private val gl3 = GL.getCapabilities().OpenGL30
    var ctx: Long
        get() {
            if (field == 0L) {
                throw IllegalStateException("This NanoVG context has already been deleted!")
            }
            return field
        }
        private set

    init {
        val ctx = if (gl3) {
            NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS)
        } else {
            NanoVGGL2.nvgCreate(NanoVGGL2.NVG_ANTIALIAS)
        }
        if (ctx == 0L) {
            throw RuntimeException("Failed to create nvg context")
        }
        this.ctx = ctx
    }

    override fun close() {
        if (gl3) {
            NanoVGGL3.nvgDelete(ctx)
        } else {
            NanoVGGL2.nvgDelete(ctx)
        }
        ctx = 0
    }
}
