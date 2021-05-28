package gg.essential.universal.wrappers.message

import gg.essential.universal.UPacket
import gg.essential.universal.UMinecraft
import gg.essential.universal.wrappers.UPlayer

//#if MC>=11600
//$$ import gg.essential.universal.utils.MCIMutableText
//#endif

class UMessage {
    private lateinit var _chatMessage: UTextComponent
    val messageParts: MutableList<UTextComponent> = mutableListOf()

    val chatMessage: UTextComponent
        get() {
            parseMessage()
            return _chatMessage
        }

    val formattedText: String
        get() = chatMessage.formattedText

    val unformattedText: String
        get() = chatMessage.unformattedText

    var chatLineId: Int = -1
    var isRecursive: Boolean = false
    var isFormatted: Boolean = true

    constructor(component: UTextComponent) {
        if (component.siblings.isEmpty()) {
            messageParts.add(component)
        } else {
            component.siblings
                //#if MC>=11600
                //$$ .filterIsInstance<MCIMutableText>()
                //#endif
                .map { UTextComponent(it) }
                .forEach { messageParts.add(it) }
        }
    }

    constructor(vararg parts: Any) {
        parts.forEach(::addPart)
    }

    fun setTextComponent(index: Int, component: Any) = apply {
        if (component is String) {
            messageParts[index] = UTextComponent(component)
        } else {
            UTextComponent.from(component)?.also { messageParts[index] = it }
        }
    }

    fun addTextComponent(index: Int, component: Any) = apply {
        if (component is String) {
            messageParts.add(index, UTextComponent(component))
        } else {
            UTextComponent.from(component)?.also { messageParts.add(index, it) }
        }
    }

    fun addTextComponent(component: Any): UMessage = addTextComponent(messageParts.size, component)

    fun edit(vararg replacements: UMessage) {
        TODO()
    }

    fun chat() {
        parseMessage()

        if (!UPlayer.hasPlayer())
            return

        // TODO: expose this field in MC>=11602
        //#if MC<=11502
        if (chatLineId != -1) {
            UMinecraft.getChatGUI()?.printChatMessageWithOptionalDeletion(chatMessage, chatLineId)
            return
        }
        //#endif

        if (isRecursive) {
            UPacket.sendChatMessage(_chatMessage)
        } else {
            UPlayer.sendClientSideMessage(_chatMessage)
        }
    }

    fun actionBar() {
        parseMessage()

        if (!UPlayer.hasPlayer())
            return

        UPacket.sendActionBarMessage(_chatMessage)
    }

    private fun addPart(part: Any) {
        when (part) {
            is UTextComponent -> messageParts.add(part)
            is String -> messageParts.add(UTextComponent(part))
            else -> UTextComponent.from(part)?.also(::addPart)
        }
    }

    private fun parseMessage() {
        _chatMessage = UTextComponent("")
        messageParts.forEach { _chatMessage.appendSibling(it) }
    }
}
