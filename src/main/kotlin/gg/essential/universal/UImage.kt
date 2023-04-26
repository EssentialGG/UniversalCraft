package gg.essential.universal

//#if MC<11600
import java.awt.image.BufferedImage
//#else
//$$ import net.minecraft.client.renderer.texture.NativeImage
//#endif

//#if MC>=11600
//$$ class UImage(val nativeImage: NativeImage) {
//#else
class UImage(val nativeImage: BufferedImage) {
//#endif

    fun copyFrom(other: UImage) {
        val otherNative = other.nativeImage
        //#if MC>=11600
        //$$ nativeImage.copyImageData(otherNative)
        //#else
        nativeImage.graphics.drawImage(otherNative, 0, 0, otherNative.width, otherNative.height, null)
        //#endif
    }

    fun copy(): UImage {
        //#if MC>=11600
        //$$ return UImage(NativeImage(getWidth(), getHeight(), false)).also { it.copyFrom(this) }
        //#else
        return UImage(BufferedImage(getWidth(), getHeight(), nativeImage.type)).also { it.copyFrom(this) }
        //#endif
    }

    fun getPixelRGBA(x: Int, y: Int): Int {
        //#if MC>=11600
        //$$ // Convert ABGR to RGBA
        //$$ val abgr = nativeImage.getPixelRGBA(x, y) // mappings are incorrect, this returns ABGR
        //$$ val a = abgr shr 24 and 0xFF
        //$$ val b = abgr shr 16 and 0xFF
        //$$ val g = abgr shr 8 and 0xFF
        //$$ val r = abgr and 0xFF
        //$$ return (r shl 24) or (g shl 16) or (b shl 8) or a
        //#else
        return Integer.rotateLeft(nativeImage.getRGB(x, y), 8) // Convert ARGB to RGBA
        //#endif
    }

    fun setPixelRGBA(x: Int, y: Int, color: Int) {
        //#if MC>=11600
        //$$ // Convert RGBA to ABGR
        //$$ val r = color shr 24 and 0xFF
        //$$ val g = color shr 16 and 0xFF
        //$$ val b = color shr 8 and 0xFF
        //$$ val a = color and 0xFF
        //$$ nativeImage.setPixelRGBA(x, y, (a shl 24) or (b shl 16) or (g shl 8) or r) // mappings are incorrect, this takes ABGR
        //#else
        nativeImage.setRGB(x, y, Integer.rotateRight(color, 8)) // Convert RGBA to ARGB
        //#endif
    }

    fun getWidth() = nativeImage.width

    fun getHeight() = nativeImage.height

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
