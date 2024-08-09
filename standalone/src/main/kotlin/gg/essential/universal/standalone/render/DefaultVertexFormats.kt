package gg.essential.universal.standalone.render

import gg.essential.universal.standalone.render.VertexFormat.Part
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
enum class DefaultVertexFormats(vararg parts: Part) : VertexFormat {
    POSITION(Part.POSITION),
    POSITION_COLOR(Part.POSITION, Part.COLOR),
    POSITION_TEX(Part.POSITION, Part.TEXTURE),
    POSITION_TEX_COLOR(Part.POSITION, Part.TEXTURE, Part.COLOR),
    BLOCK(Part.POSITION, Part.COLOR, Part.TEXTURE, Part.LIGHT),
    POSITION_TEX_LMAP_COLOR(Part.POSITION, Part.TEXTURE, Part.LIGHT, Part.COLOR),
    PARTICLE_POSITION_TEX_COLOR_LMAP(Part.POSITION, Part.TEXTURE, Part.COLOR, Part.LIGHT),
    POSITION_TEX_COLOR_NORMAL(Part.POSITION, Part.TEXTURE, Part.COLOR, Part.NORMAL),
    ;

    override val parts: List<Part> = listOf(*parts)
    override val stride: Int = parts.sumOf { it.size }
}
