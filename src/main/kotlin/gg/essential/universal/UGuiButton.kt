package gg.essential.universal

import net.minecraft.client.gui.GuiButton

object UGuiButton {
    @JvmStatic
    fun getX(button: GuiButton): Int {
        return button.xPosition
    }

    @JvmStatic
    fun getY(button: GuiButton): Int {
        return button.yPosition
    }
}
