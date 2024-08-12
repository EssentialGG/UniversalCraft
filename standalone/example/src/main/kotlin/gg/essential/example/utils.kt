package gg.essential.example

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.UIComponent
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.UIImage
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.inspector.Inspector
import gg.essential.elementa.constraints.ConstraintType
import gg.essential.elementa.constraints.resolution.ConstraintVisitor
import gg.essential.elementa.dsl.constrain
import gg.essential.elementa.dsl.pixels
import gg.essential.elementa.font.FontProvider
import gg.essential.elementa.layoutdsl.LayoutScope
import gg.essential.elementa.layoutdsl.Modifier
import gg.essential.elementa.layoutdsl.layoutAsBox
import gg.essential.elementa.layoutdsl.then
import gg.essential.elementa.state.BasicState
import gg.essential.elementa.state.State
import gg.essential.elementa.util.isInComponentTree
import gg.essential.universal.UMatrixStack
import gg.essential.universal.standalone.nanovg.NvgFont
import gg.essential.universal.standalone.nanovg.NvgFontFace
import java.awt.Color
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.div

val appBaseDir: Path by lazy {
    val dir = Path("build") / "app_run_dir"
    dir.createDirectories()
}

class LayoutDslScreen(
    block: LayoutScope.() -> Unit,
) : WindowScreen(ElementaVersion.V6) {
    init {
        window.layoutAsBox {
            block()
        }

        val inspector by lazy { Inspector(window).apply { parent = window } }
        window.onKeyType { typedChar, _ ->
            if (typedChar == '=') {
                if (inspector.isInComponentTree()) {
                    inspector.hide()
                } else {
                    inspector.unhide()
                }
            }
        }
    }
}

fun LayoutScope.geistText(text: String, modifier: Modifier = Modifier, size: Float = 16f, scale: Float = 1f, shadow: Boolean = true) =
    text(text, Fonts.GEIST_REGULAR(size).then(modifier), scale, shadow)

fun LayoutScope.text(text: String, modifier: Modifier = Modifier, scale: Float = 1f, shadow: Boolean = true) =
    text(BasicState(text), modifier, scale, shadow)

fun LayoutScope.text(text: State<String>, modifier: Modifier = Modifier, scale: Float = 1f, shadow: Boolean = true) =
    UIText(shadow = shadow).bindText(text).constrain { textScale = scale.pixels() }(modifier)

fun LayoutScope.image(path: String, modifier: Modifier = Modifier) =
    UIImage.ofResourceCached(path)(modifier)

operator fun NvgFontFace.invoke(size: Float): Modifier = Modifier.font(NvgFont(this@invoke, size))

fun Modifier.font(font: NvgFont): Modifier =
    font(NvgFontProvider(font))

fun Modifier.font(fontProvider: FontProvider): Modifier = then {
    val prevFontProvider = getFontProvider()
    setFontProvider(fontProvider)
    ; { setFontProvider(prevFontProvider) }
}

class NvgFontProvider(private val font: NvgFont) : FontProvider {

    /* Required by Elementa but unused for this type of constraint */
    override var cachedValue: FontProvider = this
    override var recalculate: Boolean = false
    override var constrainTo: UIComponent? = null

    override fun drawString(
        matrixStack: UMatrixStack,
        string: String,
        color: Color,
        x: Float,
        y: Float,
        originalPointSize: Float,
        scale: Float,
        shadow: Boolean,
        shadowColor: Color?
    ) = font.drawString(matrixStack, string, color, x, y, originalPointSize, scale, shadow, shadowColor)

    override fun getBaseLineHeight(): Float =
        font.getBaseLineHeight()

    override fun getBelowLineHeight(): Float =
        font.getBelowLineHeight()

    override fun getShadowHeight(): Float =
        font.getShadowHeight()

    override fun getStringHeight(string: String, pointSize: Float): Float =
        (font.getBaseLineHeight() + font.getBelowLineHeight()) * pointSize / 10f

    override fun getStringWidth(string: String, pointSize: Float): Float =
        font.getStringWidth(string, pointSize)

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) {
    }

}
