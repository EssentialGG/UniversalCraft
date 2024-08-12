package gg.essential.universal.standalone.glfw

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback

/**
 * Initializes GLFW and runs the given block on [Dispatchers.Glfw].
 * Terminates GLFW once the given block returns.
 *
 * Must be called from the main thread!
 */
fun runGlfw(main: suspend CoroutineScope.() -> Unit) {
    // This may be required to use AWT together with GLFW on macOS
    System.setProperty("java.awt.headless", "true")

    GLFWErrorCallback.createPrint(System.err).set()

    check(GLFW.glfwInit()) { "Unable to initialize GLFW" }

    val scope = MainScope() + Dispatchers.Glfw
    val mainJob = scope.async {
        try {
            main()
        } finally {
            GlfwDispatcher.close()
        }
    }

    GlfwDispatcher.runTasks()

    GLFW.glfwTerminate()
    GLFW.glfwSetErrorCallback(null)?.free()

    runBlocking { mainJob.await() }
}
