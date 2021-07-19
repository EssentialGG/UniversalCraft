package gg.essential.universal.utils

import gg.essential.universal.UGraphics
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.TextureUtil
import net.minecraft.client.resources.IResourceManager

//#if MC<11502
import java.awt.image.BufferedImage
//#else
//$$ import net.minecraft.client.renderer.texture.NativeImage
//#endif


import java.io.IOException


class ReleasedDynamicTexture private constructor(
    val width: Int,
    val height: Int,
    //#if MC>=11400
    //$$ textureData: NativeImage?,
    //#else
    textureData: IntArray?,
    //#endif
) : AbstractTexture() {

    //#if MC>=11400
    //$$ private var textureData: NativeImage? = textureData ?: NativeImage(width, height, true)
    //$$    set(value) {
    //$$        field?.close()
    //$$        field = value
    //$$    }
    //#else
    var textureData: IntArray = textureData ?: IntArray(width * height)
    //#endif

    var uploaded: Boolean = false

    constructor(width: Int, height: Int) : this(width, height, null)

    //#if MC>=11400
    //$$ constructor(nativeImage: NativeImage) : this(nativeImage.width, nativeImage.height, nativeImage)
    //#else
    constructor(bufferedImage: BufferedImage) : this(bufferedImage.width, bufferedImage.height) {
        bufferedImage.getRGB(0, 0, bufferedImage.width, bufferedImage.height, textureData, 0, bufferedImage.width)
    }
    //#endif

    @Throws(IOException::class)
    override fun loadTexture(resourceManager: IResourceManager) {
    }

    fun updateDynamicTexture() {
        uploadTexture()
    }

    fun uploadTexture() {
        if (!uploaded) {
            TextureUtil.allocateTexture(allocGlId(), width, height)
            //#if MC>=11400
            //$$ UGraphics.configureTexture(allocGlId()) {
            //$$     textureData?.uploadTextureSub(0, 0, 0, false)
            //$$ }
            //$$ textureData = null
            //#else
            TextureUtil.uploadTexture(
                super.getGlTextureId(), textureData,
                width, height
            )
            textureData = IntArray(0)
            //#endif
            uploaded = true
        }
    }

    //#if FABRIC
    //#disable-remap FIXME preprocessor bug: remaps to `super.glId` which gets shadowed by the protected field
    //$$ private fun allocGlId() = super.getGlId()
    //#enable-remap
    //#else
    private fun allocGlId() = super.getGlTextureId()
    //#endif

    override fun getGlTextureId(): Int {
        uploadTexture()
        return super.getGlTextureId()
    }

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Workaround for preprocessor bug where this property does not get remapped in consumer code. Do not use outside of Elementa and Essential, may be removed in the future.")
    // FIXME preprocessor bug: consumer code does not get remapped if we overwrite the getter
    //#if FABRIC
    //$$ val glTextureId get() = getGlId()
    //#else
    val glId get() = getGlTextureId()
    //#endif

    protected fun finalize() {
        UGraphics.deleteTexture(getGlTextureId())
        //#if MC>=11400
        //$$ textureData = null
        //#endif
    }

    //#if MC>=11600
    //$$ override fun close() {
    //$$     textureData = null
    //$$     deleteGlTexture()
    //$$ }
    //#endif

}
