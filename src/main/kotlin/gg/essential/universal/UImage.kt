package gg.essential.universal

import gg.essential.universal.utils.NImage

class UImage(val native: NImage) {

    fun copyFrom(other: UImage) {
        val otherNative = other.native
        //#if MC>=11600
        //$$ native.copyImageData(otherNative);
        //#else
        native.graphics.drawImage(otherNative, 0, 0, otherNative.width, otherNative.height, null)
        //#endif
    }

    fun set(x: Int, y: Int, color: Int) {
        //#if MC>=11600
        //$$ native.setPixelRGBA(x, y, color);
        //#else
        native.setRGB(x, y, color)
        //#endif
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun ofSize(width: Int, height: Int, clear: Boolean = true): UImage {
            //#if MC>=11600
            //$$ return UImage(NImage(width, height, clear))
            //#else
            return UImage(NImage(width, height, NImage.TYPE_INT_ARGB))
            //#endif
        }
    }
}
