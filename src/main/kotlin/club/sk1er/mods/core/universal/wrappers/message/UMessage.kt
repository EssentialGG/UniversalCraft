package club.sk1er.mods.core.universal.wrappers.message

import club.sk1er.mods.core.universal.UPacket
import club.sk1er.mods.core.universal.UMinecraft
import club.sk1er.mods.core.universal.wrappers.UPlayer

//#if FABRIC
//$$ import club.sk1er.mods.core.universal.utils.MCITextComponent
//#endif

class UMessage {
    private lateinit var _chatMessage: UTextComponent
    val messageParts = mutableListOf<UTextComponent>()

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
    var isRecursive = false
    var isFormatted = true

    constructor(component: UTextComponent) {
        if (component.siblings.isEmpty()) {
            messageParts.add(component)
        } else {
            //#if FORGE
            component.siblings.map(::UTextComponent).forEach { messageParts.add(it) }
            //#else
            //$$ component.siblings
            //$$         .map { UTextComponent(it as MCITextComponent) }
            //$$         .forEach { messageParts.add(it) }
            //#endif
        }
    }

    constructor(vararg parts: Any) {
        parts.forEach(::addPart)
    }

    fun setTextComponent(index: Int, component: Any) = apply {
        if (component is String) {
            messageParts[index] = UTextComponent(component)
        } else {
            UTextComponent.from(component).ifPresent { messageParts[index] = it }
        }
    }

    fun addTextComponent(index: Int, component: Any) = apply {
        if (component is String) {
            messageParts.add(index, UTextComponent(component))
        } else {
            UTextComponent.from(component).ifPresent { messageParts.add(index, it) }
        }
    }

    fun addTextComponent(component: Any) = addTextComponent(messageParts.size, component)

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
            else -> UTextComponent.from(part).ifPresent(::addPart)
        }
    }

    private fun parseMessage() {
        _chatMessage = UTextComponent("")
        messageParts.forEach { _chatMessage.appendSibling(it) }
    }
}
