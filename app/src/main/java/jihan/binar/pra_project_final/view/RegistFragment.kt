package jihan.binar.pra_project_final.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.databinding.FragmentRegistBinding
import jihan.binar.pra_project_final.viewmodel.RegisterViewModel

@AndroidEntryPoint
class RegistFragment : Fragment() {

    lateinit var binding: FragmentRegistBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegist.setOnClickListener {
            val email = binding.etEmailRegist.text.toString()
            val username = binding.etNameRegist.text.toString()
            val password = binding.etPasswordRegist.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.registerUser(username, password, email)
                findNavController().navigate(R.id.action_registFragment_to_loginFragment)
            } else {
                Toast.makeText(context, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        observeRegistrationResult()
    }

    private fun observeRegistrationResult() {
        viewModel.registrationResult.observe(viewLifecycleOwner) { result ->
            if (result.success) {
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(
                    context,
                    "Registration failed: ${result.errorMessage}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}