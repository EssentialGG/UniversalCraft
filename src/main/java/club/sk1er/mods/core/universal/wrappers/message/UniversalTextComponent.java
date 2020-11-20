package club.sk1er.mods.core.universal.wrappers.message;

import club.sk1er.mods.core.universal.UniversalChat;

//#if FABRIC
//$$ import net.minecraft.CharacterVisitor;
//$$ import net.minecraft.text.*;
//$$ import net.minecraft.util.Formatting;
//$$
//$$ import java.util.function.UnaryOperator;
//#elseif MC<=10809
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatStyle;
//#else
//$$ import net.minecraft.util.text.*;
//$$ import net.minecraft.util.text.event.*;
//#if MC>=11602
//$$ import net.minecraft.util.IReorderingProcessor;
//#endif
//#endif

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

//#if FABRIC
//$$ public class UniversalTextComponent implements MutableText {
//#elseif MC>=11202
//$$ public class UniversalTextComponent implements ITextComponent {
//#else
public class UniversalTextComponent implements IChatComponent {
//#endif

    //#if FABRIC
    //$$ private MutableText component;
    //#elseif MC>=11202
    //$$ private ITextComponent component;
    //#else
    private IChatComponent component;
    //#endif

    private String text;
    private boolean formatted = true;

    private ClickEvent.Action clickAction = null;
    private String clickValue = null;
    private HoverEvent.Action hoverAction = null;
    private Object hoverValue = null;

    public static Optional<UniversalTextComponent> from(Object object) {
        if (object instanceof UniversalTextComponent)
            return Optional.of((UniversalTextComponent) object);

        if (object instanceof String)
            object = buildSimple((String) object);

        //#if FABRIC
        //$$ if (object instanceof MutableText)
        //$$    return Optional.of(new UniversalTextComponent((MutableText) object));
        //#elseif MC>=11202
        //$$ if (object instanceof ITextComponent)
        //$$    return Optional.of(new UniversalTextComponent((ITextComponent) object));
        //#else
        if (object instanceof IChatComponent)
            return Optional.of(new UniversalTextComponent((IChatComponent) object));
        //#endif
        return Optional.empty();
    }

    public UniversalTextComponent(String text) {
        this.text = text;
        reInstance();
    }

    //#if FABRIC
    //$$ public UniversalTextComponent(MutableText component) {
    //#elseif MC>=11202
    //$$ public UniversalTextComponent(ITextComponent component) {
    //#else
    public UniversalTextComponent(IChatComponent component) {
    //#endif
        this.component = component;
        //#if FABRIC
        //$$ text = getFormattedText();
        //#elseif MC>=11602
        //$$ FormattedTextBuilder builder = new FormattedTextBuilder();
        //$$ component.func_230439_a_(builder, Style.EMPTY);
        //$$ text = builder.getString();
        //#else
        text = component.getFormattedText();
        //#endif

        //#if MC>=11202
        //$$ ClickEvent clickEvent = component.getStyle().getClickEvent();
        //#else
        ClickEvent clickEvent = component.getChatStyle().getChatClickEvent();
        //#endif

        if (clickEvent != null) {
            clickAction = clickEvent.getAction();
            clickValue = clickEvent.getValue();
        }

        //#if MC>=11202
        //$$ HoverEvent hoverEvent = component.getStyle().getHoverEvent();
        //#else
        HoverEvent hoverEvent = component.getChatStyle().getChatHoverEvent();
        //#endif
        if (hoverEvent != null) {
            hoverAction = hoverEvent.getAction();
            //#if FABRIC
            //$$ hoverValue = hoverEvent.getValue(hoverAction);
            //#elseif MC>=11602
            //$$ hoverValue = hoverEvent.getParameter(hoverAction);
            //#else
            hoverValue = hoverEvent.getValue();
            //#endif
        }
    }

    //#if FABRIC
    //$$ public MutableText getComponent() {
    //#elseif MC>=11202
    //$$ public ITextComponent getComponent() {
    //#else
    public IChatComponent getComponent() {
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
        //#if FABRIC
        //$$ component.setStyle(component.getStyle().withClickEvent(event));
        //#elseif MC>=11202
        //$$ component.getStyle().setClickEvent(event);
        //#else
        component.getChatStyle().setChatClickEvent(event);
        //#endif
    }

    private void setHoverEventHelper(HoverEvent event) {
        //#if FABRIC
        //$$ component.setStyle(component.getStyle().withHoverEvent(event));
        //#elseif MC>=11202
        //$$  component.getStyle().setHoverEvent(event);
        //#else
        component.getChatStyle().setChatHoverEvent(event);
        //#endif
    }

    //#if FABRIC
    //$$ public static MutableText buildSimple(String in) {
    //$$     return new LiteralText(in);
    //#elseif MC>=11502
    //$$ public static ITextComponent buildSimple(String in) {
    //$$     return new StringTextComponent(in);
    //#elseif MC>=11202
    //$$ public static ITextComponent buildSimple(String in) {
    //$$     return new TextComponentString(in);
    //#else
    public static IChatComponent buildSimple(String in) {
        return new ChatComponentText(in);
    //#endif
    }

    public static String getTextWithoutFormattingCodes(String in) {
        //#if FABRIC
        //$$ return Formatting.strip(in);
        //#elseif MC>=11202
        //$$ return TextFormatting.getTextWithoutFormattingCodes(in);
        //#else
        return EnumChatFormatting.getTextWithoutFormattingCodes(in);
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
    //$$ private class TextBuilder implements CharacterVisitor {
    //$$     private StringBuilder builder = new StringBuilder();
    //$$     private Style cachedStyle = null;
    //$$     private boolean isFormatted;
    //$$
    //$$     TextBuilder(boolean isFormatted) {
    //$$         this.isFormatted = formatted;
    //$$     }
    //$$
    //$$     @Override
    //$$     public boolean accept(int index, Style style, int codePoint) {
    //$$         if (formatted && style != cachedStyle) {
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

    //#if FABRIC
    //$$ public String getUnformattedText() {
    //$$     TextBuilder builder = new TextBuilder(false);
    //$$     component.asOrderedText().accept(builder);
    //$$     return builder.getString();
    //$$ }
    //$$
    //$$ public String getFormattedText() {
    //$$     TextBuilder builder = new TextBuilder(true);
    //$$     component.asOrderedText().accept(builder);
    //$$     return builder.getString();
    //$$ }
    //$$
    //$$ public MutableText appendSibling(Text text) {
    //$$     return append(text);
    //$$ }
    //$$
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
    //#elseif MC>=11602
    //$$ public void appendSibling(ITextComponent component) {
    //$$     getSiblings().add(component);
    //$$ }
    //$$
    //$$ public String getUnformattedText() {
    //$$     return getUnformattedComponentText();
    //$$ }
    //$$
    //$$ public String getFormattedText() {
    //$$     return getText();
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
    //#elseif MC>=11502
    //$$ public String getUnformattedText() {
    //$$     return getUnformattedComponentText();
    //$$ }
    //$$
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
    //#elseif MC>=11202
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
    //$$     return this.component.appendSibling(component);
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
    //#endif
}
