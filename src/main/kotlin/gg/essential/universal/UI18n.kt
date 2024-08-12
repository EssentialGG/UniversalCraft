package gg.essential.universal

//#if STANDALONE
//$$ import java.util.ServiceLoader
//$$ import java.util.IllegalFormatException
//#else
import net.minecraft.client.resources.I18n
//#endif

object UI18n {
    fun i18n(key: String, vararg arguments: Any?): String {
        //#if STANDALONE
        //$$ return theProvider.i18n(key, *arguments)
        //#else
        return I18n.format(key, *arguments)
        //#endif
    }

    //#if STANDALONE
    //$$ private val theProvider = ServiceLoader.load(Provider::class.java).firstOrNull() ?: FallbackProvider
    //$$ interface Provider {
    //$$     fun i18n(key: String, vararg arguments: Any?): String
    //$$ }
    //$$ private object FallbackProvider : Provider {
    //$$     override fun i18n(key: String, vararg arguments: Any?): String = try {
    //$$         String.format(key, *arguments)
    //$$     } catch (e: IllegalFormatException) {
    //$$         e.printStackTrace()
    //$$         "[format error $key]"
    //$$     }
    //$$ }
    //#endif
}
