package gg.essential.universal.standalone.utils

import dev.folomeev.kotgl.matrix.matrices.Mat3
import dev.folomeev.kotgl.matrix.matrices.Mat4
import dev.folomeev.kotgl.matrix.matrices.mat3
import dev.folomeev.kotgl.matrix.matrices.mat4
import dev.folomeev.kotgl.matrix.vectors.Vec3
import dev.folomeev.kotgl.matrix.vectors.Vec4
import dev.folomeev.kotgl.matrix.vectors.mutables.MutableVec3
import dev.folomeev.kotgl.matrix.vectors.mutables.MutableVec4
import dev.folomeev.kotgl.matrix.vectors.mutables.set
import dev.folomeev.kotgl.matrix.vectors.vec3
import dev.folomeev.kotgl.matrix.vectors.vec4

inline fun <T> Vec3.times(mat: Mat3, out: (Float, Float, Float) -> T) =
    out(
        x * mat.m00 + y * mat.m01 + z * mat.m02,
        x * mat.m10 + y * mat.m11 + z * mat.m12,
        x * mat.m20 + y * mat.m21 + z * mat.m22,
    )

inline fun <T> Vec4.times(mat: Mat4, out: (Float, Float, Float, Float) -> T) =
    out(
        x * mat.m00 + y * mat.m01 + z * mat.m02 + w * mat.m03,
        x * mat.m10 + y * mat.m11 + z * mat.m12 + w * mat.m13,
        x * mat.m20 + y * mat.m21 + z * mat.m22 + w * mat.m23,
        x * mat.m30 + y * mat.m31 + z * mat.m32 + w * mat.m33,
    )

/**
 * Computes the matrix-vector product `mat * this`.
 */
fun Vec3.times(mat: Mat3) = times(mat, ::vec3)

/**
 * Computes the matrix-vector product `mat * this`.
 */
fun Vec4.times(mat: Mat4) = times(mat, ::vec4)

/**
 * Computes the matrix-vector product `mat * this`, storing the result in `this`.
 */
fun MutableVec3.timesSelf(mat: Mat3) = times(mat, ::set)

/**
 * Computes the matrix-vector product `mat * this`, storing the result in `this`.
 */
fun MutableVec4.timesSelf(mat: Mat4) = times(mat, ::set)

fun Mat4.toMat3() = mat3(m00, m01, m02, m10, m11, m12, m20, m21, m22)
fun Mat3.toMat4() = mat4(m00, m01, m02, 0f, m10, m11, m12, 0f, m20, m21, m22, 0f, 0f, 0f, 0f, 1f)

fun FloatArray.toMat4() = mat4 { row, col -> this[row * 4 + col] }

fun Mat4.toRowMajor() =
    floatArrayOf(
        m00,
        m01,
        m02,
        m03,
        m10,
        m11,
        m12,
        m13,
        m20,
        m21,
        m22,
        m23,
        m30,
        m31,
        m32,
        m33,
    )

fun Mat4.toColumnMajor() =
    floatArrayOf(
        m00,
        m10,
        m20,
        m30,
        m01,
        m11,
        m21,
        m31,
        m02,
        m12,
        m22,
        m32,
        m03,
        m13,
        m23,
        m33,
    )
