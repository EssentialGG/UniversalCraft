package club.sk1er.mods.core.universal

import java.awt.Desktop
import java.io.File
import java.io.IOException
import java.net.URI

object UniversalDesktop {
    @JvmStatic
    var isLinux = false
        private set
    @JvmStatic
    var isXdg = false
        private set
    @JvmStatic
    var isKde = false
        private set
    @JvmStatic
    var isGnome = false
        private set
    @JvmStatic
    var isMac = false
        private set
    @JvmStatic
    var isWindows = false
        private set

    init {
        val osName = try {
            System.getProperty("os.name")
        } catch (e: SecurityException) {
            null
        }
        isLinux = osName != null && (osName.startsWith("Linux") || osName.startsWith("LINUX"))
        isMac = osName != null && osName.startsWith("Mac")
        isWindows = osName != null && osName.startsWith("Windows")
        if (isLinux) {
            System.getenv("XDG_SESSION_ID")?.let {
                isXdg = it.isNotEmpty()
            }
            System.getenv("GDMSESSION")?.toLowerCase()?.let {
                isGnome = "gnome" in it
                isKde = "kde" in it
            }
        } else {
            isXdg = false
            isKde = false
            isGnome = false
        }
    }

    @JvmStatic
    fun browse(uri: URI) = browseDesktop(uri) || openSystemSpecific(uri.toString())

    @JvmStatic
    fun open(file: File) = openDesktop(file) || openSystemSpecific(file.path)

    @JvmStatic
    fun edit(file: File) = editDesktop(file) || openSystemSpecific(file.path)

    private fun openSystemSpecific(file: String): Boolean {
        return when {
            isLinux -> when {
                isXdg -> runCommand("xdg-open $file")
                isKde -> runCommand("kde-open $file")
                isGnome -> runCommand("gnome-open $file")
                else -> runCommand("kde-open $file") || runCommand("gnome-open $file")
            }
            isMac -> runCommand("open $file")
            isWindows -> runCommand("explorer $file")
            else -> false
        }
    }

    private fun browseDesktop(uri: URI): Boolean {
        return if (!Desktop.isDesktopSupported()) false else try {
            if (!Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                return false
            Desktop.getDesktop().browse(uri)
            true
        } catch (e: Throwable) {
            false
        }
    }

    private fun openDesktop(file: File): Boolean {
        return if (!Desktop.isDesktopSupported()) false else try {
            if (!Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
                return false
            Desktop.getDesktop().open(file)
            true
        } catch (e: Throwable) {
            false
        }
    }

    private fun editDesktop(file: File): Boolean {
        return if (!Desktop.isDesktopSupported()) false else try {
            if (!Desktop.getDesktop().isSupported(Desktop.Action.EDIT))
                return false
            Desktop.getDesktop().edit(file)
            true
        } catch (e: Throwable) {
            false
        }
    }

    private fun runCommand(command: String): Boolean {
        return try {
            Runtime.getRuntime().exec(command).let {
                it != null && it.isAlive
            }
        } catch (e: IOException) {
            false
        }
    }
}
