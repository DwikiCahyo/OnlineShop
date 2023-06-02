package jihan.binar.pra_project_final.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.adapter.CartAdapter
import jihan.binar.pra_project_final.databinding.FragmentCartBinding
import jihan.binar.pra_project_final.viewmodel.CartViewModel
import jihan.binar.pra_project_final.viewmodel.LoginViewModel

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var binding : FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val loginViewModel: LoginViewModel by viewModels()
    private val cartViewModel:CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = loginViewModel.getIdPreferences("id")
        checkLogin(id!!)
        cartList(id)
    }

    private fun cartList(id:String) {
        cartViewModel.getCartFavorite(id)
        cartViewModel.listCart.observe(viewLifecycleOwner){
            binding.rvCart.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                cartAdapter = CartAdapter(it)
                adapter = cartAdapter
            }
        }


    }

    private fun checkLogin(id:String) {
        loginViewModel.getLoginState().observe(viewLifecycleOwner){
            isLogin(it)
        }

        Log.d("CartFragment", "id $id")
        listCart(id)
        login()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            view?.post {
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }

    private fun listCart(idUser: String?) {
        cartViewModel.getCartFavorite(idUser!!)
        cartViewModel.listCart.observe(viewLifecycleOwner){
            binding.tvJumlahBarang.text = "Jumlah Barang dalam Keranjang : ${it.size}"
        }
    }

    private fun isLogin(isLogin: Boolean) {
        if (isLogin){
            binding.layoutCart.visibility = View.VISIBLE
            binding.layoutNoLoginCart.visibility = View.GONE
        } else {
            binding.layoutCart.visibility = View.GONE
            binding.layoutNoLoginCart.visibility = View.VISIBLE
        }
    }

}