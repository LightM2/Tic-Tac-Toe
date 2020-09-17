package com.example.tic_tac_toe.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.tic_tac_toe.ui.main.MainFragment
import javax.inject.Inject

class MainFragmentFactory
@Inject
constructor(
    //private val mainRepository: MainRepository
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className){
            MainFragment::class.java.name -> {
                val fragment = MainFragment()
                fragment
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}