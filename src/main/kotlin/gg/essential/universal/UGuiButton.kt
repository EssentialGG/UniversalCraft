package gg.essential.universal

import gg.essential.universal.utils.MCButton

object UGuiButton {
    @JvmStatic
    fun getX(button: MCButton): Int {
        //#if MC<=10809
        return button.xPosition
        //#else
        //$$ return button.x
        //#endif
    }

    @JvmStatic
    fun getY(button: MCButton): Int {
        //#if MC<=10809
        return button.yPosition
        //#else
        //$$ return button.y
        //#endif
    }
}
