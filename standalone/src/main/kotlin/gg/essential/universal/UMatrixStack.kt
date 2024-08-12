package gg.essential.universal

import dev.folomeev.kotgl.matrix.matrices.identityMat3
import dev.folomeev.kotgl.matrix.matrices.identityMat4
import dev.folomeev.kotgl.matrix.matrices.mat3
import dev.folomeev.kotgl.matrix.matrices.mutables.MutableMat3
import dev.folomeev.kotgl.matrix.matrices.mutables.MutableMat4
import dev.folomeev.kotgl.matrix.matrices.mutables.set
import dev.folomeev.kotgl.matrix.matrices.mutables.timesSelf
import dev.folomeev.kotgl.matrix.matrices.mutables.toMutable
import gg.essential.universal.standalone.utils.toRowMajor
import gg.essential.universal.standalone.utils.toMat4
import java.util.*
import kotlin.math.PI
import kotlin.math.cbrt
import kotlin.math.cos
import kotlin.math.sin

/**
 * A stack of matrices which can be manipulated via common transformations, just like MC's MatrixStack.
 *
 * For MC versions 1.16 and above, methods exist to convert from (via the constructor) and to (via Entry.toMCStack) the
 * vanilla stack type if required.
 * For MC versions below 1.17, the *GlobalState methods can be used to transfer the state of this matrix stack into the
 * global GL state. For 1.17, they transfer state into Mojang's global MatrixStack in RenderSystem.
 */
class UMatrixStack private constructor(
    private val stack: MutableList<Entry>,
) {

    constructor() : this(mutableListOf(Entry(identityMat4().toMutable(), identityMat3().toMutable())))

    fun translate(x: Double, y: Double, z: Double) = translate(x.toFloat(), y.toFloat(), z.toFloat())

    fun translate(x: Float, y: Float, z: Float) {
        if (x == 0f && y == 0f && z == 0f) return
        stack.last().run {
            // kotgl's builtin translate functions put the translation in the wrong place (last row
            // instead of column)
            model.timesSelf(
                identityMat4().toMutable().apply {
                    m03 = x
                    m13 = y
                    m23 = z
                }
            )
        }
    }

    fun scale(x: Double, y: Double, z: Double) = scale(x.toFloat(), y.toFloat(), z.toFloat())

    fun scale(x: Float, y: Float, z: Float) {
        if (x == 1f && y == 1f && z == 1f) return
        return stack.last().run {
            // kotgl's builtin scale functions also scale the translate values
            model.timesSelf(
                identityMat4().toMutable().apply {
                    m00 = x
                    m11 = y
                    m22 = z
                }
            )
            if (x == y && y == z) {
                if (x < 0f) {
                    normal.timesSelf(-1f)
                }
            } else {
                val ix = 1f / x
                val iy = 1f / y
                val iz = 1f / z
                val rt = cbrt(ix * iy * iz)
                normal.timesSelf(
                    identityMat3().toMutable().apply {
                        m00 = rt * ix
                        m11 = rt * iy
                        m22 = rt * iz
                    }
                )
            }
        }
    }

    @JvmOverloads
    fun rotate(angle: Float, x: Float, y: Float, z: Float, degrees: Boolean = true) {
        if (angle == 0f) return
        stack.last().run {
            val angleRadians = if (degrees) (angle / 180 * PI).toFloat() else angle
            val c = cos(angleRadians)
            val s = sin(angleRadians)
            val oneMinusC = 1 - c
            val xx = x * x
            val xy = x * y
            val xz = x * z
            val yy = y * y
            val yz = y * z
            val zz = z * z
            val xs = x * s
            val ys = y * s
            val zs = z * s
            val rotation = mat3(
                xx * oneMinusC + c,
                xy * oneMinusC - zs,
                xz * oneMinusC + ys,
                xy * oneMinusC + zs,
                yy * oneMinusC + c,
                yz * oneMinusC - xs,
                xz * oneMinusC - ys,
                yz * oneMinusC + xs,
                zz * oneMinusC + c,
            )
            model.timesSelf(rotation.toMat4())
            normal.timesSelf(rotation)
        }
    }

    fun fork() = UMatrixStack(mutableListOf(stack.last().deepCopy()))

    fun push() {
        stack.add(stack.last().deepCopy())
    }

    fun pop() {
        stack.removeLast()
    }

    fun peek() = stack.last()

    fun isEmpty(): Boolean = stack.size == 1

    fun applyToGlobalState() {
        GLOBAL_STACK.stack.last().model.timesSelf(stack.last().model)
    }

    fun replaceGlobalState() {
        GLOBAL_STACK.stack.last().model.set(stack.last().model)
    }

    fun runWithGlobalState(block: Runnable) = runWithGlobalState { block.run() }

    fun <R> runWithGlobalState(block: () -> R): R  = withGlobalStackPushed {
        applyToGlobalState()
        block()
    }

    fun runReplacingGlobalState(block: Runnable) = runReplacingGlobalState { block.run() }

    fun <R> runReplacingGlobalState(block: () -> R): R = withGlobalStackPushed {
        replaceGlobalState()
        block()
    }

    private inline fun <R> withGlobalStackPushed(block: () -> R) : R {
        GLOBAL_STACK.push()
        return block().also {
            GLOBAL_STACK.pop()
        }
    }

    data class Entry(val model: MutableMat4, val normal: MutableMat3) {
        fun deepCopy() =
            Entry(model.copyOf(), normal.copyOf())

        /**
         * Returns the model matrix in row-major order.
         */
        val modelAsArray: FloatArray
            get() = model.toRowMajor()
    }

    object Compat {
        const val DEPRECATED = """For 1.17 this method requires you pass a UMatrixStack as the first argument.

If you are currently extending this method, you should instead extend the method with the added argument.
Note however for this to be non-breaking, your parent class needs to transition before you do.

If you are calling this method and you cannot guarantee that your target class has been fully updated (such as when
calling an open method on an open class), you should instead call the method with the "Compat" suffix, which will
call both methods, the new and the deprecated one.
If you are sure that your target class has been updated (such as when calling the super method), you should
(for super calls you must!) instead just call the method with the original name and added argument."""

        private val stack = mutableListOf<UMatrixStack>()

        /**
         * To preserve backwards compatibility with old subclasses of UScreen or similar hierarchies,
         * this method allows one to sneak in an artificial matrix stack argument when calling the legacy method
         * which can then later be retrieved via [get] when the base legacy method calls the new one.
         *
         * For an example see [UScreen.onDrawScreenCompat].
         */
        fun <R> runLegacyMethod(matrixStack: UMatrixStack, block: () -> R): R {
            stack.add(matrixStack)
            return block().also {
                stack.removeAt(stack.lastIndex)
            }
        }

        fun get(): UMatrixStack = stack.lastOrNull() ?: UMatrixStack()
    }

    companion object {
        @JvmField
        val GLOBAL_STACK = UMatrixStack()

        /**
         * Represents an empty matrix stack. That is, a stack with the identity matrix as its sole entry.
         *
         * This stack may be passed to consuming APIs which may then assume that the stack is in fact a unit stack and
         * can therefore skip math that would be redundant in such cases.
         *
         * **This stack must not be modified.**
         * Consumers may compare this stack by reference and ignore its content.
         * Consumers which are not aware of this stack must still behave correctly, so its content must be correct.
         * [fork] is fine, [push] is not!
         */
        @JvmField
        val UNIT = UMatrixStack()
    }
}