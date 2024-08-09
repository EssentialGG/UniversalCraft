package gg.essential.example

import gg.essential.universal.standalone.nanovg.NvgContext
import gg.essential.universal.standalone.nanovg.NvgFontFace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.URL
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.io.path.readBytes
import kotlin.io.path.writeBytes

object Fonts {
    val nvgContext = NvgContext()

    val GEIST_REGULAR = NvgFontFace(nvgContext, Fonts::class.java.getResource("/fonts/Geist-Regular.otf")!!.readBytes())

    suspend fun loadFallback() {
        val font = try {
            val bytes = withContext(Dispatchers.IO) {
                val cachedFile = appBaseDir / "GoNotoCurrent-Regular.ttf"
                if (cachedFile.exists()) {
                    cachedFile.readBytes()
                } else {
                    val url = "https://github.com/satbyy/go-noto-universal/releases/download/v7.0/GoNotoCurrent-Regular.ttf"
                    URI(url).toURL().readBytes().also { bytes ->
                        cachedFile.writeBytes(bytes)
                    }
                }
            }
            NvgFontFace(nvgContext, bytes)
        } catch (e: Throwable) {
            e.printStackTrace()
            return
        }
        GEIST_REGULAR.addFallback(font)
    }
}
