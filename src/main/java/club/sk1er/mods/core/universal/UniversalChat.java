package club.sk1er.mods.core.universal;

import java.util.regex.Pattern;

public class UniversalChat {
    public static Pattern amperstandPattern = Pattern.compile("(?<!\\\\)&(?![^0-9a-fklmnor]|$)");

    public static String addColor(String message) {
        return amperstandPattern.matcher(message).replaceAll("\u00a7");
    }
}
