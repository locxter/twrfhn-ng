package com.github.locxter.twrfhn.ng

import com.formdev.flatlaf.FlatDarkLaf
import com.github.locxter.twrfhn.ng.gui.Visualisation
import com.github.locxter.twrfhn.ng.model.Game
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main(args: Array<String>) {
    var currentMove = 0
    var moveCount = 0
    val game = Game(5)
    // Set a pleasing LaF
    try {
        UIManager.setLookAndFeel(FlatDarkLaf())
    } catch (exception: Exception) {
        println("Failed to initialize LaF.")
    }
    // UI components
    val frame = JFrame("twrfhn-ng")
    val panel = JPanel()
    val constraints = GridBagConstraints()
    val diskCountLabel = JLabel("Disk count:")
    val diskCountInput = JSpinner(SpinnerNumberModel(5, 1, 10, 1))
    val calculateButton = JButton("Calculate")
    val moveLabel = JLabel("Move:")
    val moveData = JLabel()
    val previousMoveButton = JButton("Previous move")
    val nextMoveButton = JButton("Next move")
    val visualisation = Visualisation()
    val aboutLabel = JLabel("2023 locxter")
// Add functions to the buttons
    calculateButton.addActionListener {
        game.diskCount = diskCountInput.value as Int
        game.calculateMoves()
        currentMove = 0
        moveCount = game.moves.size - 1
        moveData.text = "$currentMove / $moveCount"
        visualisation.move = game.moves[currentMove]
    }
    previousMoveButton.addActionListener {
        if (currentMove > 0 && game.moves.size > 1) {
            currentMove--
            moveData.text = "$currentMove / $moveCount"
            visualisation.move = game.moves[currentMove]
        }
    }
    nextMoveButton.addActionListener {
        if (currentMove < moveCount && game.moves.size > 1) {
            currentMove++
            moveData.text = "$currentMove / $moveCount"
            visualisation.move = game.moves[currentMove]
        }
    }
    // Create the main panel
    panel.border = EmptyBorder(5, 5, 5, 5)
    panel.layout = GridBagLayout()
    constraints.insets = Insets(5, 5, 5, 5)
    constraints.fill = GridBagConstraints.RELATIVE
    constraints.weightx = 1.0
    constraints.gridx = 0
    constraints.gridy = 0
    panel.add(diskCountLabel, constraints)
    constraints.fill = GridBagConstraints.HORIZONTAL
    constraints.gridx = 1
    constraints.gridy = 0
    panel.add(diskCountInput, constraints)
    constraints.gridx = 0
    constraints.gridy = 1
    constraints.gridwidth = 2
    panel.add(calculateButton, constraints)
    constraints.fill = GridBagConstraints.RELATIVE
    constraints.gridx = 0
    constraints.gridy = 2
    constraints.gridwidth = 1
    panel.add(moveLabel, constraints)
    constraints.gridx = 1
    constraints.gridy = 2
    panel.add(moveData, constraints)
    constraints.fill = GridBagConstraints.HORIZONTAL
    constraints.gridx = 0
    constraints.gridy = 3
    constraints.gridwidth = 2
    panel.add(previousMoveButton, constraints)
    constraints.gridx = 0
    constraints.gridy = 4
    panel.add(nextMoveButton, constraints)
    constraints.fill = GridBagConstraints.BOTH
    constraints.weighty = 1.0
    constraints.gridx = 0
    constraints.gridy = 5
    panel.add(visualisation, constraints)
    constraints.fill = GridBagConstraints.RELATIVE
    constraints.weighty = 0.0
    constraints.gridx = 0
    constraints.gridy = 6
    panel.add(aboutLabel, constraints)
    // Create the main window
    frame.size = Dimension(640, 640)
    frame.minimumSize = Dimension(480, 480)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.add(panel)
    frame.isVisible = true
}
