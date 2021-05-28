package gg.essential.universal

import net.minecraft.client.renderer.GLAllocation
import org.lwjgl.opengl.GL11
import org.lwjgl.util.vector.Matrix3f
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Quaternion
import org.lwjgl.util.vector.Vector3f
import java.nio.FloatBuffer
import java.util.*

//#if MC>=11600
//$$ import com.mojang.blaze3d.matrix.MatrixStack
//#endif

//#if MC>=11400
//$$ import net.minecraft.util.math.MathHelper
//#endif

/**
 * A stack of matrices which can be manipulated via common transformations, just like MC's MatrixStack.
 *
 * For MC versions 1.16 and above, methods exist to convert from (via the constructor) and to (via Entry.toMCStack) the
 * vanilla stack type if required.
 * For MC versions below 1.17, the *GlobalState methods can be used to transfer the state of this matrix stack into the
 * global GL state.
 */
class UMatrixStack private constructor(
    private val stack: Deque<Entry>
) {

    constructor() : this(ArrayDeque<Entry>().apply {
        add(Entry(
            Matrix4f().apply { setIdentity() },
            Matrix3f().apply { setIdentity() }
        ))
    })

    //#if MC>=11600
    //$$ constructor(mc: MatrixStack) : this(mc.last)
    //$$ constructor(mc: MatrixStack.Entry) : this(ArrayDeque<Entry>().apply {
    //$$     add(Entry(mc.matrix, mc.normal))
    //$$ })
    //$$ fun toMC() = peek().toMCStack()
    //#endif

    fun translate(x: Double, y: Double, z: Double) = translate(x.toFloat(), y.toFloat(), z.toFloat())

    fun translate(x: Float, y: Float, z: Float) {
        if (x == 0f && y == 0f && z == 0f) return
        stack.last.run {
            //#if MC>=11400
            //$$ model.mul(Matrix4f.makeTranslate(x, y, z))
            //#else
            Matrix4f.translate(Vector3f(x, y, z), model, model)
            //#endif
        }
    }

    fun scale(x: Double, y: Double, z: Double) = scale(x.toFloat(), y.toFloat(), z.toFloat())

    fun scale(x: Float, y: Float, z: Float) {
        if (x == 1f && y == 1f && z == 1f) return
        return stack.last.run {
            //#if MC>=11400
            //$$ model.mul(Matrix4f.makeScale(x, y, z))
            //#else
            Matrix4f.scale(Vector3f(x, y, z), model, model)
            //#endif
            if (x == y && y == z) {
                if (x < 0f) {
                    //#if MC>=11400
                    //$$ normal.mul(-1f)
                    //#else
                    Matrix3f.negate(normal, normal)
                    //#endif
                }
            } else {
                val ix = 1f / x
                val iy = 1f / y
                val iz = 1f / z
                //#if MC>=11400
                //$$ val rt = MathHelper.fastInvCubeRoot(ix * iy * iz)
                //#else
                val rt = Math.cbrt((ix * iy * iz).toDouble()).toFloat()
                //#endif
                //#if MC>=11400
                //$$ normal.mul(Matrix3f.makeScaleMatrix(rt * ix, rt * iy, rt * iz))
                //#else
                val scale = Matrix3f()
                scale.m00 = rt * ix
                scale.m11 = rt * iy
                scale.m22 = rt * iz
                Matrix3f.mul(normal, scale, normal)
                //#endif
            }
        }
    }

    @JvmOverloads
    fun rotate(angle: Float, x: Float, y: Float, z: Float, degrees: Boolean = true) {
        if (angle == 0f) return
        stack.last.run {
            //#if MC>=11400
            //$$ multiply(Quaternion(Vector3f(x, y, z), angle, degrees));
            //#else
            val angleRadians = if (degrees) Math.toRadians(angle.toDouble()).toFloat() else angle
            Matrix4f.rotate(angleRadians, Vector3f(x, y, z), model, model)
            // TODO there appears to be no method to rotate a Matrix3f, so I'll pass on that one for now (don't need it)
            //#endif
        }
    }

    fun multiply(quaternion: Quaternion): Unit = stack.last.run {
        //#if MC>=11400
        //$$ model.mul(quaternion)
        //$$ normal.mul(quaternion)
        //#else
        TODO("lwjgl quaternion multiply") // there seems to be no existing methods to do this
        //#endif
    }

    fun fork() = UMatrixStack(ArrayDeque<Entry>().apply {
        add(stack.last.deepCopy())
    })

    fun push(): Unit = stack.addLast(stack.last.deepCopy())

    fun pop() {
        stack.removeLast()
    }

    fun peek(): Entry = stack.last

    fun isEmpty(): Boolean = stack.size == 1

    fun applyToGlobalState() {
        stack.last.model.store(MATRIX_BUFFER)
        MATRIX_BUFFER.rewind()
        //#if MC>=11500
        //$$ GL11.glMultMatrixf(MATRIX_BUFFER)
        //#else
        GL11.glMultMatrix(MATRIX_BUFFER)
        //#endif
    }

    fun replaceGlobalState() {
        GL11.glLoadIdentity()
        applyToGlobalState()
    }

    fun runWithGlobalState(block: Runnable) = runWithGlobalState { block.run() }

    inline fun <R> runWithGlobalState(block: () -> R): R {
        UGraphics.GL.pushMatrix()
        applyToGlobalState()
        return block().also {
            UGraphics.GL.popMatrix()
        }
    }

    fun runReplacingGlobalState(block: Runnable) = runReplacingGlobalState { block.run() }

    inline fun <R> runReplacingGlobalState(block: () -> R): R {
        UGraphics.GL.pushMatrix()
        replaceGlobalState()
        return block().also {
            UGraphics.GL.popMatrix()
        }
    }

    data class Entry(val model: Matrix4f, val normal: Matrix3f) {
        //#if MC>=11600
        //$$ fun toMCStack() = MatrixStack().also {
        //$$     it.last.matrix.mul(model)
        //$$     it.last.normal.mul(normal)
        //$$ }
        //#endif

        fun deepCopy() =
            //#if MC>=11400
            //$$ Entry(model.copy(), normal.copy())
            //#else
            Entry(Matrix4f.load(model, null), Matrix3f.load(normal, null))
            //#endif
    }

    companion object {
        private val MATRIX_BUFFER: FloatBuffer = GLAllocation.createDirectFloatBuffer(16)
    }
}