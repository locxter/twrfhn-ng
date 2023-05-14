package com.github.locxter.twrfhn.ng.gui

import com.github.locxter.twrfhn.ng.model.Move
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import javax.swing.JComponent
import kotlin.math.roundToInt


class Visualisation() : JComponent() {
    private var scalingRatio = 0.0
    private var showStartScreen = true
    var move = Move()
        set(value) {
            if (showStartScreen) {
                showStartScreen = false
            }
            field = value
            repaint()
        }

    constructor(move: Move) : this() {
        this.move = move
    }

    // Method to draw the component
    override fun paintComponent(context: Graphics) {
        val width = width
        val height = height
        // Clear the component
        super.paintComponent(context)
        context.color = Color(60, 63, 65)
        context.fillRect(0, 0, width, height)
        // Calculate the scaling ratio and center the canvas
        if (width.toDouble() / height > 16.0 / 9) {
            scalingRatio = height / 1080.0
            context.translate(((width - (scalingRatio * 1920)) / 2).roundToInt(), 0)
        } else {
            scalingRatio = width / 1920.0
            context.translate(0, ((height - (scalingRatio * 1080)) / 2).roundToInt())
        }
        if (showStartScreen) {
            // Draw a start screen if no visualisation has been requested yet
            val font = Font(Font.SANS_SERIF, Font.PLAIN, getScaledValue(64))
            val metrics = context.getFontMetrics(font)
            val message = "Press \"Calculate\" to see the visualisation."
            context.font = font
            context.color = Color(255, 255, 255)
            context.drawString(
                message,
                ((getScaledValue(1920).toDouble() - metrics.stringWidth(message)) / 2).roundToInt(),
                (((getScaledValue(1080).toDouble() - metrics.height) / 2) + metrics.ascent).roundToInt()
            )
        } else {
            val diskCount = move.rodA.disks.size + move.rodB.disks.size + move.rodC.disks.size
            for (i in 0..2) {
                val rod = when (i) {
                    0 -> move.rodA
                    1 -> move.rodB
                    else -> move.rodC
                }
                // Drawing a rod
                context.color = Color(255, 255, 255)
                context.fillRect(
                    getScaledValue(304 + (640 * i)), 0, getScaledValue(32),
                    getScaledValue(1080)
                )
                // Drawing the disks on the rod
                context.color = Color(0, 255, 0)
                for (j in 0 until rod.disks.size) {
                    val disk = rod.disks[j]
                    val diskWidth = ((640.0 / diskCount) * disk.size).roundToInt()
                    context.fillRect(
                        getScaledValue(320 - (diskWidth / 2) + (640 * i)),
                        getScaledValue(1080 - (108 * (j + 1))),
                        getScaledValue(diskWidth),
                        getScaledValue(96)
                    )
                }
            }
        }
    }

    // Helper method to transform an unscaled value to a scaled one
    private fun getScaledValue(unscaledValue: Int): Int {
        return (unscaledValue * scalingRatio).roundToInt()
    }
}
