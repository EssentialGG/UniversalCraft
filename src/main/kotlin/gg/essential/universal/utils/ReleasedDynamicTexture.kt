package gg.essential.universal.utils

import gg.essential.universal.UGraphics

//#if STANDALONE
//$$ import org.lwjgl.BufferUtils
//$$ import org.lwjgl.opengl.GL20C
//$$ import java.nio.Buffer
//#else
//#if MC>=12106
//$$ import com.mojang.blaze3d.textures.GpuTextureView
//#endif

//#if MC>=12105
//$$ import com.mojang.blaze3d.systems.RenderSystem
//$$ import com.mojang.blaze3d.textures.FilterMode
//$$ import com.mojang.blaze3d.textures.GpuTexture
//$$ import com.mojang.blaze3d.textures.TextureFormat
//$$ import net.minecraft.client.texture.GlTexture
//#endif

import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.TextureUtil
import net.minecraft.client.resources.IResourceManager
//#endif

//#if MC<11502 || STANDALONE
import java.awt.image.BufferedImage
//#else
//$$ import com.mojang.blaze3d.platform.GlStateManager
//$$ import net.minecraft.client.renderer.texture.NativeImage
//$$ import org.lwjgl.opengl.GL11
//#endif


import java.io.Closeable
import java.io.IOException
import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class ReleasedDynamicTexture private constructor(
    val width: Int,
    val height: Int,
    //#if MC>=11400 && !STANDALONE
    //$$ textureData: NativeImage?,
    //#else
    textureData: IntArray?,
    //#endif
//#if STANDALONE
//$$ ) {
//#else
) : AbstractTexture() {
//#endif

    private var resources = Resources(this)

    //#if MC>=11400 && !STANDALONE
    //$$ init {
    //$$     resources.textureData = textureData ?: NativeImage(width, height, true)
    //$$ }
    //$$ private var textureData by resources::textureData
    //#else
    var textureData: IntArray = textureData ?: IntArray(width * height)
    //#endif

    var uploaded: Boolean = false

    constructor(width: Int, height: Int) : this(width, height, null)

    //#if MC>=11400 && !STANDALONE
    //$$ constructor(nativeImage: NativeImage) : this(nativeImage.width, nativeImage.height, nativeImage)
    //#else
    constructor(bufferedImage: BufferedImage) : this(bufferedImage.width, bufferedImage.height) {
        bufferedImage.getRGB(0, 0, bufferedImage.width, bufferedImage.height, textureData, 0, bufferedImage.width)
    }
    //#endif

    //#if MC<12104 && !STANDALONE
    @Throws(IOException::class)
    override fun loadTexture(resourceManager: IResourceManager) {
    }
    //#endif

    fun updateDynamicTexture() {
        uploadTexture()
    }

    fun uploadTexture() {
        if (!uploaded) {
            //#if STANDALONE
            //$$ val glId = GL20C.glGenTextures()
            //$$
            //$$ GL20C.glBindTexture(GL20C.GL_TEXTURE_2D, glId)
            //$$
            //$$ GL20C.glTexParameteri(GL20C.GL_TEXTURE_2D, GL20C.GL_TEXTURE_MIN_FILTER, GL20C.GL_LINEAR)
            //$$ GL20C.glTexParameteri(GL20C.GL_TEXTURE_2D, GL20C.GL_TEXTURE_MAG_FILTER, GL20C.GL_NEAREST)
            //$$ GL20C.glTexParameteri(GL20C.GL_TEXTURE_2D, GL20C.GL_TEXTURE_WRAP_S, GL20C.GL_CLAMP_TO_EDGE)
            //$$ GL20C.glTexParameteri(GL20C.GL_TEXTURE_2D, GL20C.GL_TEXTURE_WRAP_T, GL20C.GL_CLAMP_TO_EDGE)
            //$$
            //$$ val nativeBuffer = BufferUtils.createIntBuffer(textureData.size)
            //$$ nativeBuffer.put(textureData)
            //$$ (nativeBuffer as Buffer).rewind()
            //$$ GL20C.glTexImage2D(
            //$$     GL20C.GL_TEXTURE_2D,
            //$$     0,
            //$$     GL20C.GL_RGBA,
            //$$     width,
            //$$     height,
            //$$     0,
            //$$     GL20C.GL_BGRA,
            //$$     GL20C.GL_UNSIGNED_BYTE,
            //$$     nativeBuffer
            //$$ )
            //$$ textureData = IntArray(0)
            //$$
            //$$ uploaded = true
            //$$ resources.glId = glId
            //#elseif MC>=12105
            //$$ val device = RenderSystem.getDevice()
            //#if MC>=12106
            //$$ val usage = GpuTexture.USAGE_TEXTURE_BINDING or GpuTexture.USAGE_COPY_SRC or GpuTexture.USAGE_COPY_DST
            //$$ val texture = device.createTexture(null as String?, usage, TextureFormat.RGBA8, width, height, 1, 1)
            //#else
            //$$ val texture = device.createTexture(null as String?, TextureFormat.RGBA8, width, height, 1)
            //#endif
            //$$ texture.setTextureFilter(FilterMode.NEAREST, true)
            //$$ UGraphics.configureTexture((texture as GlTexture).glId) {
                //#if MC>=12106
                //$$ texture.checkDirty(GL11.GL_TEXTURE_2D)
                //#else
                //$$ texture.checkDirty()
                //#endif
            //$$ }
            //$$ device.createCommandEncoder().writeToTexture(texture, textureData!!)
            //$$ textureData = null
            //$$ uploaded = true
            //$$ resources.gpuTexture = texture
            //$$ this.glTexture = texture
            //#if MC>=12106
            //$$ val view = device.createTextureView(texture)
            //$$ resources.gpuTextureView = view
            //$$ this.glTextureView = view
            //#endif
            //#else
            TextureUtil.allocateTexture(allocGlId(), width, height)

            //#if MC>=11400
            //$$ UGraphics.configureTexture(allocGlId()) {
            //$$     textureData?.uploadTextureSub(0, 0, 0, false)
            //$$     GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
            //$$     GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
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

            resources.glId = allocGlId()
            //#endif
            Resources.drainCleanupQueue()
        }
    }

    //#if MC<12105
    private fun allocGlId() = super.getGlTextureId()
    //#endif

    val dynamicGlId: Int
        //#if MC>=12105 && !STANDALONE
        //$$ get() {
        //$$     uploadTexture()
        //$$     return (resources.gpuTexture as GlTexture?)?.glId ?: -1
        //$$ }
        //#else
        get() = getGlTextureId()
        //#endif

    //#if STANDALONE
    //$$ fun getGlTextureId(): Int {
    //$$     uploadTexture()
    //$$     return resources.glId
    //$$ }
    //$$
    //$$ fun deleteGlTexture() {
    //$$     UGraphics.deleteTexture(resources.glId)
    //$$     resources.glId = -1
    //$$ }
    //#elseif MC>=12105
    //#if MC>=12106
    //$$ override fun getGlTextureView(): GpuTextureView {
    //$$     uploadTexture()
    //$$     return super.getGlTextureView()
    //$$ }
    //$$
    //$$ override fun setUseMipmaps(mipmaps: Boolean) {
    //$$     uploadTexture()
    //$$     super.setUseMipmaps(mipmaps)
    //$$ }
    //#endif
    //$$
    //$$ override fun setClamp(clamp: Boolean) {
    //$$     uploadTexture()
    //$$     super.setClamp(clamp)
    //$$ }
    //$$
    //$$ override fun setFilter(bilinear: Boolean, mipmap: Boolean) {
    //$$     uploadTexture()
    //$$     super.setFilter(bilinear, mipmap)
    //$$ }
    //$$
    //$$ override fun getGlTexture(): GpuTexture {
    //$$     uploadTexture()
    //$$     return super.getGlTexture()
    //$$ }
    //#else
    override fun getGlTextureId(): Int {
        uploadTexture()
        return super.getGlTextureId()
    }

    override fun deleteGlTexture() {
        super.deleteGlTexture()
        resources.glId = -1
    }
    //#endif

    //#if STANDALONE
    //#elseif MC>=12105
    //$$ override fun close() {
    //$$     super.close()
    //$$     resources.close()
    //$$ }
    //#elseif MC>=11600
    //$$ override fun close() {
    //$$     deleteGlTexture()
    //$$     resources.close()
    //$$ }
    //#endif

    private class Resources(referent: ReleasedDynamicTexture) : PhantomReference<ReleasedDynamicTexture>(referent, referenceQueue), Closeable {
        //#if MC>=12105 && !STANDALONE
        //$$ var gpuTexture: GpuTexture? = null
        //$$    set(value) {
        //$$        field?.close()
        //$$        field = value
        //$$    }
        //$$
        //#if MC>=12106
        //$$ var gpuTextureView: GpuTextureView? = null
        //$$     set(value) {
        //$$         field?.close()
        //$$         field = value
        //$$     }
        //#endif
        //#else
        var glId: Int = -1
        //#endif

        //#if MC>=11400 && !STANDALONE
        //$$ var textureData: NativeImage? = null
        //$$    set(value) {
        //$$        field?.close()
        //$$        field = value
        //$$    }
        //#endif

        init {
            toBeCleanedUp.add(this)
        }

        override fun close() {
            toBeCleanedUp.remove(this)

            //#if MC>=12105 && !STANDALONE
            //$$ gpuTexture = null
            //#if MC>=12106
            //$$ gpuTextureView = null
            //#endif
            //#else
            if (glId != -1) {
                UGraphics.deleteTexture(glId)
                glId = -1
            }
            //#endif

            //#if MC>=11400 && !STANDALONE
            //$$ textureData = null
            //#endif
        }

        companion object {
            val referenceQueue: ReferenceQueue<ReleasedDynamicTexture> = ReferenceQueue()
            val toBeCleanedUp: MutableSet<Resources> = Collections.newSetFromMap(ConcurrentHashMap())

            fun drainCleanupQueue() {
                while (true) {
                    ((referenceQueue.poll() ?: break) as Resources).close()
                }
            }
        }
    }
}
