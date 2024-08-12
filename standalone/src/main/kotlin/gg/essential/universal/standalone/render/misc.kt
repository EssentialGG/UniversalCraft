package gg.essential.universal.standalone.render

import dev.folomeev.kotgl.matrix.matrices.Mat4
import dev.folomeev.kotgl.matrix.matrices.mutables.orthogonal
import dev.folomeev.kotgl.matrix.matrices.mutables.transposeSelf
import gg.essential.universal.UResolution

internal val DEBUG_GL = System.getProperty("universalcraft.standalone.debug.gl", "false").toBooleanStrict()

internal fun createOrthoProjectionMatrix(): Mat4 {
    val scaleFactor = UResolution.scaleFactor.toFloat()
    return orthogonal(
        0f, UResolution.viewportWidth / scaleFactor,
        UResolution.viewportHeight / scaleFactor, 0f,
        0f, 10000f
    ).transposeSelf() // kotgl places translation in the last column, we use the last row
}
