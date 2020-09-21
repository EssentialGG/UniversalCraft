package club.sk1er.mods.core.universal.wrappers.message;

import club.sk1er.mods.core.universal.UniversalMinecraft;
import club.sk1er.mods.core.universal.UniversalPacket;
import club.sk1er.mods.core.universal.wrappers.UniversalPlayer;

//#if FABRIC
//$$ import net.minecraft.text.MutableText;
//#endif

import java.util.ArrayList;
import java.util.List;

/**
 * A UniversalMessage can be thought of as a wrapper around
 * a series of UniversalTextComponents. This class allows
 * functionality such as editing and deleting messages that
 * have already been sent to the user
 */
public class UniversalMessage {
    private UniversalTextComponent chatMessage;
    private List<UniversalTextComponent> messageParts = new ArrayList<>();

    private int chatLineId = -1;
    private boolean recursive = false;
    private boolean formatted = true;

    public UniversalMessage(UniversalTextComponent component) {
        if (component.getSiblings().isEmpty()) {
            messageParts.add(component);
        } else {
            //#if FORGE
            component.getSiblings().stream().map(UniversalTextComponent::new).forEach(messageParts::add);
            //#else
            //$$ component.getSiblings().stream()
            //$$     .map(it -> new UniversalTextComponent((MutableText) it))
            //$$     .forEach(messageParts::add);
            //#endif
        }
    }

    /**
     * Creates a new Message from a list of strings or UniversalTextComponents
     * @param parts
     */
    public UniversalMessage(List<Object> parts) {
        parts.forEach(this::addPart);
    }

    public UniversalTextComponent getChatMessage() {
        parseMessage();
        return chatMessage;
    }

    public String getFormattedText() {
        return getChatMessage().getFormattedText();
    }

    public String getUnformattedText() {
        return getChatMessage().getUnformattedText();
    }

    public List<UniversalTextComponent> getMessageParts() {
        return messageParts;
    }

    public int getChatLineId() {
        return chatLineId;
    }

    public UniversalMessage setChatLineId(int id) {
        chatLineId = id;
        return this;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public UniversalMessage setRecursive(boolean recursive) {
        this.recursive = recursive;
        return this;
    }

    public boolean isFormatted() {
        return formatted;
    }

    public UniversalMessage setFormatted(boolean formatted) {
        this.formatted = formatted;
        return this;
    }

    public UniversalMessage setTextComponent(int index, Object component) {
        if (component instanceof String) {
            messageParts.set(index, new UniversalTextComponent((String) component));
        } else {
            UniversalTextComponent.from(component).ifPresent(it -> messageParts.set(index, it));
        }
        return this;
    }

    public UniversalMessage addTextComponent(int index, Object component) {
        if (component instanceof String) {
            messageParts.add(index, new UniversalTextComponent((String) component));
        } else {
            UniversalTextComponent.from(component).ifPresent(it -> messageParts.add(index, it));
        }
        return this;
    }

    public UniversalMessage addTextComponent(Object component) {
        return addTextComponent(messageParts.size(), component);
    }

    public void edit(UniversalMessage... replacements) {
        throw new UnsupportedOperationException("TODO");
    }

    public void chat() {
        parseMessage();

        if (UniversalPlayer.getPlayer() == null)
            return;

        //TODO: Access transformer
        //#if MC<11602
        if (chatLineId != -1) {
            if (UniversalMinecraft.getChatGUI() != null)
                UniversalMinecraft.getChatGUI().printChatMessageWithOptionalDeletion(chatMessage, chatLineId);
            return;
        }
        //#endif

        if (recursive) {
            UniversalPacket.sendChatMessage(chatMessage);
        } else {
            UniversalPlayer.sendClientSideMessage(chatMessage);
        }
    }

    public void actionBar() {
        parseMessage();

        if (UniversalPlayer.getPlayer() == null)
            return;

        UniversalPacket.sendActionBarMessage(chatMessage);
    }

    private void addPart(Object part) {
        if (part instanceof UniversalTextComponent) {
            messageParts.add((UniversalTextComponent) part);
        } else if (part instanceof String) {
            messageParts.add(new UniversalTextComponent((String) part));
        } else {
            UniversalTextComponent.from(part).ifPresent(this::addPart);
        }
    }

    private void parseMessage() {
        chatMessage = new UniversalTextComponent("");
        messageParts.forEach(chatMessage::appendSibling);
    }
}
