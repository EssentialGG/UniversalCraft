package gg.essential.universal

import net.minecraft.client.resources.I18n

object UI18n {
    fun i18n(key: String, vararg arguments: Any?): String {
        return I18n.format(key, *arguments)
    }
}
