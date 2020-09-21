package club.sk1er.mods.core.universal.message;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

//#if FABRIC
//$$ import net.minecraft.CharacterVisitor;
//$$ import net.minecraft.text.*;
//$$ import net.minecraft.util.Formatting;
//$$
//$$ import java.util.function.UnaryOperator;
//#else
//#if MC<=10809
import java.util.List;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatStyle;

import java.util.Iterator;
//#else
//$$ import net.minecraft.util.text.*;
//$$ import net.minecraft.util.text.event.*;
//#if MC>=11602
//$$ import net.minecraft.util.IReorderingProcessor;
//#endif
//#endif
//#endif

//#if MC<=10809
public class UniversalTextComponent implements IChatComponent {
//#else
//#if FORGE
//$$ public class UniversalTextComponent implements ITextComponent {
//#else
//$$ public class UniversalTextComponent implements MutableText {
//#endif
//#endif

    //#if MC<=10809
    private IChatComponent component;
    //#else
    //#if FORGE
    //$$ private ITextComponent component;
    //#else
    //$$ private MutableText component;
    //#endif
    //#endif

    private String text;
    private boolean formatted = true;

    private ClickEvent.Action clickAction = null;
    private String clickValue = null;
    private HoverEvent.Action hoverAction = null;
    private Object hoverValue = null;

    public UniversalTextComponent(String text) {
        this.text = text;
        reInstance();
    }

    //#if MC<=10809
    public UniversalTextComponent(IChatComponent component) {
    //#else
    //#if FORGE
    //$$ public UniversalTextComponent(ITextComponent component) {
    //#else
    //$$ public UniversalTextComponent(MutableText component) {
    //#endif
    //#endif
        this.component = component;
        //#if MC<11602
        text = component.getFormattedText();
        //#else
        //#if FORGE
        //$$ FormattedTextBuilder builder = new FormattedTextBuilder();
        //$$ component.func_230439_a_(builder, Style.EMPTY);
        //$$ text = builder.getString();
        //#else
        //$$ FormattedTextBuilder builder = new FormattedTextBuilder();
        //$$ component.asOrderedText().accept(builder);
        //$$ text = builder.getString();
        //#endif
        //#endif

        //#if MC<=10809
        ClickEvent clickEvent = component.getChatStyle().getChatClickEvent();
        //#else
        //$$ ClickEvent clickEvent = component.getStyle().getClickEvent();
        //#endif

        if (clickEvent != null) {
            clickAction = clickEvent.getAction();
            clickValue = clickEvent.getValue();
        }

        //#if MC<=10809
        HoverEvent hoverEvent = component.getChatStyle().getChatHoverEvent();
        //#else
        //$$ HoverEvent hoverEvent = component.getStyle().getHoverEvent();
        //#endif
        if (hoverEvent != null) {
            hoverAction = hoverEvent.getAction();
            //#if FABRIC
            //$$ hoverValue = hoverEvent.getValue(hoverAction);
            //#else
            //#if MC<11602
            hoverValue = hoverEvent.getValue();
            //#else
            //$$ hoverValue = hoverEvent.getParameter(hoverAction);
            //#endif
            //#endif
        }
    }

    //#if MC<=10809
    public IChatComponent getComponent() {
    //#else
    //#if FORGE
    //$$ public ITextComponent getComponent() {
    //#else
    //$$ public MutableText getComponent() {
    //#endif
    //#endif
        return component;
    }

    public String getText() {
        return text;
    }

    public UniversalTextComponent setText(String text) {
        this.text = text;
        reInstance();
        return this;
    }

    public boolean isFormatted() {
        return formatted;
    }

    public UniversalTextComponent setFormatted(boolean formatted) {
        this.formatted = formatted;
        reInstance();
        return this;
    }

    public UniversalTextComponent setClick(ClickEvent.Action action, String value) {
        clickAction = action;
        clickValue = value;
        reInstance();
        return this;
    }

    public ClickEvent.Action getClickAction() {
        return clickAction;
    }

    public UniversalTextComponent setClickAction(ClickEvent.Action action) {
        clickAction = action;
        reInstance();
        return this;
    }

    public String getClickValue() {
        return clickValue;
    }

    public UniversalTextComponent setClickValue(String value) {
        clickValue = value;
        reInstance();
        return this;
    }

    public UniversalTextComponent setHover(HoverEvent.Action action, Object value) {
        hoverAction = action;
        hoverValue = value;
        reInstance();
        return this;
    }

    public HoverEvent.Action getHoverAction() {
        return hoverAction;
    }

    public UniversalTextComponent setHoverAction(HoverEvent.Action action) {
        hoverAction = action;
        reInstance();
        return this;
    }

    public Object getHoverValue() {
        return hoverValue;
    }

    public UniversalTextComponent setHoverValue(Object value) {
        hoverValue = value;
        reInstance();
        return this;
    }

    public void chat() {
        throw new UnsupportedOperationException("TODO");
    }

    public void actionBar() {
        throw new UnsupportedOperationException("TODO");
    }

    private void reInstance() {
        component = buildSimple(formatted ? UniversalChat.addColor(text) : text);

        reInstanceClick();
        reInstanceHover();
    }

    private void reInstanceClick() {
        if (clickAction == null || clickValue == null)
            return;

        setClickEventHelper(new ClickEvent(
                clickAction,
                formatted ? UniversalChat.addColor(clickValue) : clickValue
        ));
    }

    private void reInstanceHover() {
        if (hoverAction == null || hoverValue == null)
            return;

        //#if FABRIC
        //$$ if (hoverAction == HoverEvent.Action.SHOW_TEXT) {
        //$$     String str = hoverValue.toString();
        //$$     setHoverEventHelper(new HoverEvent(
        //$$             hoverAction,
        //$$             buildSimple(str)
        //$$     ));
        //$$ } else {
        //$$     setHoverEventHelper(new HoverEvent(hoverAction, hoverValue));
        //$$ }
        //#else
        setHoverEventHelper(new HoverEvent(
                hoverAction,
                buildSimple(formatted ? UniversalChat.addColor(hoverValue.toString()) : hoverValue.toString())
        ));
        //#endif
    }

    private void setClickEventHelper(ClickEvent event) {
        //#if MC<=10809
        component.getChatStyle().setChatClickEvent(event);
        //#else
        //#if FORGE
        //$$ component.getStyle().setClickEvent(event);
        //#else
        //$$ component.setStyle(component.getStyle().withClickEvent(event));
        //#endif
        //#endif
    }

    private void setHoverEventHelper(HoverEvent event) {
        //#if MC<=10809
        component.getChatStyle().setChatHoverEvent(event);
        //#else
        //#if FORGE
        //$$  component.getStyle().setHoverEvent(event);
        //#else
        //$$ component.setStyle(component.getStyle().withHoverEvent(event));
        //#endif
        //#endif
    }

    //#if FABRIC
    //$$ public static MutableText buildSimple(String in) {
    //$$     return new LiteralText(in);
    //#else
    //#if MC<=10809
    public static IChatComponent buildSimple(String in) {
        return new ChatComponentText(in);
    //#else
    //#if MC<=11202
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

    //#if MC>=11602
    //#if FORGE
    //$$ private class FormattedTextBuilder implements ITextProperties.IStyledTextAcceptor<Object> {
    //$$     private StringBuilder builder = new StringBuilder();
    //$$     private Style cachedStyle = null;
    //$$
    //$$     @Override
    //$$     public Optional<Object> accept(Style style, String string) {
    //$$         if (style != cachedStyle) {
    //$$             cachedStyle = style;
    //$$             builder.append(formatString(style));
    //$$         }
    //$$
    //$$         return Optional.empty();
    //$$     }
    //$$
    //$$     public String getString() {
    //$$         return builder.toString();
    //$$     }
    //$$
    //$$     private String formatString(Style style) {
    //$$         StringBuilder builder = new StringBuilder("§r");
    //$$         if (style.getBold())
    //$$             builder.append("§l");
    //$$         if (style.getItalic())
    //$$             builder.append("§o");
    //$$         if (style.getUnderlined())
    //$$             builder.append("§n");
    //$$         if (style.getStrikethrough())
    //$$             builder.append("§m");
    //$$         if (style.getObfuscated())
    //$$             builder.append("§k");
    //$$         Color color = style.getColor();
    //$$         if (color != null)
    //$$             builder.append(color.toString());
    //$$         return builder.toString();
    //$$     }
    //$$ }
    //#else
    //$$ private class FormattedTextBuilder implements CharacterVisitor {
    //$$     private StringBuilder builder = new StringBuilder();
    //$$     private Style cachedStyle = null;
    //$$
    //$$     @Override
    //$$     public boolean accept(int index, Style style, int codePoint) {
    //$$         if (style != cachedStyle) {
    //$$             cachedStyle = style;
    //$$             builder.append(formatString(style));
    //$$         }
    //$$         builder.append((char) codePoint);
    //$$         return true;
    //$$     }
    //$$
    //$$     public String getString() {
    //$$         return builder.toString();
    //$$     }
    //$$
    //$$     private String formatString(Style style) {
    //$$         StringBuilder builder = new StringBuilder("§r");
    //$$         if (style.isBold())
    //$$             builder.append("§l");
    //$$         if (style.isItalic())
    //$$             builder.append("§o");
    //$$         if (style.isUnderlined())
    //$$             builder.append("§n");
    //$$         if (style.isStrikethrough())
    //$$             builder.append("§m");
    //$$         if (style.isObfuscated())
    //$$             builder.append("§k");
    //$$         TextColor color = style.getColor();
    //$$         if (color != null)
    //$$             builder.append(color.toString());
    //$$         return builder.toString();
    //$$     }
    //$$ }
    //#endif
    //#endif

    // **********************
    // * METHOD DELEGATIONS *
    // **********************

    //#if MC<=10809
    @Override
    public IChatComponent setChatStyle(ChatStyle style) {
        return component.setChatStyle(style);
    }

    @Override
    public ChatStyle getChatStyle() {
        return component.getChatStyle();
    }

    @Override
    public IChatComponent appendText(String text) {
        return component.appendText(text);
    }

    @Override
    public IChatComponent appendSibling(IChatComponent component) {
        return this.component.appendSibling(component);
    }

    @Override
    public String getUnformattedTextForChat() {
        return component.getUnformattedTextForChat();
    }

    @Override
    public String getUnformattedText() {
        return component.getUnformattedText();
    }

    @Override
    public String getFormattedText() {
        return component.getFormattedText();
    }

    @Override
    public List<IChatComponent> getSiblings() {
        return component.getSiblings();
    }

    @Override
    public IChatComponent createCopy() {
        return component.createCopy();
    }

    @Override
    public Iterator<IChatComponent> iterator() {
        return component.iterator();
    }
    //#else
    //#if MC<=11202
    //$$ @Override
    //$$ public ITextComponent setStyle(Style style) {
    //$$     return component.setStyle(style);
    //$$ }
    //$$
    //$$ @Override
    //$$ public Style getStyle() {
    //$$     return component.getStyle();
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent appendText(String text) {
    //$$     return component.appendText(text);
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent appendSibling(ITextComponent component) {
    //$$     return component.appendSibling(component);
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getUnformattedComponentText() {
    //$$     return component.getUnformattedComponentText();
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getUnformattedText() {
    //$$     return component.getUnformattedText();
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getFormattedText() {
    //$$     return component.getFormattedText();
    //$$ }
    //$$
    //$$ @Override
    //$$ public List<ITextComponent> getSiblings() {
    //$$     return component.getSiblings();
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent createCopy() {
    //$$     return component.createCopy();
    //$$ }
    //$$
    //$$ @Override
    //$$ public Iterator<ITextComponent> iterator() {
    //$$     return component.iterator();
    //$$ }
    //#else
    //#if MC<=11502
    //$$ @Override
    //$$ public ITextComponent appendText(String text) {
    //$$     return component.appendText(text);
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getString() {
    //$$     return component.getString();
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getStringTruncated(int maxLen) {
    //$$     return component.getStringTruncated(maxLen);
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getFormattedText() {
    //$$     return component.getFormattedText();
    //$$ }
    //$$
    //$$ @Override
    //$$ public Stream<ITextComponent> func_212637_f() {
    //$$     return component.func_212637_f();
    //$$ }
    //$$
    //$$ @Override
    //$$ public Iterator<ITextComponent> iterator() {
    //$$     return component.iterator();
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent deepCopy() {
    //$$     return component.deepCopy();
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent applyTextStyle(Consumer<Style> styleConsumer) {
    //$$     return component.applyTextStyle(styleConsumer);
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent applyTextStyles(TextFormatting... colors) {
    //$$     return component.applyTextStyles(colors);
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent applyTextStyle(TextFormatting color) {
    //$$     return component.applyTextStyle(color);
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent setStyle(Style style) {
    //$$     return component.setStyle(style);
    //$$ }
    //$$
    //$$ @Override
    //$$ public Style getStyle() {
    //$$     return component.getStyle();
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent appendSibling(ITextComponent component) {
    //$$     return this.component.appendSibling(component);
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getUnformattedComponentText() {
    //$$     return component.getUnformattedComponentText();
    //$$ }
    //$$
    //$$ @Override
    //$$ public List<ITextComponent> getSiblings() {
    //$$     return component.getSiblings();
    //$$ }
    //$$
    //$$ @Override
    //$$ public Stream<ITextComponent> stream() {
    //$$     return component.stream();
    //$$ }
    //$$
    //$$ @Override
    //$$ public ITextComponent shallowCopy() {
    //$$     return component.shallowCopy();
    //$$ }
    //#else
    //#if FORGE
    //$$ @Override
    //$$ public String getString() {
    //$$     return component.getString();
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getStringTruncated(int maxLen) {
    //$$     return component.getStringTruncated(maxLen);
    //$$ }
    //$$
    //$$ @Override
    //$$ public <T> Optional<T> func_230439_a_(IStyledTextAcceptor<T> p_230439_1_, Style p_230439_2_) {
    //$$     return component.func_230439_a_(p_230439_1_, p_230439_2_);
    //$$ }
    //$$
    //$$ @Override
    //$$ public <T> Optional<T> func_230438_a_(ITextAcceptor<T> p_230438_1_) {
    //$$     return component.func_230438_a_(p_230438_1_);
    //$$ }
    //$$
    //$$ @Override
    //$$ public <T> Optional<T> func_230534_b_(IStyledTextAcceptor<T> p_230534_1_, Style p_230534_2_) {
    //$$     return component.func_230534_b_(p_230534_1_, p_230534_2_);
    //$$ }
    //$$
    //$$ @Override
    //$$ public <T> Optional<T> func_230533_b_(ITextAcceptor<T> p_230533_1_) {
    //$$     return component.func_230533_b_(p_230533_1_);
    //$$ }
    //$$
    //$$ @Override
    //$$ public Style getStyle() {
    //$$     return component.getStyle();
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getUnformattedComponentText() {
    //$$     return component.getUnformattedComponentText();
    //$$ }
    //$$
    //$$ @Override
    //$$ public List<ITextComponent> getSiblings() {
    //$$     return component.getSiblings();
    //$$ }
    //$$
    //$$ @Override
    //$$ public IFormattableTextComponent copyRaw() {
    //$$     return component.copyRaw();
    //$$ }
    //$$
    //$$ @Override
    //$$ public IFormattableTextComponent deepCopy() {
    //$$     return component.deepCopy();
    //$$ }
    //$$
    //$$ @Override
    //$$ public IReorderingProcessor func_241878_f() {
    //$$     return component.func_241878_f();
    //$$ }
    //#else
    //$$ @Override
    //$$ public MutableText append(String text) {
    //$$     return component.append(text);
    //$$ }
    //$$
    //$$ @Override
    //$$ public MutableText styled(UnaryOperator<Style> styleUpdater) {
    //$$     return component.styled(styleUpdater);
    //$$ }
    //$$
    //$$ @Override
    //$$ public MutableText fillStyle(Style styleOverride) {
    //$$     return component.fillStyle(styleOverride);
    //$$ }
    //$$
    //$$ @Override
    //$$ public MutableText formatted(Formatting... formattings) {
    //$$     return component.formatted(formattings);
    //$$ }
    //$$
    //$$ @Override
    //$$ public MutableText formatted(Formatting formatting) {
    //$$     return component.formatted(formatting);
    //$$ }
    //$$
    //$$ @Override
    //$$ public MutableText setStyle(Style style) {
    //$$     return component.setStyle(style);
    //$$ }
    //$$
    //$$ @Override
    //$$ public MutableText append(Text text) {
    //$$     return component.append(text);
    //$$ }
    //$$
    //$$ @Override
    //$$ public String getString() {
    //$$     return component.getString();
    //$$ }
    //$$
    //$$ @Override
    //$$ public String asTruncatedString(int length) {
    //$$     return component.asTruncatedString(length);
    //$$ }
    //$$
    //$$ @Override
    //$$ public <T> Optional<T> visit(StyledVisitor<T> styledVisitor, Style style) {
    //$$     return component.visit(styledVisitor, style);
    //$$ }
    //$$
    //$$ @Override
    //$$ public <T> Optional<T> visit(Visitor<T> visitor) {
    //$$     return component.visit(visitor);
    //$$ }
    //$$
    //$$ @Override
    //$$ public <T> Optional<T> visitSelf(StyledVisitor<T> visitor, Style style) {
    //$$     return component.visitSelf(visitor, style);
    //$$ }
    //$$
    //$$ @Override
    //$$ public <T> Optional<T> visitSelf(Visitor<T> visitor) {
    //$$     return component.visitSelf(visitor);
    //$$ }
    //$$
    //$$ @Override
    //$$ public Style getStyle() {
    //$$     return component.getStyle();
    //$$ }
    //$$
    //$$ @Override
    //$$ public String asString() {
    //$$     return component.asString();
    //$$ }
    //$$
    //$$ @Override
    //$$ public List<Text> getSiblings() {
    //$$     return component.getSiblings();
    //$$ }
    //$$
    //$$ @Override
    //$$ public MutableText copy() {
    //$$     return component.copy();
    //$$ }
    //$$
    //$$ @Override
    //$$ public MutableText shallowCopy() {
    //$$     return component.shallowCopy();
    //$$ }
    //$$
    //$$ @Override
    //$$ public OrderedText asOrderedText() {
    //$$     return component.asOrderedText();
    //$$ }
    //#endif
    //#endif
    //#endif
    //#endif
}
