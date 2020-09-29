package com.example.tic_tac_toe.ui.one_player_hard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tic_tac_toe.R
import com.example.tic_tac_toe.databinding.OnePlayerHardFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnePlayerHardFragment : Fragment(R.layout.one_player_hard_fragment) {
    private val TAG = "OnePlayerHardFragment"
    private lateinit var binding: OnePlayerHardFragmentBinding
    private val viewModel: OnePlayerHardViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Create fragment")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.one_player_hard_fragment, container, false)
        binding.viewModel = viewModel
        return binding.root
    }


}