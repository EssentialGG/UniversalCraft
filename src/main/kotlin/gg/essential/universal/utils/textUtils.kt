package gg.essential.universal.utils

import net.minecraft.util.IChatComponent

//#if MC>=11600
//$$ import net.minecraft.util.ICharacterConsumer
//$$ import net.minecraft.util.text.Color
//$$ import net.minecraft.util.text.Style
//$$ import net.minecraft.util.text.TextFormatting
//#endif

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

fun IChatComponent.toUnformattedString(): String {
    //#if MC>=11600
    //$$ val builder = TextBuilder(false)
    //$$ func_241878_f().accept(builder)
    //$$ return builder.getString()
    //#else
    return unformattedText
    //#endif
}

fun IChatComponent.toFormattedString(): String {
    //#if MC>=11600
    //$$ val builder = TextBuilder(true)
    //$$ func_241878_f().accept(builder)
    //$$ return builder.getString()
    //#else
    return formattedText
    //#endif
}
