package com.github.locxter.twrfhn.ng.model

import kotlin.math.max

class Disk(size: Int = 1) {
    val size: Int
    init {
        this.size = max(size, 1)
    }
}
