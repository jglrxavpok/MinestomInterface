package net.minestom.gui.log

import net.minestom.server.extras.selfmodification.MinestomOverwriteClassLoader
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.Layout
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender
import org.apache.logging.log4j.core.appender.OutputStreamManager
import org.apache.logging.log4j.core.config.plugins.Plugin
import org.apache.logging.log4j.core.config.plugins.PluginAttribute
import org.apache.logging.log4j.core.config.plugins.PluginElement
import org.apache.logging.log4j.core.config.plugins.PluginFactory
import org.apache.logging.log4j.core.layout.PatternLayout
import java.io.ByteArrayOutputStream
import java.io.Serializable
import javax.swing.JTextArea

@Plugin(name = "TextAreaAppender", category = "Core", elementType = "appender", printObject = true)
class TextAreaAppender: AbstractOutputStreamAppender<JTextAreaManager> {

    constructor(name: String, layout: Layout<out Serializable>, filter: Filter?, ignoreExceptions: Boolean): super(name, layout, filter, ignoreExceptions, true, JTextAreaManager)

    companion object {
        @PluginFactory
        @JvmStatic
        fun createAppender(@PluginAttribute("name") name: String?,
                           @PluginAttribute("ignoreExceptions") ignoreExceptions: Boolean,
                           @PluginElement("Layout") layout: Layout<out Serializable>?,
                           @PluginElement("Filters") filter: Filter?): TextAreaAppender? {
            return TextAreaAppender(name ?: error("No name provided"), layout ?: PatternLayout.createDefaultLayout(), filter, ignoreExceptions)
        }
    }
}

object JTextAreaManager: OutputStreamManager(ByteArrayOutputStream(), "TextAreaManager", PatternLayout.createDefaultLayout(), true) {

    init {

    }

    private val out get()= outputStream as ByteArrayOutputStream
    private val subscribers = mutableListOf<Subscriber>()

    override fun flush() {
        super.flush()
        val message = String(out.toByteArray(), Charsets.UTF_8)
        subscribers.forEach {
            it.onMessage(message)
        }
        out.reset()
    }

    fun subscribe(area: JTextArea) {
        subscribers += object : Subscriber {
            override fun onMessage(message: String) {
                area.text += message
                area.repaint()
            }
        }
    }

    @FunctionalInterface
    interface Subscriber {
        fun onMessage(message: String): Unit
    }
}
