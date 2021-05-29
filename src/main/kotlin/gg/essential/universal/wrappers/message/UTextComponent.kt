
package gg.essential.universal.wrappers.message

import gg.essential.universal.UChat
import gg.essential.universal.utils.*
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent
import net.minecraft.util.ChatStyle

//#if MC>=11602
//$$ import net.minecraft.util.IReorderingProcessor
//$$ import net.minecraft.util.text.IFormattableTextComponent
//$$ import net.minecraft.util.ICharacterConsumer
//$$ import net.minecraft.util.text.Color
//$$ // FIXME preprocessor bug: same inner class issue as in typealiases.kt
//#if FABRIC
//$$ import net.minecraft.text.StringVisitable.StyledVisitor
//$$ import net.minecraft.text.StringVisitable.Visitor
//#else
//$$ import net.minecraft.util.text.ITextProperties.IStyledTextAcceptor
//$$ import net.minecraft.util.text.ITextProperties.ITextAcceptor
//#endif
//$$ import java.util.Optional
//$$ import java.util.function.UnaryOperator
//#endif

//#if MC==11502
//$$ import java.util.stream.Stream
//$$ import java.util.function.Consumer
//#endif

@Suppress("MemberVisibilityCanBePrivate")
class UTextComponent : MCIMutableText {
    lateinit var component: MCIMutableText
        private set
    var text: String
        set(value) {
            field = value
            reInstance()
        }
    var formatted = true
        set(value) {
            field = value
            reInstance()
        }

    var clickAction: MCClickEventAction? = null
        set(value) {
            field = value
            reInstance()
        }
    var clickValue: String? = null
        set(value) {
            field = value
            reInstance()
        }
    var hoverAction: MCHoverEventAction? = null
        set(value) {
            field = value
            reInstance()
        }
    var hoverValue: Any? = null
        set(value) {
            field = value
            reInstance()
        }

    constructor(text: String) {
        this.text = text
        reInstance()
    }

    constructor(component: MCIMutableText) {
        this.component = component

        //#if MC>=11602
        //$$ text = formattedText
        //#else
        text = component.formattedText
        //#endif

        //#if MC>=11202
        //$$ val clickEvent = component.style.clickEvent
        //#else
        val clickEvent = component.chatStyle.chatClickEvent
        //#endif

        if (clickEvent != null) {
            clickAction = clickEvent.action
            clickValue = clickEvent.value
        }

        //#if MC>=11202
        //$$ val hoverEvent = component.style.hoverEvent
        //#else
        val hoverEvent = component.chatStyle.chatHoverEvent
        //#endif

        if (hoverEvent != null) {
            hoverAction = hoverEvent.action

            //#if MC>=11602
            //$$ hoverValue = hoverEvent.getParameter(hoverAction)
            //#else
            hoverValue = hoverEvent.value
            //#endif
        }
    }

    fun setClick(action: MCClickEventAction, value: String) = apply {
        clickAction = action
        clickValue = value
        reInstance()
    }

    fun setHover(action: MCHoverEventAction, value: Any) = apply {
        hoverAction = action
        hoverValue = value
        reInstance()
    }

    fun chat() {
        TODO()
    }

    fun actionBar() {
        TODO()
    }

    private fun reInstance() {
        component = MCStringTextComponent(text.formatIf(formatted))

        reInstanceClick()
        reInstanceHover()
    }

    private fun reInstanceClick() {
        if (clickAction == null || clickValue == null)
            return

        val event = ClickEvent(clickAction, clickValue!!.formatIf(formatted))

        //#if MC>=11600
        //$$ component.style = component.style.setClickEvent(event)
        //#elseif MC>=11202
        //$$ component.style.clickEvent = event
        //#else
        component.chatStyle.chatClickEvent = event
        //#endif
    }

    private fun reInstanceHover() {
        if (hoverAction == null || hoverValue == null)
            return

        //#if MC>=11602
        //$$ val action: HoverEvent.Action<MCITextComponent> = hoverAction!! as HoverEvent.Action<MCITextComponent>
        //$$ val value: MCITextComponent = MCStringTextComponent(hoverValue!! as String)
        //$$ val event = HoverEvent<MCITextComponent>(action, value)
        //$$ setHoverEventHelper(event)
        //#else
        setHoverEventHelper(HoverEvent(
            hoverAction,
            MCStringTextComponent(hoverValue!! as String)
        ))
        //#endif
    }

    private fun setHoverEventHelper(event: HoverEvent) {
        //#if MC>=11600
        //$$ component.style = component.style.setHoverEvent(event)
        //#elseif MC>=11202
        //$$ component.style.hoverEvent = event
        //#else
        component.chatStyle.chatHoverEvent = event
        //#endif
    }

    private fun String.formatIf(predicate: Boolean) = if (predicate) UChat.addColor(this) else this

    //#if MC>=11602
    //$$ private class TextBuilder(private val isFormatted: Boolean) : ICharacterConsumer {
    //$$     private val builder = StringBuilder()
    //$$     private var cachedStyle: Style? = null
    //$$
    //$$     override fun accept(index: Int, style: Style, codePoint: Int): Boolean  {
    //$$         if (isFormatted && style != cachedStyle) {
    //$$             cachedStyle = style
    //$$             builder.append(formatString(style))
    //$$         }
    //$$
    //$$         builder.append(codePoint.toChar())
    //$$         return true
    //$$     }
    //$$
    //$$     fun getString() = builder.toString()
    //$$
    //$$     private fun formatString(style: Style): String {
    //$$         val builder = StringBuilder("§r")
    //$$
    //$$         when {
    //$$             style.bold -> builder.append("§l")
    //$$             style.italic -> builder.append("§o")
    //$$             style.underlined -> builder.append("§n")
    //$$             style.strikethrough -> builder.append("§m")
    //$$             style.obfuscated -> builder.append("§k")
    //$$         }
    //$$
    //$$         style.color?.let(colorToFormatChar::get)?.let {
    //$$             builder.append(it)
    //$$         }
    //$$         return builder.toString()
    //$$     }
    //$$
    //$$     companion object {
    //$$         private val colorToFormatChar = TextFormatting.values().mapNotNull { format ->
    //$$             Color.fromTextFormatting(format)?.let { it to format }
    //$$         }.toMap()
    //$$     }
    //$$ }
    //#endif

    // **********************
    // * METHOD DELEGATIONS *
    // **********************


    //#if MC>=11602
    //$$ val unformattedText: String get() {
    //$$     val builder = TextBuilder(false)
    //$$     component.func_241878_f().accept(builder)
    //$$     return builder.getString()
    //$$ }
    //$$
    //$$ val formattedText: String get() {
    //$$     val builder = TextBuilder(true)
    //$$     component.func_241878_f().accept(builder)
    //$$     return builder.getString()
    //$$ }
    //$$
    //$$ fun appendSibling(text: ITextComponent): IFormattableTextComponent = append(text)
    //$$
    //$$ override fun setStyle(style: Style): IFormattableTextComponent = component.setStyle(style)
    //$$
    //$$ override fun append(sibling: ITextComponent): IFormattableTextComponent = component.append(sibling)
    //$$
    //$$ override fun appendString(string: String): IFormattableTextComponent = component.appendString(string)
    //$$
    //$$ override fun modifyStyle(func: UnaryOperator<Style>): IFormattableTextComponent = component.modifyStyle(func)
    //$$
    //$$ override fun mergeStyle(style: Style): IFormattableTextComponent = component.mergeStyle(style)
    //$$
    //$$ override fun mergeStyle(vararg formats: TextFormatting): IFormattableTextComponent = component.mergeStyle(*formats)
    //$$
    //$$ override fun mergeStyle(format: TextFormatting): IFormattableTextComponent = component.mergeStyle(format)
    //$$
    //$$ override fun getString(): String = component.string
    //$$
    //$$ override fun getStringTruncated(maxLen: Int): String = component.getStringTruncated(maxLen)
    //$$
    //$$ override fun <T> getComponentWithStyle(p_230439_1_: IStyledTextAcceptor<T>, p_230439_2_: Style): Optional<T> {
    //$$     return component.getComponentWithStyle(p_230439_1_, p_230439_2_)
    //$$ }
    //$$
    //$$ override fun <T> getComponent(p_230438_1_: ITextAcceptor<T>): Optional<T> {
    //$$     return component.getComponent(p_230438_1_)
    //$$ }
    //$$
    //$$ override fun <T> func_230534_b_(p_230534_1_: IStyledTextAcceptor<T>, p_230534_2_: Style): Optional<T> {
    //$$     return component.func_230534_b_(p_230534_1_, p_230534_2_)
    //$$ }
    //$$
    //$$ override fun <T> func_230533_b_(p_230533_1_: ITextAcceptor<T>): Optional<T> {
    //$$     return component.func_230533_b_(p_230533_1_)
    //$$ }
    //$$
    //$$ override fun getStyle(): Style = component.style
    //$$
    //$$ override fun getUnformattedComponentText(): String = component.unformattedComponentText
    //$$
    //$$ override fun getSiblings(): MutableList<ITextComponent> = component.siblings
    //$$
    //$$ override fun copyRaw(): IFormattableTextComponent = component.copyRaw()
    //$$
    //$$ override fun deepCopy(): IFormattableTextComponent = component.deepCopy()
    //$$
    //$$ override fun func_241878_f(): IReorderingProcessor = component.func_241878_f()
    //#elseif MC>=11502
    //$$ val unformattedText: String get() = getUnformattedComponentText()
    //$$
    //$$ override fun appendText(text: String): ITextComponent = component.appendText(text)
    //$$
    //$$ override fun getString(): String = component.string
    //$$
    //$$ override fun getStringTruncated(maxLen: Int): String = component.getStringTruncated(maxLen)
    //$$
    //$$ override fun getFormattedText(): String = component.formattedText
    //$$
    //$$ override fun func_212637_f(): Stream<ITextComponent> = component.func_212637_f()
    //$$
    //$$ override fun iterator(): MutableIterator<ITextComponent> = component.iterator()
    //$$
    //$$ override fun deepCopy(): ITextComponent = component.deepCopy()
    //$$
    //$$ override fun applyTextStyle(styleConsumer: Consumer<Style>): ITextComponent {
    //$$     return component.applyTextStyle(styleConsumer)
    //$$ }
    //$$
    //$$ override fun applyTextStyles(vararg colors: TextFormatting): ITextComponent {
    //$$     return component.applyTextStyles(*colors)
    //$$ }
    //$$
    //$$ override fun applyTextStyle(color: TextFormatting): ITextComponent {
    //$$     return component.applyTextStyle(color)
    //$$ }
    //$$
    //$$ override fun setStyle(style: Style): ITextComponent {
    //$$     return component.setStyle(style)
    //$$ }
    //$$
    //$$ override fun getStyle(): Style = component.style
    //$$
    //$$ override fun appendSibling(other: ITextComponent): ITextComponent = component.appendSibling(other)
    //$$
    //$$ override fun getUnformattedComponentText(): String = component.unformattedComponentText
    //$$
    //$$ override fun getSiblings(): MutableList<ITextComponent> = component.siblings
    //$$
    //$$ override fun stream(): Stream<ITextComponent> = component.stream()
    //$$
    //$$ override fun shallowCopy(): ITextComponent = component.shallowCopy()
    //#elseif MC>=11202
    //$$ override fun setStyle(style: Style): ITextComponent = component.setStyle(style)
    //$$
    //$$ override fun getStyle(): Style = component.style
    //$$
    //$$ override fun appendText(text: String): ITextComponent = component.appendText(text)
    //$$
    //$$ override fun appendSibling(other: ITextComponent): ITextComponent = component.appendSibling(other)
    //$$
    //$$ override fun getUnformattedComponentText(): String = component.unformattedComponentText
    //$$
    //$$ override fun getUnformattedText(): String = component.unformattedText
    //$$
    //$$ override fun getFormattedText(): String = component.formattedText
    //$$
    //$$ override fun getSiblings(): MutableList<ITextComponent> = component.siblings
    //$$
    //$$ override fun createCopy(): ITextComponent = component.createCopy()
    //$$
    //$$ override fun iterator(): MutableIterator<ITextComponent> = component.iterator()
    //#else
    override fun setChatStyle(style: ChatStyle): IChatComponent = component.setChatStyle(style)

    override fun getChatStyle(): ChatStyle = component.chatStyle

    override fun appendText(text: String): IChatComponent = component.appendText(text)

    override fun appendSibling(other: IChatComponent): IChatComponent = component.appendSibling(other)

    override fun getUnformattedTextForChat(): String = component.unformattedTextForChat

    override fun getUnformattedText(): String = component.unformattedText

    override fun getFormattedText(): String = component.formattedText

    override fun getSiblings(): MutableList<IChatComponent> = component.siblings

    override fun createCopy(): IChatComponent = component.createCopy()

    override fun iterator(): MutableIterator<IChatComponent> = component.iterator()
    //#endif

    companion object {
        fun from(obj: Any): UTextComponent? {
            return when (obj) {
                is UTextComponent -> obj
                is String -> UTextComponent(obj)
                is MCIMutableText -> UTextComponent(obj)
                else -> null
            }
        }

        fun stripFormatting(string: String): String {
            //#if MC>=11202
            //$$ return TextFormatting.getTextWithoutFormattingCodes(string)!!
            //#else
            return EnumChatFormatting.getTextWithoutFormattingCodes(string)
            //#endif
        }
    }
}
