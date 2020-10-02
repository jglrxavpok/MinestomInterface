package net.minestom.gui

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension
import net.minestom.server.extras.selfmodification.MinestomOverwriteClassLoader
import javax.swing.UIManager

class GuiExtension: Extension() {

    lateinit var frame: ApplicationFrame

    override fun preInitialize() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        frame = ApplicationFrame(MinecraftServer.getBrandName())
        initEvents()
    }

    private fun initEvents() {
        MinecraftServer.getConnectionManager().addPlayerInitialization { player ->
            logger.info("Connected: ${player.username}")
        }


        logger.info("Interface ready to accept events")
        println("Interface ready to accept events")
    }

    override fun initialize() {

    }

    override fun terminate() {
        frame.dispose()
    }
}