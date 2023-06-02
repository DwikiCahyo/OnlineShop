package jihan.binar.pra_project_final.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.databinding.FragmentProfileBinding
import jihan.binar.pra_project_final.viewmodel.LoginViewModel


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLogin()
        login()
        val id = loginViewModel.getIdPreferences("id")
        userData(id!!)
        binding.btnLogout.setOnClickListener {
            logoutUser()
        }

    }

    private fun userData(idUser:String) {
        loginViewModel.getUserLoginItem(idUser)
        loginViewModel.userItemLogin.observe(viewLifecycleOwner){ user ->
            binding.apply {
                etNameRegist.setText(user.name)
                etEmailRegist.setText(user.email)
                etPasswordRegist.setText(user.password)
                etImage.setText(user.image)
            }
        }
    }

    private fun logoutUser() {
        loginViewModel.getLoginState().observe(viewLifecycleOwner) {
            if (it){
                loginViewModel.saveLoginState(false)
                loginViewModel.logoutPref()
                Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "User Belum Login", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun checkLogin() {
        loginViewModel.getLoginState().observe(viewLifecycleOwner) {
                isLogin(it)
                Log.d("Profile Fragment","Login State $it")
        }
    }

    private fun isLogin(it: Boolean) {
        if (it) {
            binding.layoutProfile.visibility = View.VISIBLE
            binding.layoutNoLoginProfile.visibility = View.GONE
        } else {
            binding.layoutProfile.visibility = View.GONE
            binding.layoutNoLoginProfile.visibility = View.VISIBLE
        }
    }


}