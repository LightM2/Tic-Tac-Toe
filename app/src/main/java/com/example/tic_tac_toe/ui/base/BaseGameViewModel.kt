package com.example.tic_tac_toe.ui.base

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.tic_tac_toe.game_logic.Cell
import com.example.tic_tac_toe.game_logic.clearBoard

abstract class BaseGameViewModel: ViewModel(), Observable {
    abstract val TAG: String

    val board: MutableList<MutableList<Cell>> = mutableListOf(
            mutableListOf(Cell(0,0), Cell(0,1), Cell(0,2)),
            mutableListOf(Cell(1,0), Cell(1,1), Cell(1,2)),
            mutableListOf(Cell(2,0), Cell(2,1), Cell(2,2))
    )

    val xMove: MutableLiveData<MutableList<MutableList<Int>>> = MutableLiveData(mutableListOf(
            mutableListOf(0,0,0),
            mutableListOf(0,0,0),
            mutableListOf(0,0,0)))

    val oMove: MutableLiveData<MutableList<MutableList<Int>>> = MutableLiveData(mutableListOf(
            mutableListOf(0,0,0),
            mutableListOf(0,0,0),
            mutableListOf(0,0,0)))

    var resultVisibility = View.INVISIBLE

    var resultText = ""



    fun backwardClick(view: View){
        view.findNavController().popBackStack()
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

    fun showResult(result: Int?){
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