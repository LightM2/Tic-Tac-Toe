package com.example.tic_tac_toe.ui.one_player_level

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tic_tac_toe.R
import com.example.tic_tac_toe.databinding.MainFragmentBinding
import com.example.tic_tac_toe.databinding.OnePlayerLevelFragmentBinding
import com.example.tic_tac_toe.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnePlayerLevelFragment : Fragment(R.layout.one_player_level_fragment) {
    private val TAG = "OnePlayerLevelFragment"
    private lateinit var binding: OnePlayerLevelFragmentBinding
    private val viewModel: OnePlayerLevelViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Create fragment")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.one_player_level_fragment, container, false)
        binding.viewModel = viewModel
        return binding.root
    }


}