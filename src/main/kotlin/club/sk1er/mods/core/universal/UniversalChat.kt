package club.sk1er.mods.core.universal

import club.sk1er.mods.core.universal.wrappers.UniversalPlayer
import club.sk1er.mods.core.universal.wrappers.message.UniversalMessage
import club.sk1er.mods.core.universal.wrappers.message.UniversalTextComponent
import java.util.regex.Pattern

object UniversalChat {
    private val amperstandPattern = Pattern.compile("(?<!\\\\)&(?![^0-9a-fklmnor]|$)")

    /**
     * Prints a message to chat. Accepts a String, UniversalMessage, UniversalTextComponent,
     * or any version-specific text component. If the object is of an unrecognized type,
     * it's toString() method will be called.
     *
     * This function is client side.
     */
    @JvmStatic
    fun chat(obj: Any) {
        if (obj is String || obj is UniversalTextComponent) {
            UniversalMessage(obj).chat()
        } else {
            val component = UniversalTextComponent.from(obj)
            if (component.isPresent) {
                component.get().chat()
            } else {
                UniversalMessage(obj.toString()).chat()
            }
        }
    }

    /**
     * Prints a message to the action bar. Accepts a String, UniversalMessage,
     * UniversalTextComponent, or any version-specific text component. If the
     * object is of an unrecognized type, it's toString() method will be called.
     *
     * This function is client side.
     */
    @JvmStatic
    fun actionBar(obj: Any) {
        if (obj is String || obj is UniversalTextComponent) {
            UniversalMessage(obj).actionBar()
        } else {
            val component = UniversalTextComponent.from(obj)
            if (component.isPresent) {
                component.get().actionBar()
            } else {
                UniversalMessage(obj.toString()).actionBar()
            }
        }
    }

    /**
     * Sends a String to the server.
     */
    @JvmStatic
    fun say(text: String) {
        UniversalPlayer.getPlayer().sendChatMessage(text)
    }

    /**
     * Replaces ampersand color codes with section symbol color codes
     */
    @JvmStatic
    fun addColor(message: String): String {
        return amperstandPattern.matcher(message).replaceAll("\u00a7")
    }
}
