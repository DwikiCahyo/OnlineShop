package jihan.binar.pra_project_final.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.databinding.FragmentSecondProductBinding

@AndroidEntryPoint
class SecondProductFragment : Fragment() {

    lateinit var binding : FragmentSecondProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}