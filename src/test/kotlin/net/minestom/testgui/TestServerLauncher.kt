package net.minestom.testgui

import net.minestom.server.Bootstrap

object TestServerLauncher {

    @JvmStatic
    fun main(args: Array<String>) {
        Bootstrap.bootstrap("fr.themode.demo.MainDemo", args)
    }
}