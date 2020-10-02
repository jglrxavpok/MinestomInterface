package net.minestom.testgui

import net.minestom.server.Bootstrap
import net.minestom.server.extras.selfmodification.MinestomOverwriteClassLoader

object TestServerLauncher {

    @JvmStatic
    fun main(args: Array<String>) {
        MinestomOverwriteClassLoader.getInstance().protectedPackages.add("net.minestom.gui.log")
        Bootstrap.bootstrap("fr.themode.demo.MainDemo", args)
    }
}