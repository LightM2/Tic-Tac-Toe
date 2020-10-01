package com.example.tic_tac_toe.ui.one_player_easy

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.example.tic_tac_toe.game_logic.*
import com.example.tic_tac_toe.ui.base.BaseGameViewModel

class OnePlayerEasyViewModel
@ViewModelInject
constructor(
        @Assisted private val savedStateHandle: SavedStateHandle
): BaseGameViewModel(){
    override val TAG: String = "OnePlayerEasyViewModel"

    fun onClick(cell: Cell){
        Log.d(TAG, "onClick mast work")
        val position = cell.rowAndCol
        addMove(board, xMove, oMove, position, Seed.X)
        notifyChange()
        xMove.value?.forEach { Log.d(TAG, "X moves $it") }
        oMove.value?.forEach { Log.d(TAG, "O moves $it") }
        val result = win(xMove, oMove)
        Log.d(TAG, "Result $result")
        if(result != null){
            showResult(result)
        }else{
            worstMove(board, xMove, oMove)
            notifyChange()
            showResult(win(xMove, oMove))
        }
    }
}