package gg.essential.universal.standalone.glfw

import gg.essential.universal.UDesktop
import gg.essential.universal.standalone.render.DEBUG_GL
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.stb.STBIWriteCallback
import org.lwjgl.stb.STBImageWrite
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.io.Closeable
import java.io.IOException
import java.nio.ByteBuffer

/**
 * Creates and manages a GLFW window.
 * GLFW must already have been initialized.
 *
 * Unless noted otherwise, all methods must be called from the main thread!
 */
class GlfwWindow(
    private val title: String,
    width: Int,
    height: Int,
    resizable: Boolean = true,
) : Closeable {

    val glfwId: Long = run {
        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, if (resizable) GLFW.GLFW_TRUE else GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_DEBUG, if (DEBUG_GL) GLFW.GLFW_TRUE else GLFW.GLFW_FALSE)

        // Ideally we'd have a forward-compatible core profile (available from OpenGL 3.2) so we can't accidentally use
        // deprecated stuff, and so we can make full use of tools like RenderDoc
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE)

        GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
            .takeUnless { it == MemoryUtil.NULL }
            ?.let { return@run it }

        // If we can't get one, we can still make great use of any kind of OpenGL 3.0+
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_ANY_PROFILE)

        GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
            .takeUnless { it == MemoryUtil.NULL }
            ?.let { return@run it }

        // If we can't even get that, we can mostly still work so long as we get at least OpenGL 2.0 (released in 2004)
        // with a few extensions
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 2)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_ANY_PROFILE)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_FALSE)

        GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
            .takeUnless { it == MemoryUtil.NULL }
            ?.let { return@run it }

        // Failed to create any context, fetch the error and throw
        val message = MemoryStack.stackPush().use { stack ->
            val pointer = stack.mallocPointer(1)
            val error = GLFW.glfwGetError(pointer)
            if (error != GLFW.GLFW_NO_ERROR) {
                "${MemoryUtil.memUTF8Safe(pointer.get(0))} (code $error)"
            } else {
                "unknown error"
            }
        }
        throw RuntimeException("Failed to create the GLFW window: $message")
    }

    init {
        GLFW.glfwMakeContextCurrent(glfwId)
        GLFW.glfwSwapInterval(1)

        GL.createCapabilities()

        UDesktop.glfwWindow = this
    }

    /** Must be called from the thread on which this window's OpenGL context is current. */
    fun prepareFrame(width: Int, height: Int) {
        glViewport(0, 0, width, height)
        glClearColor(0f, 0f, 0f, 1f)
        glClearDepth(1.0)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    /** Must be called from the thread on which this window's OpenGL context is current. */
    fun capturePng(width: Int, height: Int): ByteArray {
        val buffer = MemoryUtil.memAlloc(width * height * 4)
        try {
            glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
            return pixelsToPng(width, height, buffer)
        } finally {
            MemoryUtil.memFree(buffer)
        }
    }

    private fun pixelsToPng(width: Int, height: Int, buffer: ByteBuffer): ByteArray {
        var output = ByteArray(0)

        val writeCallback =
            STBIWriteCallback.create { _, data, size ->
                val byteBuffer = STBIWriteCallback.getData(data, size)
                output = output.copyOf(output.size + size)
                byteBuffer.get(output, output.size - size, size)
            }
        try {
            STBImageWrite.stbi_flip_vertically_on_write(true)
            val success =
                STBImageWrite.stbi_write_png_to_func(writeCallback, 0L, width, height, 4, buffer, 0)
            STBImageWrite.stbi_flip_vertically_on_write(false)
            if (!success) {
                throw IOException("Failed to encode image")
            }
            return output
        } finally {
            writeCallback.free()
        }
    }

    override fun close() {
        GLFW.glfwDestroyWindow(glfwId)
    }
}