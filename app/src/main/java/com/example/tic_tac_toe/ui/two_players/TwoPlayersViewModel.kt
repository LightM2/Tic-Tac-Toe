package com.example.tic_tac_toe.ui.two_players

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController

class TwoPlayersViewModel
@ViewModelInject
constructor(
        @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(), Observable{

    private val TAG = "TwoPlayersViewModel"

    private var lastMove = ""

    var resultVisibility = INVISIBLE

    var resultText = ""

    val board: MutableList<Cell> = mutableListOf(
            Cell(0,0), Cell(0,1), Cell(0,2),
            Cell(1,0), Cell(1,1), Cell(1,2),
            Cell(2,0), Cell(2,1), Cell(2,2)
    )

    private val xMove: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf(0,0,0,
            0,0,0,
            0,0,0))

    private val oMove: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf(0,0,0,
            0,0,0,
            0,0,0))

    fun replayClick(){
        board.forEach { it.clear() }
        resultVisibility = INVISIBLE
        xMove.value = mutableListOf(0,0,0,
                0,0,0,
                0,0,0)
        oMove.value = mutableListOf(0,0,0,
                0,0,0,
                0,0,0)
        lastMove = ""
        notifyChange()
    }

    fun backwardClick(view: View){
        view.findNavController().popBackStack()
    }

    fun onClick(cell: Cell) {
        Log.d(TAG, "OnClick")

        if (cell.content == Seed.EMPTY){
            val position = board.indexOf(cell)
            lastMove = when (lastMove){
                "" -> {
                    board[position].content = Seed.X
                    "X"
                }
                "X" -> {
                    board[position].content = Seed.O
                    "O"
                }
                "O" -> {
                    board[position].content = Seed.X
                    "X"
                }
                else -> "e"
            }

            addMove(position, board[position].content)
            board[position].text = lastMove

            notifyChange()

            win()

        }else Log.d(TAG, "cell not empty")

    }

    private fun addMove(position: Int, seed: Seed){
        when (seed){
            Seed.X -> xMove.value?.set(position, 1)
            Seed.O -> oMove.value?.set(position, 1)
            else -> Log.d(TAG, "Seed.EMPTY")
        }
    }

    private fun win(){
        val notEmptyCell = board.count { it.content != Seed.EMPTY }

        val winCombination: List<List<Int>?> = listOf(
                listOf(1,1,1, 0,0,0, 0,0,0),
                listOf(0,0,0, 1,1,1, 0,0,0),
                listOf(0,0,0, 0,0,0, 1,1,1),
                listOf(1,0,0, 1,0,0, 1,0,0),
                listOf(0,1,0, 0,1,0, 0,1,0),
                listOf(0,0,1, 0,0,1, 0,0,1),
                listOf(1,0,0, 0,1,0, 0,0,1),
                listOf(0,0,1, 0,1,0, 1,0,0)
        )

        when {
            winCombination.contains(xMove.value) -> {
                showResult("X Win")
                Log.d(TAG, "X win")
            }
            winCombination.contains(oMove.value) -> {
                showResult("O Win")
                Log.d(TAG, "O win")
            }
            notEmptyCell == board.size -> {
                showResult("Dead Heat")
                Log.d(TAG, "Dead heat")
            }
        }
    }

    private fun showResult(result: String){
        val timer = object: CountDownTimer(5000, 5000){
            override fun onTick(p0: Long) {
                resultText = result
                resultVisibility = VISIBLE
                notifyChange()
            }

            override fun onFinish() {
                if(resultVisibility == VISIBLE) replayClick()
            }

        }
        timer.start()

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