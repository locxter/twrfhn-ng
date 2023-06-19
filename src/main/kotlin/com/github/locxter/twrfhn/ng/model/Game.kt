package com.github.locxter.twrfhn.ng.model

import kotlin.math.pow

class Game() {
    var diskCount: Int = 0
        set(value) {
            if (value >= 0) {
                val rodA = Rod()
                for (i in value downTo 1) {
                    rodA.disks.add(Disk(i))
                }
                moves.clear()
                moves.add(Move(rodA))
                field = value
            }
        }
    val moves: MutableList<Move> = mutableListOf()

    constructor(diskCount: Int) : this() {
        this.diskCount = diskCount
    }

    // Calculate the ideal set of moves to solve the game using an iterative approach
    fun calculateMoves() {
        for (i in 1 until (2.0).pow(diskCount).toInt()) {
            val rodA = Rod(moves.last().rodA.disks.map { Disk(it.size) }.toMutableList())
            val rodB = Rod(moves.last().rodB.disks.map { Disk(it.size) }.toMutableList())
            val rodC = Rod(moves.last().rodC.disks.map { Disk(it.size) }.toMutableList())
            if (diskCount % 2 == 0) {
                when (i % 3) {
                    1 -> moveBetweenRods(rodA, rodB)
                    2 -> moveBetweenRods(rodA, rodC)
                    0 -> moveBetweenRods(rodB, rodC)
                }
            } else {
                when (i % 3) {
                    1 -> moveBetweenRods(rodA, rodC)
                    2 -> moveBetweenRods(rodA, rodB)
                    0 -> moveBetweenRods(rodB, rodC)
                }
            }
            moves.add(Move(rodA, rodB, rodC))
        }
    }

    // Make the legal move between two rods (in either direction)
    private fun moveBetweenRods(rod1: Rod, rod2: Rod) {
        if (rod1.disks.isEmpty() && rod2.disks.isNotEmpty()) {
            rod1.disks.add(rod2.disks.last())
            rod2.disks.removeLast()
        } else if (rod2.disks.isEmpty() && rod1.disks.isNotEmpty()) {
            rod2.disks.add(rod1.disks.last())
            rod1.disks.removeLast()
        } else if (rod1.disks.last().size > rod2.disks.last().size) {
            rod1.disks.add(rod2.disks.last())
            rod2.disks.removeLast()
        } else {
            rod2.disks.add(rod1.disks.last())
            rod1.disks.removeLast()
        }
    }
}
