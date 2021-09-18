package gg.essential.universal

import gg.essential.universal.utils.MCButton

object UGuiButton {
    @JvmStatic
    fun getX(button: MCButton): Int {
        return button.xPosition
    }

    @JvmStatic
    fun getY(button: MCButton): Int {
        return button.yPosition
    }
}
