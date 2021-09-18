package gg.essential.universal.vertex

import gg.essential.universal.UMatrixStack
import java.awt.Color

interface UVertexConsumer {
    fun pos(stack: UMatrixStack, x: Double, y: Double, z: Double): UVertexConsumer

    fun color(red: Int, green: Int, blue: Int, alpha: Int): UVertexConsumer

    fun color(red: Float, green: Float, blue: Float, alpha: Float): UVertexConsumer {
        return color((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt(), (alpha * 255).toInt())
    }

    fun color(color: Color): UVertexConsumer {
        return color(color.red, color.green, color.blue, color.alpha)
    }

    fun tex(u: Double, v: Double): UVertexConsumer

    fun overlay(u: Int, v: Int): UVertexConsumer

    fun light(u: Int, v: Int): UVertexConsumer

    fun norm(stack: UMatrixStack, x: Float, y: Float, z: Float): UVertexConsumer

    fun endVertex(): UVertexConsumer

    companion object {
        @JvmStatic
        fun of(
            //#if MC>=11600
            //$$ wrapped: com.mojang.blaze3d.vertex.IVertexBuilder,
            //#else
            wrapped: net.minecraft.client.renderer.WorldRenderer,
            //#endif
        ): UVertexConsumer = VanillaVertexConsumer(wrapped)
    }
}
