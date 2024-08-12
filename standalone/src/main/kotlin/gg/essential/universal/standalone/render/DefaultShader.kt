package gg.essential.universal.standalone.render

import gg.essential.universal.shader.BlendState
import gg.essential.universal.shader.UShader
import gg.essential.universal.standalone.render.VertexFormat.Part

internal class DefaultShader(val shader: UShader) {

    val uSampler = shader.getSamplerUniformOrNull("uSampler")

    companion object {
        private val cache = mutableMapOf<List<Part>, DefaultShader>()

        fun get(attributes: List<Part>): DefaultShader {
            return cache.getOrPut(attributes) {
                val texture = Part.TEXTURE in attributes
                val color = Part.COLOR in attributes
                val light = Part.LIGHT in attributes
                val normal = Part.NORMAL in attributes
                if (light || normal) {
                    TODO("Light and normals not yet supported.")
                }
                val shader = UShader.fromLegacyShader(
                    genVertexShaderSource(texture, color),
                    genFragmentShaderSource(texture, color),
                    BlendState.NORMAL,
                    attributes,
                )
                DefaultShader(shader)
            }
        }

        private fun genVertexShaderSource(texture: Boolean, color: Boolean): String {
            return """
            ${if (texture) "varying vec2 vTextureCoord;" else ""}
            ${if (color) "varying vec4 vColor;" else ""}

            void main() {
                gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * gl_Vertex;
                ${ if (texture) "vTextureCoord = gl_MultiTexCoord0.st;" else ""}
                ${ if (color) "vColor = gl_Color;" else ""}
            }
        """.trimIndent()
        }

        private fun genFragmentShaderSource(texture: Boolean, color: Boolean): String {
            return """
            ${if (texture) "uniform sampler2D uSampler;" else ""}

            ${if (texture) "varying vec2 vTextureCoord;" else ""}
            ${if (color) "varying vec4 vColor;" else ""}

            void main() {
                vec4 color = ${if (texture) "texture2D(uSampler, vTextureCoord)" else "vec4(1.0)"};
                ${if (color) "color *= vColor;" else ""}
                if (color.a == 0.0) {
                    discard;
                }
                gl_FragColor = color;
            }
        """.trimIndent()
        }
    }
}