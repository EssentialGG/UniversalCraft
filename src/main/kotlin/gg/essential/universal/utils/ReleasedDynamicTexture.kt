package gg.essential.universal.utils


import gg.essential.universal.UGraphics
import net.minecraft.client.renderer.texture.TextureUtil
//#if MC<11502
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.resources.IResourceManager
//#else
//$$ import java.util.function.Supplier
//$$ import net.minecraft.client.renderer.texture.NativeImage
//$$ import org.apache.logging.log4j.LogManager
//$$ import com.mojang.blaze3d.systems.RenderSystem
//#endif


import java.awt.image.BufferedImage
import java.io.IOException


//#if MC<11502
class ReleasedDynamicTexture(
    /** width of this icon in pixels  */
    val width: Int,
    /** height of this icon in pixels  */
    val height: Int
) : AbstractTexture() {
    var textureData: IntArray = IntArray(width * height)
    var uploaded: Boolean = false

    constructor(bufferedImage: BufferedImage) : this(bufferedImage.width, bufferedImage.height) {
        bufferedImage.getRGB(0, 0, bufferedImage.width, bufferedImage.height, textureData, 0, bufferedImage.width)
    }

    @Throws(IOException::class)
    override fun loadTexture(resourceManager: IResourceManager) {
    }

    fun updateDynamicTexture() {
        uploadTexture()
    }

    fun uploadTexture() {
        if (!uploaded) {
            TextureUtil.allocateTexture(super.getGlTextureId(), width, height)
            TextureUtil.uploadTexture(
                super.getGlTextureId(), textureData,
                width, height
            )
            textureData = IntArray(0)
            uploaded = true
        }
    }

    override fun getGlTextureId(): Int {
        uploadTexture()
        return super.getGlTextureId()
    }

    protected fun finalize() {
        UGraphics.deleteTexture(getGlTextureId())
    }

}
//#else
//$$ class ReleasedDynamicTexture : net.minecraft.client.renderer.texture.Texture {
//$$     val width: Int
//$$     val height: Int
//$$     private var dynamicTextureData: NativeImage?
//$$
//$$     constructor(nativeImageIn: NativeImage) {
//$$         dynamicTextureData = nativeImageIn
//$$         width = nativeImageIn.width
//$$         height = nativeImageIn.height
//$$         if (!RenderSystem.isOnRenderThread()) {
//$$             RenderSystem.recordRenderCall {
//$$                 TextureUtil.prepareImage(
//$$                     this.allocGlId(),
//$$                     dynamicTextureData!!.width,
//$$                     dynamicTextureData!!.height
//$$                 )
//$$                 updateDynamicTexture()
//$$             }
//$$         } else {
//$$             TextureUtil.prepareImage(
//$$                 this.allocGlId(),
//$$                 dynamicTextureData!!.width,
//$$                 dynamicTextureData!!.height
//$$             )
//$$             updateDynamicTexture()
//$$         }
//$$     }
//$$
//$$     constructor(widthIn: Int, heightIn: Int, clearIn: Boolean) {
//$$         RenderSystem.assertThread { RenderSystem.isOnGameThreadOrInit() }
//$$         dynamicTextureData = NativeImage(widthIn, heightIn, clearIn)
//$$         width = widthIn
//$$         height = heightIn
//$$         TextureUtil.prepareImage(this.allocGlId(), dynamicTextureData!!.width, dynamicTextureData!!.height)
//$$     }
//$$
    //#if FABRIC
    //#disable-remap FIXME preprocessor bug: remaps to `this.glId` which gets shadowed by the protected field
    //$$ private fun allocGlId() = this.getGlId()
    //#enable-remap
    //#else
    //$$ private fun allocGlId() = this.getGlTextureId()
    //#endif
//$$
//$$     override fun loadTexture(manager: net.minecraft.resources.IResourceManager) {}
//$$
//$$     fun updateDynamicTexture() {
//$$         if (dynamicTextureData != null) {
//$$             this.bindTexture()
//$$             dynamicTextureData!!.uploadTextureSub(0, 0, 0, false)
//$$         } else {
//$$             field_243504_d.warn("Trying to upload disposed texture {}", glTextureId)
//$$         }
//$$         dynamicTextureData = null
//$$     }
//$$
//$$     var textureData: NativeImage?
//$$         get() = dynamicTextureData
//$$         set(nativeImageIn) {
//$$             if (dynamicTextureData != null) {
//$$                 dynamicTextureData!!.close()
//$$             }
//$$             dynamicTextureData = nativeImageIn
//$$         }
//$$
    //#if MC>=11600
    //$$ override fun close() {
    //#else
    //$$ fun close() {
    //#endif
//$$         if (dynamicTextureData != null) {
//$$             dynamicTextureData!!.close()
//$$             this.deleteGlTexture()
//$$             dynamicTextureData = null
//$$         }
//$$     }
//$$
//$$     companion object {
//$$         private val field_243504_d = LogManager.getLogger()
//$$     }
//$$ }
//#endif