package gg.essential.universal.standalone

import gg.essential.universal.UGraphics
import gg.essential.universal.standalone.glfw.Glfw
import gg.essential.universal.standalone.glfw.GlfwWindow
import gg.essential.universal.standalone.glfw.runGlfw
import gg.essential.universal.standalone.render.DEBUG_GL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL43C
import org.lwjgl.opengl.GLUtil
import org.lwjgl.system.MemoryUtil
import java.io.PrintStream

/**
 * Initializes the standalone UniversalCraft framework and runs the given [main] function on the main dispatcher.
 * Once the [main] function returns, any remaining child jobs launched in its [CoroutineScope] are cancelled and the
 * framework is shut down once they have all completed.
 *
 * Takes control of the main thread and does not return until the coroutine completes and the framework has shut down.
 *
 * Must be called from the main thread!
 */
fun runUniversalCraft(
    title: String,
    width: Int,
    height: Int,
    resizable: Boolean = true,
    main: suspend CoroutineScope.(window: UCWindow) -> Unit,
) = runGlfw {
    GlfwWindow(title, width, height, resizable).use { glfwWindow ->
        GLFW.glfwMakeContextCurrent(MemoryUtil.NULL)
        withContext(Dispatchers.Main) {
            GLFW.glfwMakeContextCurrent(glfwWindow.glfwId)
            val caps = GL.createCapabilities()
            try {
                if (DEBUG_GL) {
                    GLUtil.setupDebugMessageCallback(object : PrintStream(System.err) {
                        override fun print(obj: Any?) {
                            super.print(obj)
                            Throwable().printStackTrace()
                        }
                    })
                    if (caps.OpenGL43) {
                        GL43C.glEnable(GL43C.GL_DEBUG_OUTPUT_SYNCHRONOUS)
                    }
                }

                UGraphics.init()

                coroutineScope mainScope@{
                    val ucWindow = withContext(Dispatchers.Glfw) { UCWindow(glfwWindow, this@mainScope) }
                    main(ucWindow)
                    coroutineContext.cancelChildren()
                }
            } finally {
                GLFW.glfwMakeContextCurrent(MemoryUtil.NULL)
            }
        }
    }
}
