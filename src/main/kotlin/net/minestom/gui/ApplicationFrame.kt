package net.minestom.gui

import net.minestom.gui.log.JTextAreaManager
import net.minestom.server.MinecraftServer
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import javax.imageio.ImageIO
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JTextArea
import javax.swing.JTextField

class ApplicationFrame(title: String): JFrame(title), WindowListener {

    init {
        iconImages = listOf(
            ImageIO.read(javaClass.getResource("/minestom_icon.png")),
            ImageIO.read(javaClass.getResource("/minestom_icon64.png")),
            ImageIO.read(javaClass.getResource("/minestom_icon32.png")),
        )
        defaultCloseOperation = DISPOSE_ON_CLOSE
        add(chatArea(), BorderLayout.CENTER)
        add(commandBox(), BorderLayout.SOUTH)
        setLocationRelativeTo(null)
        minimumSize = Dimension(600, 400)
        addWindowListener(this)
        pack()
        isVisible = true
    }

    private fun chatArea(): JComponent {
        return JTextArea(30, 100).apply {
            isEditable = false
            text = "Hello"

            // redirect output to this text area
            System.setOut(RedirectingOutputStream(System.out, this))
            System.setErr(RedirectingOutputStream(System.err, this))

            JTextAreaManager.subscribe(this)
        } // TODO
    }

    private fun commandBox(): JComponent {
        return JTextField() // TODO
    }

    override fun windowClosed(e: WindowEvent?) {}

    override fun windowOpened(e: WindowEvent?) {}

    override fun windowClosing(e: WindowEvent?) {
        MinecraftServer.stopCleanly()
    }

    override fun windowIconified(e: WindowEvent?) {}

    override fun windowDeiconified(e: WindowEvent?) {}

    override fun windowActivated(e: WindowEvent?) {}

    override fun windowDeactivated(e: WindowEvent?) {}

}

private class RedirectingOutputStream(val target: PrintStream, val textArea: JTextArea): PrintStream(ByteArrayOutputStream()) {
    private val buffer get()= this.out as ByteArrayOutputStream

    override fun println(x: String?) {
        super.println(x)
        flush()
    }

    override fun println() {
        super.println()
        flush()
    }

    override fun println(x: Boolean) {
        super.println(x)
        flush()
    }

    override fun println(x: Char) {
        super.println(x)
        flush()
    }

    override fun println(x: Int) {
        super.println(x)
        flush()
    }

    override fun println(x: Long) {
        super.println(x)
        flush()
    }

    override fun println(x: Float) {
        super.println(x)
        flush()
    }

    override fun println(x: Double) {
        super.println(x)
        flush()
    }

    override fun println(x: CharArray) {
        super.println(x)
        flush()
    }

    override fun println(x: Any?) {
        super.println(x)
        flush()
    }

    override fun flush() {
        super.flush()
        updateArea()
    }

    private fun updateArea() {
        val message = String(buffer.toByteArray(), Charsets.UTF_8)
        textArea.text += message+"\n"
        target.print(message)
        buffer.reset()
    }
}