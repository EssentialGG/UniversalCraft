package gg.essential.universal.standalone.render

import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
interface VertexFormat {
    val parts: List<Part>
    val stride: Int

    @ApiStatus.Internal
    enum class Part(val size: Int) {
        POSITION(4),
        TEXTURE(2),
        COLOR(4),
        LIGHT(2),
        NORMAL(3),
    }
}
