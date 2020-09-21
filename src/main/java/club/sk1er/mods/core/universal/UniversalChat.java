package club.sk1er.mods.core.universal;

import club.sk1er.mods.core.universal.wrappers.UniversalPlayer;
import club.sk1er.mods.core.universal.wrappers.message.UniversalMessage;
import club.sk1er.mods.core.universal.wrappers.message.UniversalTextComponent;

import java.util.Optional;
import java.util.regex.Pattern;

public class UniversalChat {
    public static Pattern amperstandPattern = Pattern.compile("(?<!\\\\)&(?![^0-9a-fklmnor]|$)");

    /**
     * Prints a message to chat. Accepts a String, UniversalMessage, UniversalTextComponent,
     * or any version-specific text component. If the object is of an unrecognized type,
     * it's toString() method will be called.
     *
     * This function is client side.
     */
    public static void chat(Object object) {
        if (object instanceof String || object instanceof UniversalTextComponent) {
            new UniversalMessage(object).chat();
        } else {
            Optional<UniversalTextComponent> component = UniversalTextComponent.from(object);
            if (component.isPresent()) {
                component.get().chat();
            } else {
                new UniversalMessage(object.toString()).chat();
            }
        }
    }

    /**
     * Prints a message to the action bar. Accepts a String, UniversalMessage,
     * UniversalTextComponent, or any version-specific text component. If the
     * object is of an unrecognized type, it's toString() method will be called.
     *
     * This function is client side.
     */
    public static void actionBar(Object object) {
        if (object instanceof String || object instanceof UniversalTextComponent) {
            new UniversalMessage(object).actionBar();
        } else {
            Optional<UniversalTextComponent> component = UniversalTextComponent.from(object);
            if (component.isPresent()) {
                component.get().actionBar();
            } else {
                new UniversalMessage(object.toString()).actionBar();
            }
        }
    }

    /**
     * Sends a String to the server.
     */
    public static void say(String text) {
        UniversalPlayer.getPlayer().sendChatMessage(text);
    }

    public static String addColor(String message) {
        return amperstandPattern.matcher(message).replaceAll("\u00a7");
    }
}
