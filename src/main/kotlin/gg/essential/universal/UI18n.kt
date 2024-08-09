package gg.essential.universal

//#if STANDALONE
//#else
import net.minecraft.client.resources.I18n
//#endif

object UI18n {
    fun i18n(key: String, vararg arguments: Any?): String {
        //#if STANDALONE
        //$$ TODO("i18n($key, $arguments)")
        //#else
        return I18n.format(key, *arguments)
        //#endif
    }
}
