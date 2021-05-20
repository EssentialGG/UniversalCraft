package gg.essential.universal

import gg.essential.universal.wrappers.UPlayer
import gg.essential.universal.wrappers.message.UMessage
import gg.essential.universal.wrappers.message.UTextComponent
import java.util.regex.Pattern

object UChat {
    private val amperstandPattern = Pattern.compile("(?<!\\\\)&(?![^0-9a-fklmnor]|$)")

    /**
     * Prints a message to chat. Accepts a String, UMessage, UTextComponent,
     * or any version-specific text component. If the object is of an unrecognized type,
     * it's toString() method will be called.
     *
     * This function is client side.
     */
    @JvmStatic
    fun chat(obj: Any) {
        if (obj is String || obj is UTextComponent) {
            UMessage(obj).chat()
        } else {
            val component = UTextComponent.from(obj)
            if (component != null) {
                component.chat()
            } else {
                UMessage(obj.toString()).chat()
            }
        }
    }

    /**
     * Prints a message to the action bar. Accepts a String, UMessage,
     * UTextComponent, or any version-specific text component. If the
     * object is of an unrecognized type, it's toString() method will be called.
     *
     * This function is client side.
     */
    @JvmStatic
    fun actionBar(obj: Any) {
        //#if MC>=10809
        if (obj is String || obj is UTextComponent) {
            UMessage(obj).actionBar()
        } else {
            val component = UTextComponent.from(obj)
            if (component != null) {
                component.actionBar()
            } else {
                UMessage(obj.toString()).actionBar()
            }
        }
        //#endif
    }

    /**
     * Sends a String to the server.
     */
    @JvmStatic
    fun say(text: String) {
        UPlayer.getPlayer()!!.sendChatMessage(text)
    }

    /**
     * Replaces ampersand color codes with section symbol color codes
     */
    @JvmStatic
    fun addColor(message: String): String {
        return amperstandPattern.matcher(message).replaceAll("\u00a7")
    }
}
