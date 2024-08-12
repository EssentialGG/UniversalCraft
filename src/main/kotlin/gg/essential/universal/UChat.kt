package gg.essential.universal

//#if !STANDALONE
import gg.essential.universal.wrappers.UPlayer
import gg.essential.universal.wrappers.message.UMessage
import gg.essential.universal.wrappers.message.UTextComponent
//#endif
import java.util.regex.Pattern

object UChat {
    private val ampersandPattern = Pattern.compile("(?<!\\\\)&(?![^0-9a-fklmnor]|$)")

    /**
     * Prints a message to chat. Accepts a String, UMessage, UTextComponent,
     * or any version-specific text component. If the object is of an unrecognized type,
     * it's toString() method will be called.
     *
     * This function is client side.
     */
    @JvmStatic
    fun chat(obj: Any) {
        //#if STANDALONE
        //$$ println(obj)
        //#else
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
        //#endif
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
        //#if STANDALONE
        //$$ throw UnsupportedOperationException("actionBar($obj)")
        //#else
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
        //#if STANDALONE
        //$$ throw UnsupportedOperationException("say($text)")
        //#else
        //#if MC>=11903
        //$$ UPlayer.getPlayer()!!.networkHandler.sendChatMessage(text)
        //#elseif MC>=11901
        //$$ UPlayer.getPlayer()!!.sendChatMessage(text, null)
        //#else
        UPlayer.getPlayer()!!.sendChatMessage(text)
        //#endif
        //#endif
    }

    /**
     * Replaces ampersand color codes with section symbol color codes
     */
    @JvmStatic
    fun addColor(message: String): String {
        return ampersandPattern.matcher(message).replaceAll("\u00a7")
    }
}
