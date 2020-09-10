package club.sk1er.mods.core.universal;

//#if MC<=10809
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
//#else
//$$ import net.minecraft.util.text.TextComponentString;
//$$ import net.minecraft.util.text.TextFormatting;
//$$ import net.minecraft.util.text.ITextComponent;
//#endif

//#if MC>=11602
//$$ import net.minecraft.util.text.StringTextComponent;
//#endif

public class UniversalTextComponent {
    //#if MC<=10809
    public static IChatComponent buildSimple(String in) {
        return new ChatComponentText(in);
    //#else
    //#if MC<11602
    //$$ public static ITextComponent buildSimple(String in) {
    //$$     return new TextComponentString(in);
    //#else
    //$$ public static ITextComponent buildSimple(String in) {
    //$$     return new StringTextComponent(in);
    //#endif
    //#endif
    }

    public static String getTextWithoutFormattingCodes(String in) {
        //#if MC<=10809
        return EnumChatFormatting.getTextWithoutFormattingCodes(in);
        //#else
        //$$ return TextFormatting.getTextWithoutFormattingCodes(in);
        //#endif
    }
}
