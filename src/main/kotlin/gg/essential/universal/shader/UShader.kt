package gg.essential.universal.shader

//#if MC>=11700
//$$ import net.minecraft.client.render.Shader
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
        fun fromLegacyShader(vertSource: String, fragSource: String, blendState: BlendState): UShader {
            //#if MC>=11700
            //$$ return MCShader.fromLegacyShader(vertSource, fragSource, blendState)
            //#else
            return GlShader(vertSource, fragSource, blendState)
            //#endif
        }

        //#if MC>=11700
        //$$ fun fromMcShader(shader: Shader, blendState: BlendState): UShader {
        //$$     return MCShader(shader, blendState)
        //$$ }
        //#endif
    }
}
