package com.example.tic_tac_toe.ui.two_players

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tic_tac_toe.R
import com.example.tic_tac_toe.databinding.TwoPlayersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TwoPlayersFragment : Fragment(R.layout.two_players_fragment) {
    private val TAG = "TwoPlayersFragment"
    private lateinit var binding: TwoPlayersFragmentBinding
    private val viewModel: TwoPlayersViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Create fragment")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.two_players_fragment, container, false)
        binding.viewModel = viewModel
        return binding.root
    }


}