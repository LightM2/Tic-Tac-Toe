package com.example.tic_tac_toe.ui.one_player_level

import android.util.Log
import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.tic_tac_toe.R

class OnePlayerLevelViewModel
@ViewModelInject
constructor(
        @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val TAG = "OnePlayerLevelViewModel "

    fun onClick(view: View) {
        Log.d(TAG, "Navigation mast work")

        when (view.id){
            R.id.hard_level_button -> view.findNavController().navigate(R.id.action_onePlayerLevelFragment_to_onePlayerHardFragment)
            R.id.easy_level_button -> view.findNavController().navigate(R.id.action_onePlayerLevelFragment_to_onePlayerEasyFragment)
            else -> Log.d(TAG, "not hard or easy")
        }
    }

}