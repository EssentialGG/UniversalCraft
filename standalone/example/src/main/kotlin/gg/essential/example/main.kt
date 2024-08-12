package gg.essential.example

import gg.essential.elementa.components.UIRoundedRectangle
import gg.essential.elementa.dsl.constrain
import gg.essential.elementa.font.DefaultFonts
import gg.essential.elementa.layoutdsl.*
import gg.essential.elementa.state.v2.State
import gg.essential.elementa.state.v2.mutableStateOf
import gg.essential.universal.UMinecraft
import gg.essential.universal.standalone.runUniversalCraft
import gg.essential.universal.UResolution
import gg.essential.universal.UScreen
import kotlinx.coroutines.launch
import java.awt.Color

fun main() = runUniversalCraft("Example", 1000, 600) { window ->
    val extraFontsLoaded = mutableStateOf(false)
    launch {
        Fonts.loadFallback()
        extraFontsLoaded.set(true)
    }

    UMinecraft.guiScale = 2 * (UResolution.viewportWidth / UResolution.windowWidth)
    UScreen.displayScreen(LayoutDslScreen { exampleScreen(extraFontsLoaded) })

    window.renderScreenUntilClosed()
}

fun LayoutScope.exampleScreen(extraFontsLoaded: State<Boolean>) {
    column(Arrangement.spacedBy(10f)) {
        image("/100px-Tabby_cat_with_blue_eyes-3336579.jpg", Modifier.width(50f).height(60f))
        row(Arrangement.spacedBy(5f)) {
            box(Modifier.width(100f).height(20f).color(Color.CYAN).hoverColor(Color.BLUE).hoverScope())
            UIRoundedRectangle(10f)(Modifier.width(100f).height(20f).color(Color.RED))
            box(Modifier.width(100f).height(20f).color(Color.CYAN).hoverColor(Color.BLUE).hoverScope())
        }
        row(Arrangement.spacedBy(20f)) {
            box(Modifier.color(Color.DARK_GRAY)) {
                text("Hello, MinecraftFive!").constrain {
                    fontProvider = DefaultFonts.MINECRAFT_FIVE
                }
            }
            row {
                column {
                    repeat(4) {
                        box(Modifier.width(1f).height(1f).color(Color.RED))
                        box(Modifier.width(1f).height(1f).color(Color.GREEN))
                    }
                }
                box(Modifier.color(Color.DARK_GRAY)) {
                    text("Hello, NanoVG!")
                }
            }
            text("Hello, Color!", Modifier.color(Color.RED))
        }
        box(Modifier.color(Color.DARK_GRAY)) {
            text("Geist Regular 32", Fonts.GEIST_REGULAR(32f))
        }
        box(Modifier.color(Color.DARK_GRAY)) {
            text("Geist Regular 16", Fonts.GEIST_REGULAR(16f))
        }
        if_(extraFontsLoaded) {
            row(Arrangement.spacedBy(20f)) {
                geistText("Olá Mundo")
                geistText("Chào thế giới!")
                geistText("здравствуй, мир")
            }
            row(Arrangement.spacedBy(20f)) {
                geistText("こんにちは世界")
                geistText("হ্যালো, ওয়ার্ল্ড!")
                geistText("أهلا بالعالم")
            }
        } `else` {
            row(Modifier.height(17f)) {
                text("Loading extra fonts...")
            }
            row(Modifier.height(17f)) {}
        }
        box(Modifier.childBasedSize(5f).color(Color.GRAY).hoverColor(Color.LIGHT_GRAY).hoverScope()) {
            geistText("Quit")
        }.onMouseClick {
            UScreen.displayScreen(null)
        }
    }
    text("Press `=` to open the Inspector.", Modifier.alignHorizontal(Alignment.End(5f)).alignVertical(Alignment.Start(5f)))
}
