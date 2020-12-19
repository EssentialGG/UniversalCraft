package club.sk1er.mods.core.universal.wrappers.message

import club.sk1er.mods.core.universal.UniversalPacket
import club.sk1er.mods.core.universal.wrappers.UniversalPlayer
import java.util.function.Function

class UniversalMessage {
    private lateinit var _chatMessage: UniversalTextComponent
    val messageParts = mutableListOf<UniversalTextComponent>()

    val chatMessage: UniversalTextComponent
        get() {
            parseMessage()
            return _chatMessage
        }

    val formattedText: String
        get() = chatMessage.formattedText

    val unformattedText: String
        get() = chatMessage.unformattedText

    var chatLineId: Int = -1
    var isRecursive = false
    var isFormatted = true

    constructor(component: UniversalTextComponent) {
        if (component.siblings.isEmpty()) {
            messageParts.add(component)
        } else {
            //#if FORGE
            component.siblings.map(::UniversalTextComponent).forEach { messageParts.add(it) }
            //#else
            //$$ component.siblings
            //$$         .map { UniversalTextComponent(it as MutableText) }
            //$$         .forEach { messageParts.add(it) }
            //#endif
        }
    }

    constructor(vararg parts: Any) {
        parts.forEach(::addPart)
    }

    fun setTextComponent(index: Int, component: Any) = apply {
        if (component is String) {
            messageParts[index] = UniversalTextComponent(component)
        } else {
            UniversalTextComponent.from(component).ifPresent { messageParts[index] = it }
        }
    }

    fun addTextComponent(index: Int, component: Any) = apply {
        if (component is String) {
            messageParts.add(index, UniversalTextComponent(component))
        } else {
            UniversalTextComponent.from(component).ifPresent { messageParts.add(index, it) }
        }
    }

    fun addTextComponent(component: Any) = addTextComponent(messageParts.size, component)

    fun edit(vararg replacements: UniversalMessage) {
        TODO()
    }

    fun chat() {
        parseMessage()

        if (!UniversalPlayer.hasPlayer())
            return

        // TODO: expose this field in MC>=11602
        //#if MC<11602
        //$$ if (chatLineId != -1) {
        //$$     UniversalMinecraft.getChatGUI()?.printChatMessageWithOptionalDeletion(chatMessage, chatLineId)
        //$$     return
        //$$ }
        //#endif

        if (isRecursive) {
            UniversalPacket.sendChatMessage(_chatMessage)
        } else {
            UniversalPlayer.sendClientSideMessage(_chatMessage)
        }
    }

    fun actionBar() {
        parseMessage()

        if (!UniversalPlayer.hasPlayer())
            return

        UniversalPacket.sendActionBarMessage(_chatMessage)
    }

    private fun addPart(part: Any) {
        when (part) {
            is UniversalTextComponent -> messageParts.add(part)
            is String -> messageParts.add(UniversalTextComponent(part))
            else -> UniversalTextComponent.from(part).ifPresent(::addPart)
        }
    }

    private fun parseMessage() {
        _chatMessage = UniversalTextComponent("")
        messageParts.forEach { _chatMessage.appendSibling(it) }
    }
}
