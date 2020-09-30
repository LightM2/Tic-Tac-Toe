package com.example.tic_tac_toe.game_logic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlin.math.max
import kotlin.math.min

fun addMove(board: MutableList<MutableList<Cell>>,
            xMove: MutableLiveData<MutableList<MutableList<Int>>>,
            oMove: MutableLiveData<MutableList<MutableList<Int>>>,
            position: Pair<Int, Int>, seed: Seed){
    when (seed){
        Seed.X -> {
            board[position.first][position.second].content = seed
            board[position.first][position.second].buttonClickable = false
            board[position.first][position.second].text = "X"
            xMove.value?.get(position.first)?.set(position.second, 1)
        }
        Seed.O -> {
            board[position.first][position.second].content = seed
            board[position.first][position.second].buttonClickable = false
            board[position.first][position.second].text = "O"
            oMove.value?.get(position.first)?.set(position.second, 1)
        }
        else -> Log.d("fun addMove", "Seed.EMPTY")
    }
}

fun removeMove(board: MutableList<MutableList<Cell>>,
               xMove: MutableLiveData<MutableList<MutableList<Int>>>,
               oMove: MutableLiveData<MutableList<MutableList<Int>>>,
               position: Pair<Int, Int>, seed: Seed){
    when (seed){
        Seed.X -> {
            board[position.first][position.second].content = Seed.EMPTY
            xMove.value?.get(position.first)?.set(position.second, 0)
        }
        Seed.O -> {
            board[position.first][position.second].content = Seed.EMPTY
            oMove.value?.get(position.first)?.set(position.second, 0)
        }
        else -> Log.d("fun removeMove", "Seed.EMPTY")
    }
    board[position.first][position.second].buttonClickable = true
    board[position.first][position.second].text = ""
}

fun bestMove(board: MutableList<MutableList<Cell>>,
             xMove: MutableLiveData<MutableList<MutableList<Int>>>,
             oMove: MutableLiveData<MutableList<MutableList<Int>>>){
    var bestScore = 2
    var move = 0 to 0
    for (i in 0 until board.size){
        for (j in 0 until board[i].size) {
            if (board[i][j].content == Seed.EMPTY) {
                addMove(board, xMove, oMove, Pair(i,j), Seed.O)
                val score = mimMax(board, xMove, oMove,0, false)
                removeMove(board, xMove, oMove, Pair(i,j), Seed.O)
                if (score < bestScore) {
                    bestScore = score
                    move = Pair(i,j)
                }
            }
        }
    }
    addMove(board, xMove, oMove, move, Seed.O)
}

private fun mimMax(board: MutableList<MutableList<Cell>>,
           xMove: MutableLiveData<MutableList<MutableList<Int>>>,
           oMove: MutableLiveData<MutableList<MutableList<Int>>>,
           depth: Int, isMaximizing: Boolean): Int{
    if (win(xMove, oMove) != null) return win(xMove, oMove)!!

    if (isMaximizing){
        var bestScore = 2
        for (i in 0 until board.size){
            for (j in 0 until board[i].size) {
                if (board[i][j].content == Seed.EMPTY) {
                    addMove(board, xMove, oMove, Pair(i, j), Seed.O)
                    val score = mimMax(board, xMove, oMove,depth + 1, false)
                    removeMove(board, xMove, oMove, Pair(i, j), Seed.O)
                    bestScore = min(score, bestScore)
                }
            }
        }
        return bestScore
    }else{
        var bestScore = -2
        for (i in 0 until board.size){
            for (j in 0 until board[i].size) {
                if (board[i][j].content == Seed.EMPTY) {
                    addMove(board, xMove, oMove, Pair(i, j), Seed.X)
                    val score = mimMax(board, xMove, oMove,depth + 1, true)
                    removeMove(board, xMove, oMove, Pair(i, j), Seed.X)
                    bestScore = max(score, bestScore)
                }
            }
        }
        return bestScore
    }
}

fun win(xMove: MutableLiveData<MutableList<MutableList<Int>>>,
        oMove: MutableLiveData<MutableList<MutableList<Int>>>): Int?{
    var notEmptyCell = 0
    xMove.value?.forEach { row ->
        notEmptyCell += row.count { it == 1 }
    }
    oMove.value?.forEach { row ->
        notEmptyCell += row.count { it == 1 }
    }

    return if(notEmptyCell >= 5){
        when {
            checkWin(xMove.value!!) -> 1
            checkWin(oMove.value!!) -> -1
            notEmptyCell == xMove.value!!.size * xMove.value!!.size -> 0
            else -> null
        }
    } else null

}

private fun checkWin(moves: List<List<Int>>): Boolean{
    return when {
        moves.contains(listOf(1,1,1)) -> true
        moves[0][0] == 1 && moves[1][0] == 1 && moves[2][0] == 1 -> true
        moves[0][1] == 1 && moves[1][1] == 1 && moves[2][1] == 1 -> true
        moves[0][2] == 1 && moves[1][2] == 1 && moves[2][2] == 1 -> true
        moves[0][0] == 1 && moves[1][1] == 1 && moves[2][2] == 1 -> true
        moves[0][2] == 1 && moves[1][1] == 1 && moves[2][0] == 1 -> true
        else -> false
    }
}

fun clearBoard(board: MutableList<MutableList<Cell>>){
    board.forEach { row ->
        row.forEach { it.clear() }
    }
}