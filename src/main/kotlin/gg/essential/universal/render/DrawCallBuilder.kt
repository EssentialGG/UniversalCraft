package gg.essential.universal.render

import org.jetbrains.annotations.ApiStatus.NonExtendable

@NonExtendable
interface DrawCallBuilder {
    fun noScissor(): DrawCallBuilder
    fun scissor(x: Int, y: Int, width: Int, height: Int): DrawCallBuilder

    fun uniform(name: String, vararg values: Int): DrawCallBuilder
    fun uniform(name: String, vararg values: Float): DrawCallBuilder

    fun texture(name: String, textureGlId: Int): DrawCallBuilder
    fun texture(index: Int, textureGlId: Int): DrawCallBuilder
}
