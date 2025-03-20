package gg.essential.universal.shader

import gg.essential.universal.UGraphics

//#if STANDALONE
//$$ import gg.essential.universal.standalone.render.VertexFormat.Part
//$$ import org.lwjgl.opengl.GL
//$$ import org.lwjgl.opengl.GL20C
//#elseif MC>=11700
//$$ import net.minecraft.client.render.Shader
//#endif

//#if MC<11600
@JvmDefaultWithCompatibility
//#endif
interface UShader {
    val usable: Boolean

    fun bind()
    fun unbind()

    fun getIntUniformOrNull(name: String): IntUniform?
    fun getFloatUniformOrNull(name: String): FloatUniform?
    fun getFloat2UniformOrNull(name: String): Float2Uniform?
    fun getFloat3UniformOrNull(name: String): Float3Uniform?
    fun getFloat4UniformOrNull(name: String): Float4Uniform?
    fun getFloatMatrixUniformOrNull(name: String): FloatMatrixUniform?
    fun getSamplerUniformOrNull(name: String): SamplerUniform?

    fun getIntUniform(name: String): IntUniform = getIntUniformOrNull(name) ?: throw NoSuchElementException(name)
    fun getFloatUniform(name: String): FloatUniform = getFloatUniformOrNull(name) ?: throw NoSuchElementException(name)
    fun getFloat2Uniform(name: String): Float2Uniform = getFloat2UniformOrNull(name) ?: throw NoSuchElementException(name)
    fun getFloat3Uniform(name: String): Float3Uniform = getFloat3UniformOrNull(name) ?: throw NoSuchElementException(name)
    fun getFloat4Uniform(name: String): Float4Uniform = getFloat4UniformOrNull(name) ?: throw NoSuchElementException(name)
    fun getFloatMatrixUniform(name: String): FloatMatrixUniform = getFloatMatrixUniformOrNull(name) ?: throw NoSuchElementException(name)
    fun getSamplerUniform(name: String): SamplerUniform = getSamplerUniformOrNull(name) ?: throw NoSuchElementException(name)

    companion object {
        @Deprecated(
            "Use the overload which takes a vertex format to ensure proper operation on all versions.",
            replaceWith = ReplaceWith("UShader.fromLegacyShader(vertSource, fragSource, blendState, vertexFormat)")
        )
        fun fromLegacyShader(vertSource: String, fragSource: String, blendState: BlendState): UShader {
            //#if STANDALONE
            //$$ return fromLegacyShader(vertSource, fragSource, blendState, UGraphics.CommonVertexFormats.POSITION_COLOR)
            //#elseif MC>=11700
            //$$ return MCShader.fromLegacyShader(vertSource, fragSource, blendState, null)
            //#else
            return GlShader(vertSource, fragSource, blendState) {}
            //#endif
        }

        fun fromLegacyShader(vertSource: String, fragSource: String, blendState: BlendState, vertexFormat: UGraphics.CommonVertexFormats): UShader {
            //#if STANDALONE
            //$$ return fromLegacyShader(vertSource, fragSource, blendState, vertexFormat.mc.parts)
            //#elseif MC>=11700
            //$$ return MCShader.fromLegacyShader(vertSource, fragSource, blendState, vertexFormat)
            //#else
            @Suppress("UNUSED_EXPRESSION") vertexFormat // only relevant to MCShader
            return GlShader(vertSource, fragSource, blendState) {}
            //#endif
        }

        //#if STANDALONE
        //$$ internal fun fromLegacyShader(vertSource: String, fragSource: String, blendState: BlendState, attributes: List<Part>): UShader {
        //$$     val caps = GL.getCapabilities()
        //$$     val targetGlslVersion = when {
        //$$         caps.OpenGL32 -> 150
        //$$         caps.OpenGL30 -> 130
        //$$         else -> 110
        //$$     }
        //$$     val transformer = ShaderTransformer(null, targetGlslVersion)
        //$$     return GlShader(transformer.transform(vertSource), transformer.transform(fragSource), blendState) { program ->
        //$$         for ((index, attribute) in attributes.withIndex()) {
        //$$             GL20C.glBindAttribLocation(program, index, when (attribute) {
        //$$                 Part.POSITION -> "uc_Position"
        //$$                 Part.TEXTURE -> "uc_UV0"
        //$$                 Part.COLOR -> "uc_Color"
        //$$                 Part.LIGHT -> "uc_UV1"
        //$$                 Part.NORMAL -> "uc_Normal"
        //$$             })
        //$$         }
        //$$     }
        //$$ }
        //#elseif MC>=11700
        //$$ fun fromMcShader(shader: Shader, blendState: BlendState): UShader {
        //$$     return MCShader(shader, blendState)
        //$$ }
        //#endif
    }
}
