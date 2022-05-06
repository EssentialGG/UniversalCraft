package gg.essential.universal

//#if MC<11600
import java.awt.image.BufferedImage
//#else
//$$ import net.minecraft.client.renderer.texture.NativeImage
//#endif

//#if MC>=11600
//$$ class UImage(val native: NativeImage) {
//#else
class UImage(val native: BufferedImage) {
//#endif

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
            //$$ return UImage(NativeImage(width, height, clear))
            //#else
            return UImage(BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB))
            //#endif
        }
    }
}
