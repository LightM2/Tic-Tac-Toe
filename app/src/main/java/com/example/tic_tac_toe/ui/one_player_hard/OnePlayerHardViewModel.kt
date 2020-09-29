package com.example.tic_tac_toe.ui.one_player_hard

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.tic_tac_toe.ui.two_players.Cell
import com.example.tic_tac_toe.ui.two_players.Seed
import kotlin.math.max
import kotlin.math.min

class OnePlayerHardViewModel
@ViewModelInject
constructor(
        @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(), Observable{

    private val TAG = "OnePlayerHardViewModel"

    val board: MutableList<MutableList<Cell>> = mutableListOf(
            mutableListOf(Cell(0,0), Cell(0,1), Cell(0,2)),
            mutableListOf(Cell(1,0), Cell(1,1), Cell(1,2)),
            mutableListOf(Cell(2,0), Cell(2,1), Cell(2,2))
    )

    private val xMove: MutableLiveData<MutableList<MutableList<Int>>> = MutableLiveData(mutableListOf(
            mutableListOf(0,0,0),
            mutableListOf(0,0,0),
            mutableListOf(0,0,0)))

    private val oMove: MutableLiveData<MutableList<MutableList<Int>>> = MutableLiveData(mutableListOf(
            mutableListOf(0,0,0),
            mutableListOf(0,0,0),
            mutableListOf(0,0,0)))

    var resultVisibility = View.INVISIBLE

    var resultText = ""

    fun onClick(cell: Cell) {
        Log.d(TAG, "onClick mast work")
        val position = cell.rowAndCol
        xMove.value?.get(position.first)?.set(position.second, 1)
        board[position.first][position.second].content = Seed.X
        board[position.first][position.second].buttonClickable = false
        board[position.first][position.second].text = "X"
        notifyChange()
        xMove.value?.forEach { Log.d(TAG, "X moves $it") }
        oMove.value?.forEach { Log.d(TAG, "O moves $it") }
        val result = win()
        Log.d(TAG, "Result $result")
        if(result != null){
            showResult(result)
        }else{
            bestMove()
            showResult(win())
        }


    }

    fun bestMove(){
        var bestScore = 2
        var move = 0 to 0
        for (i in 0 until board.size){
            for (j in 0 until board[i].size) {
                if (board[i][j].content == Seed.EMPTY) {
                    addMove(Pair(i,j), Seed.O)
                    val score = mimMax(0, false)
                    removeMove(Pair(i,j), Seed.O)
                    if (score < bestScore) {
                        bestScore = score
                        move = Pair(i,j)
                    }
                }
            }
        }
        addMove(move, Seed.O)
        notifyChange()

    }

    fun mimMax(depth: Int, isMaximizing: Boolean): Int{
        if (win() != null) return win()!!

        if (isMaximizing){
            var bestScore = 2
            for (i in 0 until board.size){
                for (j in 0 until board[i].size) {
                    if (board[i][j].content == Seed.EMPTY) {
                        addMove(Pair(i, j), Seed.O)
                        val score = mimMax(depth + 1, false)
                        removeMove(Pair(i, j), Seed.O)
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
                        addMove(Pair(i, j), Seed.X)
                        val score = mimMax(depth + 1, true)
                        removeMove(Pair(i, j), Seed.X)
                        bestScore = max(score, bestScore)
                    }
                }
            }
            return bestScore
        }
    }

    private fun addMove(position: Pair<Int, Int>, seed: Seed){
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
            else -> Log.d(TAG, "Seed.EMPTY")
        }
    }

    private fun removeMove(position: Pair<Int, Int>, seed: Seed){
        when (seed){
            Seed.X -> {
                board[position.first][position.second].content = Seed.EMPTY
                xMove.value?.get(position.first)?.set(position.second, 0)
            }
            Seed.O -> {
                board[position.first][position.second].content = Seed.EMPTY
                oMove.value?.get(position.first)?.set(position.second, 0)
            }
            else -> Log.d(TAG, "Seed.EMPTY")
        }
        board[position.first][position.second].buttonClickable = true
        board[position.first][position.second].text = ""
    }

    private fun win(): Int?{
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

    fun backwardClick(view: View){
        view.findNavController().popBackStack()
    }

    private fun clearBoard(someBoard: MutableList<MutableList<Cell>>){
        someBoard.forEach { row ->
            row.forEach { it.clear() }
        }
    }

    fun replayClick(){
        Log.d(TAG,"replayClick")
        clearBoard(board)
        resultVisibility = View.INVISIBLE
        xMove.value = mutableListOf(
                mutableListOf(0,0,0),
                mutableListOf(0,0,0),
                mutableListOf(0,0,0))
        oMove.value = mutableListOf(
                mutableListOf(0,0,0),
                mutableListOf(0,0,0),
                mutableListOf(0,0,0))
        notifyChange()
    }

    private fun showResult(result: Int?){
        if (result != null){
            val timer = object: CountDownTimer(5000, 5000){
                override fun onTick(p0: Long) {
                    resultText = when(result){
                        1 -> "X win"
                        0 -> "Dead heat"
                        -1 -> "O win"
                        else -> ""
                    }
                    resultVisibility = View.VISIBLE
                    notifyChange()
                }

                override fun onFinish() {
                    if(resultVisibility == View.VISIBLE) replayClick()
                }

            }
            timer.start()
        }
    }


    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }


}