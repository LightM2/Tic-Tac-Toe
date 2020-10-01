package com.example.tic_tac_toe.ui.one_player_easy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tic_tac_toe.R
import com.example.tic_tac_toe.databinding.OnePlayerEasyFragmentBinding
import com.example.tic_tac_toe.databinding.OnePlayerHardFragmentBinding
import com.example.tic_tac_toe.ui.one_player_hard.OnePlayerHardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnePlayerEasyFragment : Fragment(R.layout.one_player_easy_fragment) {
    private val TAG = "OnePlayerEasyFragment"
    private lateinit var binding: OnePlayerEasyFragmentBinding
    private val viewModel: OnePlayerEasyViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Create fragment")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.one_player_easy_fragment, container, false)
        binding.viewModel = viewModel
        return binding.root
    }


}