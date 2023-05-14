package com.github.locxter.twrfhn.ng.model

import kotlin.math.pow

class Game() {
    var diskCount: Int = 0
        set(value) {
            val rodA = Rod()
            for (i in value downTo 1) {
                rodA.disks.add(Disk(i))
            }
            moves.clear()
            moves.add(Move(rodA))
            field = value
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
                    1 -> moveBetweenRodAAndB(rodA, rodB)
                    2 -> moveBetweenRodAAndC(rodA, rodC)
                    0 -> moveBetweenRodBAndC(rodB, rodC)
                }
            } else {
                when (i % 3) {
                    1 -> moveBetweenRodAAndC(rodA, rodC)
                    2 -> moveBetweenRodAAndB(rodA, rodB)
                    0 -> moveBetweenRodBAndC(rodB, rodC)
                }
            }
            moves.add(Move(rodA, rodB, rodC))
        }
    }

    // Make the legal move between pegs A and B (in either direction)
    private fun moveBetweenRodAAndB(rodA: Rod, rodB: Rod) {
        if (rodA.disks.isEmpty() && rodB.disks.isNotEmpty()) {
            rodA.disks.add(rodB.disks.last())
            rodB.disks.removeLast()
        } else if (rodB.disks.isEmpty() && rodA.disks.isNotEmpty()) {
            rodB.disks.add(rodA.disks.last())
            rodA.disks.removeLast()
        } else if (rodA.disks.last().size > rodB.disks.last().size) {
            rodA.disks.add(rodB.disks.last())
            rodB.disks.removeLast()
        } else {
            rodB.disks.add(rodA.disks.last())
            rodA.disks.removeLast()
        }
    }

    // Make the legal move between pegs A and B (in either direction)
    private fun moveBetweenRodAAndC(rodA: Rod, rodC: Rod) {
        if (rodA.disks.isEmpty() && rodC.disks.isNotEmpty()) {
            rodA.disks.add(rodC.disks.last())
            rodC.disks.removeLast()
        } else if (rodC.disks.isEmpty() && rodA.disks.isNotEmpty()) {
            rodC.disks.add(rodA.disks.last())
            rodA.disks.removeLast()
        } else if (rodA.disks.last().size > rodC.disks.last().size) {
            rodA.disks.add(rodC.disks.last())
            rodC.disks.removeLast()
        } else {
            rodC.disks.add(rodA.disks.last())
            rodA.disks.removeLast()
        }
    }

    // Make the legal move between pegs B and C (in either direction)
    private fun moveBetweenRodBAndC(rodB: Rod, rodC: Rod) {
        if (rodB.disks.isEmpty() && rodC.disks.isNotEmpty()) {
            rodB.disks.add(rodC.disks.last())
            rodC.disks.removeLast()
        } else if (rodC.disks.isEmpty() && rodB.disks.isNotEmpty()) {
            rodC.disks.add(rodB.disks.last())
            rodB.disks.removeLast()
        } else if (rodB.disks.last().size > rodC.disks.last().size) {
            rodB.disks.add(rodC.disks.last())
            rodC.disks.removeLast()
        } else {
            rodC.disks.add(rodB.disks.last())
            rodB.disks.removeLast()
        }
    }
}
