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


import java.io.Closeable
import java.io.IOException
import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class ReleasedDynamicTexture private constructor(
    val width: Int,
    val height: Int,
    //#if MC>=11400
    //$$ textureData: NativeImage?,
    //#else
    textureData: IntArray?,
    //#endif
) : AbstractTexture() {

    private var resources = Resources(this)

    //#if MC>=11400
    //$$ init {
    //$$     resources.textureData = textureData ?: NativeImage(width, height, true)
    //$$ }
    //$$ private var textureData by resources::textureData
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

            resources.glId = allocGlId()
            Resources.drainCleanupQueue()
        }
    }

    private fun allocGlId() = super.getGlTextureId()

    override fun getGlTextureId(): Int {
        uploadTexture()
        return super.getGlTextureId()
    }

    override fun deleteGlTexture() {
        super.deleteGlTexture()
        resources.glId = -1
    }

    //#if MC>=11600
    //$$ override fun close() {
    //$$     deleteGlTexture()
    //$$     resources.close()
    //$$ }
    //#endif

    private class Resources(referent: ReleasedDynamicTexture) : PhantomReference<ReleasedDynamicTexture>(referent, referenceQueue), Closeable {
        var glId: Int = -1
        //#if MC>=11400
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

            if (glId != -1) {
                UGraphics.deleteTexture(glId)
                glId = -1
            }

            //#if MC>=11400
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
