package gg.essential.universal.utils

//#if FABRIC
//$$ typealias MCMinecraft = net.minecraft.client.MinecraftClient
//$$ typealias MCSettings = net.minecraft.client.options.GameOptions
//$$ typealias MCWorld = net.minecraft.client.world.ClientWorld
//$$ typealias MCFontRenderer = net.minecraft.client.font.TextRenderer
//$$ typealias MCScreen = net.minecraft.client.gui.screen.Screen
//$$ typealias MCChatScreen = net.minecraft.client.gui.screen.ChatScreen
//$$ typealias MCEntityPlayerSP = net.minecraft.client.network.ClientPlayerEntity
//$$ typealias MCClientNetworkHandler = net.minecraft.client.network.ClientPlayNetworkHandler
//$$ typealias MCSChatPacket = net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
//$$ typealias MCMainMenuScreen = net.minecraft.client.gui.screen.TitleScreen
//$$ typealias MCITextComponent = net.minecraft.text.MutableText
//$$ typealias MCStringTextComponent = net.minecraft.text.LiteralText
//$$ typealias MCClickEvent = net.minecraft.text.ClickEvent
//$$ typealias MCHoverEvent = net.minecraft.text.HoverEvent
//$$ typealias MCClickEventAction = net.minecraft.text.ClickEvent.Action
//$$ typealias MCHoverEventAction = net.minecraft.text.HoverEvent.Action<*>
//$$ typealias MCButton = net.minecraft.client.gui.widget.AbstractButtonWidget
//#else
typealias MCMinecraft = net.minecraft.client.Minecraft
typealias MCFontRenderer = net.minecraft.client.gui.FontRenderer

//#if MC>=11202
//$$ typealias MCITextComponent = net.minecraft.util.text.ITextComponent
//$$ typealias MCClickEvent = net.minecraft.util.text.event.ClickEvent
//$$ typealias MCHoverEvent = net.minecraft.util.text.event.HoverEvent
//$$ typealias MCClickEventAction = net.minecraft.util.text.event.ClickEvent.Action
//#else
typealias MCITextComponent = net.minecraft.util.IChatComponent
typealias MCClickEvent = net.minecraft.event.ClickEvent
typealias MCHoverEvent = net.minecraft.event.HoverEvent
typealias MCClickEventAction = net.minecraft.event.ClickEvent.Action
//#endif

//#if MC>=11502
//$$ typealias MCSettings = net.minecraft.client.GameSettings
//$$ typealias MCWorld = net.minecraft.client.world.ClientWorld
//$$ typealias MCEntityPlayerSP = net.minecraft.client.entity.player.ClientPlayerEntity
//$$ typealias MCChatScreen = net.minecraft.client.gui.NewChatGui
//$$ typealias MCScreen = net.minecraft.client.gui.screen.Screen
//$$ typealias MCClientNetworkHandler = net.minecraft.client.network.play.ClientPlayNetHandler
//$$ typealias MCMainMenuScreen = net.minecraft.client.gui.screen.MainMenuScreen
//$$ typealias MCSChatPacket = net.minecraft.network.play.server.SChatPacket
//#else
//#if MC==10710
//$$ typealias MCEntityPlayerSP = net.minecraft.client.entity.EntityClientPlayerMP
//#else
typealias MCEntityPlayerSP = net.minecraft.client.entity.EntityPlayerSP
//#endif
typealias MCSettings = net.minecraft.client.settings.GameSettings
typealias MCWorld = net.minecraft.client.multiplayer.WorldClient
typealias MCScreen = net.minecraft.client.gui.GuiScreen
typealias MCChatScreen = net.minecraft.client.gui.GuiNewChat
typealias MCMainMenuScreen = net.minecraft.client.gui.GuiMainMenu
typealias MCClientNetworkHandler = net.minecraft.client.network.NetHandlerPlayClient
//#endif

//#if MC>=11502
//$$ typealias MCButton = net.minecraft.client.gui.widget.button.Button
//#else
typealias MCButton = net.minecraft.client.gui.GuiButton
//#endif

//#if MC>=11602
//$$ typealias MCHoverEventAction = net.minecraft.util.text.event.HoverEvent.Action<*>
//$$ typealias MCStringTextComponent = net.minecraft.util.text.StringTextComponent
//#elseif MC>=11502
//$$ typealias MCHoverEventAction = net.minecraft.util.text.event.HoverEvent.Action
//$$ typealias MCStringTextComponent = net.minecraft.util.text.StringTextComponent
//#elseif MC>=11202
//$$ typealias MCHoverEventAction = net.minecraft.util.text.event.HoverEvent.Action
//$$ typealias MCStringTextComponent = net.minecraft.util.text.TextComponentString
//$$ typealias MCSChatPacket = net.minecraft.network.play.server.SPacketChat
//#else
typealias MCStringTextComponent = net.minecraft.util.ChatComponentText
typealias MCSChatPacket = net.minecraft.network.play.server.S02PacketChat
typealias MCHoverEventAction = net.minecraft.event.HoverEvent.Action
//#endif

//#endif
