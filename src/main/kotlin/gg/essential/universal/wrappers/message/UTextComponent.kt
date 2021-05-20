
package gg.essential.universal.wrappers.message

import gg.essential.universal.UChat
import gg.essential.universal.utils.*

//#if FABRIC
//$$ import net.minecraft.text.*
//$$ import net.minecraft.text.StringVisitable.StyledVisitor
//$$ import net.minecraft.text.StringVisitable.Visitor
//$$ import net.minecraft.util.Formatting
//$$
//$$ import java.util.function.UnaryOperator
//#elseif MC<=10809
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent
import net.minecraft.util.ChatStyle
//#else
//$$ import net.minecraft.util.text.*
//$$ import net.minecraft.util.text.event.*
//#if MC>=11602
//$$ import net.minecraft.util.IReorderingProcessor
//$$ import net.minecraft.util.text.ITextProperties.IStyledTextAcceptor
//$$ import net.minecraft.util.text.ITextProperties.ITextAcceptor
//#endif
//#endif

import java.util.Iterator
import java.util.List
import java.util.Optional

//#if FORGE && MC>=11502
//$$ import java.util.stream.Stream
//$$ import java.util.function.Consumer
//#endif

@Suppress("MemberVisibilityCanBePrivate")
class UTextComponent : MCITextComponent {
    lateinit var component: MCITextComponent
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

    constructor(component: MCITextComponent) {
        this.component = component

        //#if FABRIC
        //$$ text = formattedText
        //#elseif MC>=11602
        //$$ val builder = FormattedTextBuilder()
        //$$ component.func_230439_a_(builder, Style.EMPTY)
        //$$ text = builder.getString()
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

            //#if FABRIC
            //$$ hoverValue = hoverEvent.getValue(hoverAction)
            //#elseif MC>=11602
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

        //#if FABRIC
        //$$ component.style = component.style.withClickEvent(event)
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
        //#if FABRIC
        //$$ component.style = component.style.withHoverEvent(event)
        //#elseif MC>=11202
        //$$ component.style.hoverEvent = event
        //#else
        component.chatStyle.chatHoverEvent = event
        //#endif
    }

    private fun String.formatIf(predicate: Boolean) = if (predicate) UChat.addColor(this) else this

    //#if MC>=11602
    //#if FORGE
    //$$ private class FormattedTextBuilder : ITextProperties.IStyledTextAcceptor<Any> {
    //$$     private val builder = StringBuilder()
    //$$     private var cachedStyle: Style? = null
    //$$
    //$$     override fun accept(style: Style, string: String): Optional<Any>  {
    //$$         if (style != cachedStyle) {
    //$$             cachedStyle = style
    //$$             builder.append(formatString(style))
    //$$         }
    //$$
    //$$         return Optional.empty()
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
    //$$         if (style.color != null)
    //$$             builder.append(style.color.toString())
    //$$         return builder.toString()
    //$$     }
    //$$ }
    //#else
    //$$ private class TextBuilder(private val isFormatted: Boolean) : CharacterVisitor {
    //$$     private val builder = StringBuilder()
    //$$     private var cachedStyle: Style? = null
    //$$
    //$$     override fun accept(index: Int, style: Style, codePoint: Int): Boolean {
    //$$         if (isFormatted && style != cachedStyle) {
    //$$             cachedStyle = style
    //$$             builder.append(formatString(style))
    //$$         }
    //$$         builder.append(codePoint.toChar())
    //$$         return true
    //$$     }
    //$$
    //$$     fun getString() = builder.toString()
    //$$
    //$$     private fun formatString(style: Style): String {
    //$$         val builder = StringBuilder("§r");
    //$$
    //$$         when {
    //$$             style.isBold -> builder.append("§l")
    //$$             style.isItalic -> builder.append("§o")
    //$$             style.isUnderlined -> builder.append("§n")
    //$$             style.isStrikethrough -> builder.append("§m")
    //$$             style.isObfuscated -> builder.append("§k")
    //$$         }
    //$$
    //$$         if (style.color != null)
    //$$             builder.append(style.color.toString())
    //$$         return builder.toString()
    //$$     }
    //$$ }
    //#endif
    //#endif

    // **********************
    // * METHOD DELEGATIONS *
    // **********************


    //#if FABRIC
    //$$ val unformattedText: String get() {
    //$$     val builder = TextBuilder(false)
    //$$     component.asOrderedText().accept(builder)
    //$$     return builder.getString()
    //$$ }
    //$$
    //$$ val formattedText: String get() {
    //$$     val builder = TextBuilder(true)
    //$$     component.asOrderedText().accept(builder)
    //$$     return builder.getString()
    //$$ }
    //$$
    //$$ fun appendSibling(text: Text): MutableText = append(text)
    //$$
    //$$ override fun append(text: String): MutableText = component.append(text)
    //$$
    //$$ override fun styled(styleUpdater: UnaryOperator<Style>): MutableText = component.styled(styleUpdater)
    //$$
    //$$ override fun fillStyle(styleOverride: Style): MutableText = component.fillStyle(styleOverride)
    //$$
    //$$ override fun formatted(vararg formattings: Formatting): MutableText = component.formatted(*formattings)
    //$$
    //$$ override fun formatted(formatting: Formatting): MutableText = component.formatted(formatting)
    //$$
    //$$ override fun setStyle(style: Style): MutableText = component.setStyle(style)
    //$$
    //$$ override fun append(text: Text): MutableText = component.append(text)
    //$$
    //$$ override fun getString(): String = component.string
    //$$
    //$$ override fun asTruncatedString(length: Int): String = component.asTruncatedString(length)
    //$$
    //$$ override fun <T> visit(styledVisitor: StyledVisitor<T>, style: Style): Optional<T> {
    //$$     return component.visit(styledVisitor, style)
    //$$ }
    //$$
    //$$ override fun <T> visit(visitor: Visitor<T>): Optional<T> {
    //$$     return component.visit(visitor)
    //$$ }
    //$$
    //$$ override fun <T> visitSelf(visitor: StyledVisitor<T>, style: Style): Optional<T> {
    //$$     return component.visitSelf(visitor, style)
    //$$ }
    //$$
    //$$ override fun <T> visitSelf(visitor: Visitor<T>): Optional<T> {
    //$$     return component.visitSelf(visitor)
    //$$ }
    //$$
    //$$ override fun getStyle(): Style = component.style
    //$$
    //$$ override fun asString(): String = component.asString()
    //$$
    //$$ override fun getSiblings(): MutableList<Text> = component.siblings
    //$$
    //$$ override fun copy(): MutableText = component.copy()
    //$$
    //$$ override fun shallowCopy(): MutableText = component.shallowCopy()
    //$$
    //$$ override fun asOrderedText(): OrderedText = component.asOrderedText()
    //#elseif MC>=11602
    //$$ fun appendSibling(component: ITextComponent) {
    //$$     siblings.add(component)
    //$$ }
    //$$
    //$$ val unformattedText: String get() = unformattedComponentText
    //$$
    //$$ val formattedText: String get() = text
    //$$
    //$$ override fun getString(): String = component.string
    //$$
    //$$ override fun getStringTruncated(maxLen: Int): String = component.getStringTruncated(maxLen)
    //$$
    //$$ override fun <T> func_230439_a_(p_230439_1_: IStyledTextAcceptor<T>, p_230439_2_: Style): Optional<T> {
    //$$     return component.func_230439_a_(p_230439_1_, p_230439_2_)
    //$$ }
    //$$
    //$$ override fun <T> func_230438_a_(p_230438_1_: ITextAcceptor<T>): Optional<T> {
    //$$     return component.func_230438_a_(p_230438_1_)
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

    override fun createCopy(): IChatComponent = component.createCopy()

    //#if MC==10710
    //$$ override fun getSiblings(): MutableList<IChatComponent> = component.siblings as MutableList<IChatComponent>

    //$$ override fun iterator(): MutableIterator<IChatComponent> = component.iterator() as MutableIterator<IChatComponent>
    //#else
    override fun getSiblings(): MutableList<IChatComponent> = component.siblings

    override fun iterator(): MutableIterator<IChatComponent> = component.iterator()
    //#endif
    //#endif

    companion object {
        fun from(obj: Any): UTextComponent? {
            return when (obj) {
                is UTextComponent -> obj
                is String -> UTextComponent(obj)
                is MCITextComponent -> UTextComponent(obj)
                else -> null
            }
        }

        fun stripFormatting(string: String): String {
            //#if FABRIC
            //$$ return Formatting.strip(string)!!
            //#elseif MC>=11202
            //$$ return TextFormatting.getTextWithoutFormattingCodes(string)!!
            //#else
            return EnumChatFormatting.getTextWithoutFormattingCodes(string)
            //#endif
        }
    }
}
