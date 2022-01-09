package gg.essential.universal

import java.awt.Color

enum class ChatColor(val char: Char, val color: Color? = null, val isFormat: Boolean = false) {
    BLACK('0', Color(0x000000)),
    DARK_BLUE('1', Color(0x0000AA)),
    DARK_GREEN('2', Color(0x00AA00)),
    DARK_AQUA('3', Color(0x00AAAA)),
    DARK_RED('4', Color(0xAA0000)),
    DARK_PURPLE('5', Color(0xAA00AA)),
    GOLD('6', Color(0xFFAA00)),
    GRAY('7', Color(0xAAAAAA)),
    DARK_GRAY('8', Color(0x555555)),
    BLUE('9', Color(0x5555FF)),
    GREEN('a', Color(0x55FF55)),
    AQUA('b', Color(0x55FFFF)),
    RED('c', Color(0xFF5555)),
    LIGHT_PURPLE('d', Color(0xFF55FF)),
    YELLOW('e', Color(0xFFFF55)),
    WHITE('f', Color(0xFFFFFF)),
    MAGIC('k', isFormat = true),
    BOLD('l', isFormat = true),
    STRIKETHROUGH('m', isFormat = true),
    UNDERLINE('n', isFormat = true),
    ITALIC('o', isFormat = true),
    RESET('r');

    override fun toString(): String = "${COLOR_CHAR}$char"

    fun isColor(): Boolean = color != null
  
    operator fun plus(text: String): String = toString() + text

    companion object {
        const val COLOR_CHAR: Char = '\u00a7'
        val FORMATTING_CODE_PATTERN = Regex("§[0-9a-fk-or]", RegexOption.IGNORE_CASE)

        fun translateAlternateColorCodes(altColorChar: Char, textToTranslate: String): String {
            val b = textToTranslate.toCharArray()
            for (i in 0 until b.size - 1) {
                if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                    b[i] = COLOR_CHAR
                    b[i + 1] = Character.toLowerCase(b[i + 1])
                }
            }
            return String(b)
        }

        fun stripControlCodes(text: String?) =
            text?.let { FORMATTING_CODE_PATTERN.replace(it, "") }
    }
}
