package gg.essential.universal.vertex

//#if STANDALONE
//$$ import gg.essential.universal.standalone.render.BufferBuilder as BuiltBuffer
//#elseif MC>=12100
//$$ import net.minecraft.client.render.BuiltBuffer
//#elseif MC>=11900
//$$ import net.minecraft.client.render.BufferBuilder.BuiltBuffer
//#else
import net.minecraft.client.renderer.WorldRenderer as BuiltBuffer
//#endif

internal interface UBuiltBufferInternal : UBuiltBuffer {
    val mc: BuiltBuffer
    fun closedExternally()
}
