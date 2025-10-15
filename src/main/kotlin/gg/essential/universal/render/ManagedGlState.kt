//#if MC<12105 || STANDALONE
package gg.essential.universal.render

import gg.essential.universal.UGraphics
import gg.essential.universal.shader.BlendState
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import java.nio.ByteBuffer

//#if STANDALONE
//#else
import net.minecraft.client.renderer.GlStateManager
//#endif

internal class ManagedGlState(
    var depthTest: Boolean,
    var depthFunc: Int,
    var culling: Boolean,
    var colorLogicOp: Boolean,
    var colorLogicOpMode: Int,
    var blendState: BlendState,
    var colorMask: List<Boolean>,
    var depthMask: Boolean,
    var polygonOffset: Boolean,
    var polygonOffsetFactor: Float,
    var polygonOffsetUnits: Float,
    var shadeModel: Int,
    var alphaTest: Boolean,
    var alphaTestFunc: Int,
    var alphaTestRef: Float,
    var colorR: Float,
    var colorG: Float,
    var colorB: Float,
    var colorA: Float,
    val texture2DStates: MutableList<Boolean>,
) {
    constructor(other: ManagedGlState) : this(
        depthTest = other.depthTest,
        depthFunc = other.depthFunc,
        culling = other.culling,
        colorLogicOp = other.colorLogicOp,
        colorLogicOpMode = other.colorLogicOpMode,
        blendState = other.blendState,
        colorMask = other.colorMask,
        depthMask = other.depthMask,
        polygonOffset = other.polygonOffset,
        polygonOffsetFactor = other.polygonOffsetFactor,
        polygonOffsetUnits = other.polygonOffsetUnits,
        shadeModel = other.shadeModel,
        alphaTest = other.alphaTest,
        alphaTestFunc = other.alphaTestFunc,
        alphaTestRef = other.alphaTestRef,
        colorR = other.colorR,
        colorG = other.colorG,
        colorB = other.colorB,
        colorA = other.colorA,
        texture2DStates = other.texture2DStates.toMutableList(),
    )

    fun activate(curr: ManagedGlState, org: ManagedGlState, includingUnused: Boolean) {
        if (curr.depthTest != depthTest) {
            curr.depthTest = depthTest
            if (depthTest) {
                //#if STANDALONE
                //$$ GL11.glEnable(GL11.GL_DEPTH_TEST)
                //#else
                GlStateManager.enableDepth()
                //#endif
            } else {
                //#if STANDALONE
                //$$ GL11.glDisable(GL11.GL_DEPTH_TEST)
                //#else
                GlStateManager.disableDepth()
                //#endif
            }
        }
        if ((depthTest || includingUnused) && curr.depthFunc != depthFunc) {
            curr.depthFunc = depthFunc
            //#if STANDALONE
            //$$ GL11.glDepthFunc(depthFunc)
            //#else
            GlStateManager.depthFunc(depthFunc)
            //#endif
        }
        if (curr.culling != culling) {
            curr.culling = culling
            if (culling) {
                //#if STANDALONE
                //$$ GL11.glEnable(GL11.GL_CULL_FACE)
                //#else
                GlStateManager.enableCull()
                //#endif
            } else {
                //#if STANDALONE
                //$$ GL11.glDisable(GL11.GL_CULL_FACE)
                //#else
                GlStateManager.disableCull()
                //#endif
            }
        }
        if (curr.colorLogicOp != colorLogicOp) {
            curr.colorLogicOp = colorLogicOp
            if (colorLogicOp) {
                //#if STANDALONE
                //$$ GL11.glEnable(GL11.GL_COLOR_LOGIC_OP)
                //#else
                GlStateManager.enableColorLogic()
                //#endif
            } else {
                //#if STANDALONE
                //$$ GL11.glDisable(GL11.GL_COLOR_LOGIC_OP)
                //#else
                GlStateManager.disableColorLogic()
                //#endif
            }
        }
        if ((colorLogicOp || includingUnused) && curr.colorLogicOpMode != colorLogicOpMode) {
            curr.colorLogicOpMode = colorLogicOpMode
            //#if STANDALONE
            //$$ GL11.glLogicOp(colorLogicOpMode)
            //#elseif MC>=11600
            //$$ com.mojang.blaze3d.platform.GlStateManager.logicOp(colorLogicOpMode)
            //#else
            GlStateManager.colorLogicOp(colorLogicOpMode)
            //#endif
        }
        if (curr.blendState != blendState) {
            curr.blendState = blendState
            @Suppress("DEPRECATION")
            blendState.activate()
        }
        if (curr.colorMask != colorMask) {
            curr.colorMask = colorMask
            //#if STANDALONE
            //$$ GL11.glColorMask(colorMask[0], colorMask[1], colorMask[2], colorMask[3])
            //#else
            GlStateManager.colorMask(colorMask[0], colorMask[1], colorMask[2], colorMask[3])
            //#endif
        }
        if (curr.depthMask != depthMask) {
            curr.depthMask = depthMask
            //#if STANDALONE
            //$$ GL11.glDepthMask(depthMask)
            //#else
            GlStateManager.depthMask(depthMask)
            //#endif
        }
        if (curr.polygonOffset != polygonOffset) {
            curr.polygonOffset = polygonOffset
            if (polygonOffset) {
                //#if STANDALONE
                //$$ GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL)
                //#else
                GlStateManager.enablePolygonOffset()
                //#endif
            } else {
                //#if STANDALONE
                //$$ GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL)
                //#else
                GlStateManager.disablePolygonOffset()
                //#endif
            }
        }
        if ((polygonOffset || includingUnused) && curr.polygonOffsetFactor != polygonOffsetFactor || curr.polygonOffsetUnits != polygonOffsetUnits) {
            //#if STANDALONE
            //$$ GL11.glPolygonOffset(polygonOffsetFactor, polygonOffsetUnits)
            //#else
            GlStateManager.doPolygonOffset(polygonOffsetFactor, polygonOffsetUnits)
            //#endif
        }
        //#if MC>=11700
        //$$ @Suppress("UNUSED_VARIABLE") val unused = org
        //#else
        if (curr.shadeModel != shadeModel) {
            curr.shadeModel = shadeModel
            GlStateManager.shadeModel(shadeModel)
        }
        if (curr.alphaTest != alphaTest) {
            curr.alphaTest = alphaTest
            //#if MC==11602
            //$$ @Suppress("DEPRECATION")
            //#endif
            if (alphaTest) {
                GlStateManager.enableAlpha()
            } else {
                GlStateManager.disableAlpha()
            }
        }
        if ((alphaTest || includingUnused) && (curr.alphaTestFunc != alphaTestFunc || curr.alphaTestRef != alphaTestRef)) {
            curr.alphaTestRef = alphaTestRef
            //#if MC==11602
            //$$ @Suppress("DEPRECATION")
            //#endif
            GlStateManager.alphaFunc(alphaTestFunc, alphaTestRef)
        }
        if (curr.colorR != colorR || curr.colorG != colorG || curr.colorB != colorB && curr.colorA != colorA) {
            curr.colorR = colorR
            curr.colorG = colorG
            curr.colorB = colorB
            curr.colorA = colorA
            GlStateManager.color(colorR, colorG, colorB, colorA)
        }
        for ((index, wantEnabled) in texture2DStates.withIndex()) {
            val isEnabled = curr.texture2DStates.getOrNull(index)
            if (isEnabled == wantEnabled) continue
            UGraphics.configureTextureUnit(index) {
                if (isEnabled == null) {
                    org.texture2DStates += GL11.glIsEnabled(GL11.GL_TEXTURE_2D)
                    curr.texture2DStates += wantEnabled
                } else {
                    curr.texture2DStates[index] = wantEnabled
                }
                if (wantEnabled) {
                    GlStateManager.enableTexture2D()
                } else {
                    GlStateManager.disableTexture2D()
                }
            }
        }
        //#endif
    }

    companion object {
        fun active() = ManagedGlState(
            depthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST),
            depthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC),
            culling = GL11.glGetBoolean(GL11.GL_CULL_FACE),
            colorLogicOp = GL11.glGetBoolean(GL11.GL_COLOR_LOGIC_OP),
            colorLogicOpMode = GL11.glGetInteger(GL11.GL_LOGIC_OP_MODE),
            blendState = BlendState.active(),
            colorMask = glGetBooleans(GL11.GL_COLOR_WRITEMASK, 4),
            depthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK),
            polygonOffset = GL11.glGetBoolean(GL11.GL_POLYGON_OFFSET_FILL),
            polygonOffsetFactor = GL11.glGetFloat(GL11.GL_POLYGON_OFFSET_FACTOR),
            polygonOffsetUnits = GL11.glGetFloat(GL11.GL_POLYGON_OFFSET_UNITS),
            //#if MC>=11700
            //$$ shadeModel = 0,
            //$$ alphaTest = false,
            //$$ alphaTestFunc = 0,
            //$$ alphaTestRef = 0f,
            //$$ colorR = 0f,
            //$$ colorG = 0f,
            //$$ colorB = 0f,
            //$$ colorA = 0f,
            //#else
            shadeModel = GL11.glGetInteger(GL11.GL_SHADE_MODEL),
            alphaTest = GL11.glGetBoolean(GL11.GL_ALPHA_TEST),
            alphaTestFunc = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC),
            alphaTestRef = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF),
            colorR = run {
                //#if MC>=11600
                //$$ GL11.glGetFloatv(GL11.GL_CURRENT_COLOR, tmpFloatBuffer)
                //#else
                GL11.glGetFloat(GL11.GL_CURRENT_COLOR, tmpFloatBuffer)
                //#endif
                tmpFloatBuffer.get(0)
            },
            colorG = tmpFloatBuffer.get(1),
            colorB = tmpFloatBuffer.get(2),
            colorA = tmpFloatBuffer.get(3),
            //#endif
            texture2DStates = mutableListOf(), // populated on demand
        )

        // Note: LWJGL2 requires a buffer of 16 elements, even if the properties we query have fewer
        private val tmpByteBuffer = ByteBuffer.allocateDirect(16)
        private val tmpFloatBuffer = BufferUtils.createFloatBuffer(16)

        private fun glGetBooleans(param: Int, count: Int) =
            tmpByteBuffer
                //#if MC>=11600
                //$$ .also { GL11.glGetBooleanv(param, it) }
                //#else
                .also { GL11.glGetBoolean(param, it) }
                //#endif
                .let { buf -> List(count) { i -> buf.get(i) != 0.toByte() } }
    }
}
//#endif
