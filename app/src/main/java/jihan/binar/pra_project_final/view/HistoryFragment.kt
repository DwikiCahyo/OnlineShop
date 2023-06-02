package jihan.binar.pra_project_final.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.databinding.FragmentHistoryBinding
import jihan.binar.pra_project_final.viewmodel.LoginViewModel

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding:FragmentHistoryBinding
    private val loginViewModel:LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.getLoginState().observe(requireActivity()){
            Log.d("HistoryFragment", "login $it")
            if(it){
                binding.layoutHisory.visibility = View.VISIBLE
                binding.layoutNoLoginHistory.visibility = View.GONE
            } else{
                binding.layoutHisory.visibility = View.GONE
                binding.layoutNoLoginHistory.visibility = View.VISIBLE
            }
        }
        val id = loginViewModel.getIdPreferences("id")
        Log.d("HistoryFragment", "id $id")


    }

}