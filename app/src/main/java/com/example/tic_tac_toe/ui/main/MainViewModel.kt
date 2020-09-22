package com.example.tic_tac_toe.ui.main

import android.util.Log
import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.tic_tac_toe.R
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainViewModel
@ViewModelInject
constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val TAG = "MainViewModel"

    fun onClick(view: View) {
        Log.d(TAG, "Navigation mast work")

        when (view.id){
            R.id.two_players_button -> view.findNavController().navigate(R.id.action_mainFragment_to_twoPlayersFragment)
            else -> Log.d(TAG, "not two players")
        }
    }

}