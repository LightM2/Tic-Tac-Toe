package com.example.tic_tac_toe.game_logic

class Cell(val row: Int, val col: Int) {
    var content: Seed = Seed.EMPTY
    var text: String = ""
    var buttonClickable = true
    val rowAndCol = row to col

    fun clear() {
        content = Seed.EMPTY
        text = ""
        buttonClickable = true
    }
}