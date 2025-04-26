package gg.essential.universal.utils

import net.minecraft.util.IChatComponent

//#if MC>=11600
//$$ import net.minecraft.util.text.Color
//$$ import net.minecraft.util.text.Style
//$$ import net.minecraft.util.text.TextFormatting
//$$ import java.util.Optional
//#endif

//#if MC>=11602
//$$ private val colorToFormatChar = TextFormatting.values().mapNotNull { format ->
//$$     Color.fromTextFormatting(format)?.let { it to format }
//$$ }.toMap()
//$$
//$$ private fun formatString(style: Style): String = buildString {
//$$    style.color?.let(colorToFormatChar::get)?.let(::append)
//$$    if (style.isBold) append("§l")
//$$    if (style.isItalic) append("§o")
//$$    if (style.isUnderlined) append("§n")
//$$    if (style.isObfuscated) append("§k")
//$$    if (style.isStrikethrough) append("§m")
//$$ }
//#endif

fun IChatComponent.toUnformattedString(): String {
    //#if MC>=11600
    //$$ return string
    //#else
    return unformattedText
    //#endif
}

fun IChatComponent.toFormattedString(): String {
    //#if MC>=11600
    //$$ return buildString {
    //$$    append(formatString(style))
    //$$    this@toFormattedString.visit<String> {
    //$$        append(it)
    //$$        Optional.empty()
    //$$    }
    //$$    append("§r")
    //$$    siblings.forEach { append(it.toFormattedString()) }
    //$$ }
    //#else
    return formattedText
    //#endif
}
