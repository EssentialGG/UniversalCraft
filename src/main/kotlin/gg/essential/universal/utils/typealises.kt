package gg.essential.universal.utils

typealias MCMinecraft = net.minecraft.client.Minecraft
typealias MCFontRenderer = net.minecraft.client.gui.FontRenderer

typealias MCClickEventAction = net.minecraft.event.ClickEvent.Action
//#if MC>=12105
//$$ typealias MCHoverEventAction = net.minecraft.text.HoverEvent.Action
//#elseif MC>=11600
//$$ typealias MCHoverEventAction = net.minecraft.util.text.event.HoverEvent.Action<*>
//#else
typealias MCHoverEventAction = net.minecraft.event.HoverEvent.Action
//#endif
//#if MC>=11600
//$$ typealias MCIMutableText = net.minecraft.util.text.IFormattableTextComponent
//#else
typealias MCIMutableText = net.minecraft.util.IChatComponent
//#endif
typealias MCITextComponent = net.minecraft.util.IChatComponent
typealias MCClickEvent = net.minecraft.event.ClickEvent
typealias MCHoverEvent = net.minecraft.event.HoverEvent

typealias MCSettings = net.minecraft.client.settings.GameSettings
typealias MCWorld = net.minecraft.client.multiplayer.WorldClient
typealias MCEntityPlayerSP = net.minecraft.client.entity.EntityPlayerSP
typealias MCScreen = net.minecraft.client.gui.GuiScreen
typealias MCChatScreen = net.minecraft.client.gui.GuiNewChat
typealias MCMainMenuScreen = net.minecraft.client.gui.GuiMainMenu
typealias MCClientNetworkHandler = net.minecraft.client.network.NetHandlerPlayClient

//#if MC>=11502
//$$ typealias MCButton = net.minecraft.client.gui.widget.button.Button
//#else
typealias MCButton = net.minecraft.client.gui.GuiButton
//#endif

typealias MCStringTextComponent = net.minecraft.util.ChatComponentText
typealias MCSChatPacket = net.minecraft.network.play.server.S02PacketChat
