package gg.essential.universal.wrappers.message

import gg.essential.universal.UMinecraft
import gg.essential.universal.UPacket
import gg.essential.universal.utils.MCITextComponent
import gg.essential.universal.wrappers.UPlayer
import net.minecraft.client.gui.GuiNewChat
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.util.concurrent.ThreadLocalRandom

//#if MC>=11600
//$$ import net.minecraft.util.text.IFormattableTextComponent
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
                //$$ .filterIsInstance<IFormattableTextComponent>()
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

    /**
     * Must be called to be able to edit later
     */
    fun mutable() = apply {
        chatLineId = ThreadLocalRandom.current().nextInt()
    }

    fun edit(vararg replacements: Any) {
        if (chatLineId == -1) throw IllegalStateException("This message is not mutable!")
        messageParts.clear()
        replacements.forEach(::addPart)
        chat()
    }

    fun chat() {
        parseMessage()

        if (!UPlayer.hasPlayer())
            return

        if (chatLineId != -1) {
            printChatMessageWithOptionalDeletion(chatMessage, chatLineId)
            return
        }

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

private val printChatMessageWithOptionalDeletion: MethodHandle? = try {
    val method = GuiNewChat::class.java.declaredMethods.find { method ->
        method.parameterTypes.run {
            size == 2 &&
                    get(0) == MCITextComponent::class.java &&
                    get(1) == Int::class.java
        }
    } ?: throw NoSuchMethodException(
        "Could not find method to edit chat messages. " +
        "No method with parameters (${MCITextComponent::class.java.name}, int) in ${GuiNewChat::class.java.name}."
    )
    method.isAccessible = true
    MethodHandles.lookup().unreflect(method)
} catch (e: Throwable) {
    e.printStackTrace()
    null
}

internal fun printChatMessageWithOptionalDeletion(textComponent: UTextComponent, lineID: Int) {
    // The `apply` wrapper appears to be necessary when using the IR compiler backend for it to generate the return type
    // as `V`, otherwise it'll infer `Ljava/lang/Object;` which does not match our target method.
    printChatMessageWithOptionalDeletion?.apply { invokeExact(UMinecraft.getChatGUI(), textComponent as MCITextComponent, lineID) }
}