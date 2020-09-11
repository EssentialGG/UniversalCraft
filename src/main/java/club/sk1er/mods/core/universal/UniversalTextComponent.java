package club.sk1er.mods.core.universal;

//#if FABRIC
//$$ import net.minecraft.text.LiteralText;
//$$ import net.minecraft.text.Text;
//$$ import net.minecraft.util.Formatting;
//#else
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
//#endif

public class UniversalTextComponent {
    //#if FABRIC
    //$$ public static Text buildSimple(String in) {
    //$$     return new LiteralText(in);
    //#else
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
    //#endif
    }

    public static String getTextWithoutFormattingCodes(String in) {
        //#if FABRIC
        //$$ return Formatting.strip(in);
        //#else
        //#if MC<=10809
        return EnumChatFormatting.getTextWithoutFormattingCodes(in);
        //#else
        //$$ return TextFormatting.getTextWithoutFormattingCodes(in);
        //#endif
        //#endif
    }
}
