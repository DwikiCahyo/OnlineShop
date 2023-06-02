package jihan.binar.pra_project_final.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.databinding.FragmentLoginBinding
import jihan.binar.pra_project_final.viewmodel.LoginViewModel


@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val loginViewModel:LoginViewModel by viewModels()
    private var isSuccesLogin = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.getUserLogin()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            userValidation(email,pass)
            Log.d("Login Fragment", isSuccesLogin.toString())

            if(isSuccesLogin){
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                loginViewModel.saveLoginState(true)
            } else {
                Toast.makeText(requireContext(), "Pastikan email dan Password anda benar", Toast.LENGTH_SHORT).show()
            }
        }


        binding.txtRegist.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registFragment)
        }



    }

    private fun userValidation(email:String,pass:String) {
        loginViewModel.userLogin.observe(requireActivity()) {
            //when get data success, validate email and password to login
            for (i in it.indices) {
                //validate email and password using index data
                val emailValidate = it[i]
                val passValidate = it[i]

                //create conditional to make sure email and password is available in response data
                if (email == emailValidate.email && pass == passValidate.password) {
                    Log.d("Login Fragment", "email validate: $emailValidate ")
                    Log.d("Login Fragment", email)
                    Log.d("Login Fragment", "pass validate: $passValidate ")
                    Log.d("Login Fragment", pass)
                    //get id
                    val id = it[i].idUsers
                    Log.d("Login Fragment", id.toString())
                    loginViewModel.saveIdPreferences("id",id!!)


                    isSuccesLogin = true
                    break
                } else if (email != emailValidate.email && pass == passValidate.password) {
                    isSuccesLogin = false
                } else if (email == emailValidate.email && pass != passValidate.password) {
                    isSuccesLogin = false
                } else {
                    isSuccesLogin = false
                }
            }
        }
    }


}