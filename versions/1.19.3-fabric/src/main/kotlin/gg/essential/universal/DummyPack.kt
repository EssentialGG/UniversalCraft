package gg.essential.universal

import gg.essential.universal.shader.MCShader
import net.minecraft.resource.InputSupplier
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourcePack
import net.minecraft.resource.ResourceType
import net.minecraft.resource.metadata.ResourceMetadataReader
import net.minecraft.util.Identifier
import java.io.InputStream

/**
 * A dummy resource pack for use in [MCShader], since the [Resource] constructor
 * on 1.19.3+ requires a [ResourcePack] instead of a String name.
 */
internal object DummyPack : ResourcePack {
    override fun getName(): String = "__generated__"

    override fun close() {
        throw UnsupportedOperationException()
    }

    override fun openRoot(vararg segments: String?): InputSupplier<InputStream>? {
        throw UnsupportedOperationException()
    }

    override fun open(type: ResourceType?, id: Identifier?): InputSupplier<InputStream>? {
        throw UnsupportedOperationException()
    }

    override fun findResources(type: ResourceType?, namespace: String?, prefix: String?, consumer: ResourcePack.ResultConsumer?) {
        throw UnsupportedOperationException()
    }

    override fun getNamespaces(type: ResourceType?): MutableSet<String> {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> parseMetadata(metaReader: ResourceMetadataReader<T>?): T? {
        throw UnsupportedOperationException()
    }
}
