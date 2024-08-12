package gg.essential.universal.standalone

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.internal.MainDispatcherFactory
import java.io.Closeable
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

/**
 * A coroutine dispatcher for use as [Dispatchers.Main] backed by a plain single-threaded executor service.
 */
internal object UCMainDispatcher : MainCoroutineDispatcher(), Closeable {
    private val threadGroup = ThreadGroup("Main")
    private val executorService = Executors.newSingleThreadExecutor {
        Thread(threadGroup, it, "Main").apply { isDaemon = true }
    }

    override val immediate: MainCoroutineDispatcher
        get() = Immediate

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        executorService.execute(block)
    }

    override fun close() {
        executorService.shutdown()
    }

    object Immediate : MainCoroutineDispatcher() {
        override val immediate: MainCoroutineDispatcher
            get() = this

        override fun dispatch(context: CoroutineContext, block: Runnable) {
            executorService.execute(block)
        }

        override fun isDispatchNeeded(context: CoroutineContext): Boolean =
            Thread.currentThread().threadGroup != threadGroup
    }
}

@OptIn(InternalCoroutinesApi::class)
internal class UCDispatcherFactory : MainDispatcherFactory {
    override val loadPriority: Int
        get() = 1000

    override fun createDispatcher(allFactories: List<MainDispatcherFactory>): MainCoroutineDispatcher =
        UCMainDispatcher
}