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
import com.example.tic_tac_toe.game_logic.Cell
import com.example.tic_tac_toe.game_logic.Seed
import com.example.tic_tac_toe.game_logic.addMove
import com.example.tic_tac_toe.game_logic.win
import com.example.tic_tac_toe.ui.base.BaseGameViewModel

class TwoPlayersViewModel
@ViewModelInject
constructor(
        @Assisted private val savedStateHandle: SavedStateHandle
): BaseGameViewModel(){

    override val TAG = "TwoPlayersViewModel"

    private var lastMove = ""

    fun onClick(cell: Cell) {
        Log.d(TAG, "OnClick")

        val position = cell.rowAndCol
        lastMove = when (lastMove){
            "" -> {
                board[position.first][position.second].content = Seed.X
                "X"
            }
            "X" -> {
                board[position.first][position.second].content = Seed.O
                "O"
            }
            "O" -> {
                board[position.first][position.second].content = Seed.X
                "X"
            }
            else -> "e"
        }

        addMove(board, xMove, oMove, position, board[position.first][position.second].content)
        board[position.first][position.second].text = lastMove

        notifyChange()

        val result = win(xMove, oMove)
        Log.d(TAG, "Result $result")
        if(result != null){
            showResult(result)
            lastMove = ""
        }

    }

}