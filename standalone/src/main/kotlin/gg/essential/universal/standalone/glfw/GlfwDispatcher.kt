package gg.essential.universal.standalone.glfw

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import java.io.Closeable
import java.util.concurrent.LinkedBlockingQueue
import kotlin.coroutines.CoroutineContext


/**
 * A coroutine dispatcher that is confined to the "Main Thread" as required by various GLFW functions.
 * This is not the same as [Dispatchers.Main] and meant primarily for running GLFW functions, not UI in general.
 *
 * Must be driven via [runGlfw] which will not return until this dispatcher is fully shut down.
 */
val Dispatchers.Glfw: MainCoroutineDispatcher
    get() = GlfwDispatcher

internal object GlfwDispatcher : MainCoroutineDispatcher(), Closeable {
    private val mainThread = Thread.currentThread()
    private val tasks = LinkedBlockingQueue<Runnable>()
    private var shuttingDown = false
    private var shutDown = false

    fun runTasks() {
        while (!shutDown) {
            val task = tasks.take()
            task.run()
        }
        while (true) {
            val task = tasks.poll() ?: return
            task.run()
        }
    }

    /** Gracefully shuts down this dispatcher. Must be called from its thread. */
    override fun close() {
        if (shuttingDown) {
            return
        }
        shuttingDown = true
        tasks.put { shutDown = true }
    }

    override val immediate: MainCoroutineDispatcher
        get() = Immediate

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (shuttingDown) {
            throw IllegalStateException("$this was shut down.")
        }
        tasks.put(block)
    }

    override fun toString(): String {
        return "Dispatchers.Glfw"
    }

    object Immediate : MainCoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) =
            GlfwDispatcher.dispatch(context, block)

        override fun isDispatchNeeded(context: CoroutineContext): Boolean =
            Thread.currentThread() != mainThread

        override val immediate: MainCoroutineDispatcher
            get() = this

        override fun toString(): String {
            return "Dispatchers.Glfw.immediate"
        }
    }
}
