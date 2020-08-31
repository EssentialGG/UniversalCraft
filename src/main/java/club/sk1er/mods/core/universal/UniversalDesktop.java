package club.sk1er.mods.core.universal;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class UniversalDesktop {
    public static final boolean isLinux;
    public static final boolean isXdg;
    public static final boolean isKde;
    public static final boolean isGnome;
    public static final boolean isMac;
    public static final boolean isWindows;

    static {
        String osName;

        try {
            osName = System.getProperty("os.name");
        } catch (SecurityException e) {
            osName = null;
        }

        isLinux = osName != null && (osName.startsWith("Linux") || osName.startsWith("LINUX"));
        isMac = osName != null && osName.startsWith("Mac");
        isWindows = osName != null && osName.startsWith("Windows");

        if (isLinux) {
            String env = System.getenv("XDG_SESSION_ID");
            isXdg = env != null && env.length() > 0;

            env = System.getenv("GDMSESSION");
            isGnome = env != null && env.toLowerCase().contains("gnome");
            isKde = env != null && env.toLowerCase().contains("kde");
        } else {
            isXdg = false;
            isKde = false;
            isGnome = false;
        }
    }

    public static boolean browse(URI uri) {
        return browseDesktop(uri) || openSystemSpecific(uri.toString());
    }

    public static boolean open(File file) {
        return openDesktop(file) || openSystemSpecific(file.getPath());
    }

    public static boolean edit(File file) {
        return editDesktop(file) || openSystemSpecific(file.getPath());
    }

    private static boolean openSystemSpecific(String file) {
        if (isLinux) {
            if (isXdg) return runCommand("xdg-open " + file);
            if (isKde) return runCommand("kde-open " + file);
            if (isGnome) return runCommand("gnome-open " + file);

            return runCommand("kde-open " + file) || runCommand("gnome-open " + file);
        }

        if (isMac) return runCommand("open " + file);
        if (isWindows) return runCommand("explorer " + file);

        return false;
    }

    private static boolean browseDesktop(URI uri) {
        if (!Desktop.isDesktopSupported())
            return false;

        try {
            if (!Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                return false;

            Desktop.getDesktop().browse(uri);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    private static boolean openDesktop(File file) {
        if (!Desktop.isDesktopSupported())
            return false;

        try {
            if (!Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
                return false;

            Desktop.getDesktop().open(file);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    private static boolean editDesktop(File file) {
        if (!Desktop.isDesktopSupported())
            return false;

        try {
            if (!Desktop.getDesktop().isSupported(Desktop.Action.EDIT))
                return false;

            Desktop.getDesktop().edit(file);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    private static boolean runCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            return process != null && process.isAlive();
        } catch (IOException e) {
            return false;
        }
    }
}
