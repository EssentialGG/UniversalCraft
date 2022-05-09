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
        //$$ return UImage(NativeImage(getWidth(), getHeight(), nativeImage.format.hasAlpha()))
        //#else
        return UImage(BufferedImage(getWidth(), getHeight(), nativeImage.type))
        //#endif
    }

    fun getPixelRGBA(x: Int, y: Int): Int {
        //#if MC>=11600
        //$$ return nativeImage.getPixelRGBA(x, y)
        //#else
        return Integer.rotateLeft(nativeImage.getRGB(x, y), 8) // Convert ARGB to RGBA
        //#endif
    }

    fun setPixelRGBA(x: Int, y: Int, color: Int) {
        //#if MC>=11600
        //$$ nativeImage.setPixelRGBA(x, y, color)
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
