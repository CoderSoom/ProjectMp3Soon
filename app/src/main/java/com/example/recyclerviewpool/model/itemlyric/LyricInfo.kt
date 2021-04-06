package com.example.recyclerviewpool.model.itemlyric

import java.util.*

class LyricInfo {
    var lines: MutableList<LineInfo>? = null
        get() {
            if (field == null) {
                field = ArrayList()
            }
            return field
        }

}