package gg.essential.universal.shader

import gg.essential.universal.UGraphics.CommonVertexFormats

internal class ShaderTransformer(private val vertexFormat: CommonVertexFormats?, private val targetVersion: Int) {
    init {
        check(targetVersion in listOf(110, 130, 150))
    }

    val attributes = mutableListOf<String>()
    val samplers = mutableSetOf<String>()
    val uniforms = mutableMapOf<String, UniformType>()

    fun transform(originalSource: String): String {
        var source = originalSource

        source = source.replace("gl_ModelViewProjectionMatrix", "gl_ProjectionMatrix * gl_ModelViewMatrix")
        if (targetVersion >= 130) {
            source = source.replace("texture2D", "texture")
        }

        val replacements = mutableMapOf<String, String>()
        val transformed = mutableListOf<String>()
        transformed.add("#version $targetVersion")

        val frag = "gl_FragColor" in source
        val vert = !frag

        val attributeIn = if (targetVersion >= 130) "in" else "attribute"
        val varyingIn = if (targetVersion >= 130) "in" else "varying"
        val varyingOut = if (targetVersion >= 130) "out" else "varying"

        if (frag && targetVersion >= 130) {
            transformed.add("$varyingOut vec4 uc_FragColor;")
            replacements["gl_FragColor"] = "uc_FragColor"
        }

        if (vert && "gl_FrontColor" in source) {
            transformed.add("$varyingOut vec4 uc_FrontColor;")
            replacements["gl_FrontColor"] = "uc_FrontColor"
        }
        if (frag && "gl_Color" in source) {
            transformed.add("$varyingIn vec4 uc_FrontColor;")
            replacements["gl_Color"] = "uc_FrontColor"
        }

        fun replaceAttribute(newAttributes: MutableList<Pair<String, String>>, needle: String, type: String, replacementName: String = "uc_" + needle.substringAfter("_"), replacement: String = replacementName) {
            if (needle in source) {
                replacements[needle] = replacement
                newAttributes.add(replacementName to "$attributeIn $type $replacementName;")
            }
        }
        if (vert) {
            val newAttributes = mutableListOf<Pair<String, String>>()
            replaceAttribute(newAttributes, "gl_Vertex", "vec3", "uc_Position", replacement = "vec4(uc_Position, 1.0)")
            replaceAttribute(newAttributes, "gl_Color", "vec4")
            replaceAttribute(newAttributes, "gl_MultiTexCoord0.st", "vec2", "uc_UV0")
            replaceAttribute(newAttributes, "gl_MultiTexCoord1.st", "vec2", "uc_UV1")
            replaceAttribute(newAttributes, "gl_MultiTexCoord2.st", "vec2", "uc_UV2")

            if (vertexFormat != null) {
                //#if MC>=11700 && !STANDALONE
                //$$ newAttributes.sortedBy { vertexFormat.mc.shaderAttributes.indexOf(it.first.removePrefix("uc_")) }
                //$$     .forEach {
                //$$         attributes.add(it.first)
                //$$         transformed.add(it.second)
                //$$     }
                //#else
                newAttributes.forEach {
                    attributes.add(it.first)
                    transformed.add(it.second)
                }
                //#endif
            } else {
                newAttributes.forEach {
                    attributes.add(it.first)
                    transformed.add(it.second)
                }
            }
        }

        fun replaceUniform(needle: String, type: UniformType, replacementName: String, replacement: String = replacementName) {
            if (needle in source) {
                replacements[needle] = replacement
                if (replacementName !in uniforms) {
                    uniforms[replacementName] = type
                    transformed.add("uniform ${type.glslName} $replacementName;")
                }
            }
        }
        replaceUniform("gl_ModelViewMatrix", UniformType.Mat4, "ModelViewMat")
        replaceUniform("gl_ProjectionMatrix", UniformType.Mat4, "ProjMat")


        for (line in source.lines()) {
            transformed.add(when {
                line.startsWith("#version") -> continue
                line.startsWith("varying ") && targetVersion >= 130 -> (if (frag) "in " else "out ") + line.substringAfter("varying ")
                line.startsWith("uniform ") -> {
                    val (_, glslType, name) = line.trimEnd(';').split(" ")
                    if (glslType == "sampler2D") {
                        samplers.add(name)
                    } else {
                        uniforms[name] = UniformType.fromGlsl(glslType)
                    }
                    line
                }
                else -> replacements.entries.fold(line) { acc, (needle, replacement) -> acc.replace(needle, replacement) }
            })
        }

        return transformed.joinToString("\n")
    }
}

internal enum class UniformType(val typeName: String, val glslName: String, val default: IntArray) {
    Int1("int", "int", intArrayOf(0)),
    Float1("float", "float", intArrayOf(0)),
    Float2("float", "vec2", intArrayOf(0, 0)),
    Float3("float", "vec3", intArrayOf(0, 0, 0)),
    Float4("float", "vec4", intArrayOf(0, 0, 0, 0)),
    Mat2("matrix2x2", "mat2", intArrayOf(1, 0, 0, 1)),
    Mat3("matrix3x3", "mat3", intArrayOf(1, 0, 0, 0, 1, 0, 0, 0, 1)),
    Mat4("matrix4x4", "mat4", intArrayOf(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1)),
    ;

    companion object {
        fun fromGlsl(glslName: String): UniformType =
            values().find { it.glslName == glslName } ?: throw NoSuchElementException(glslName)
    }
}