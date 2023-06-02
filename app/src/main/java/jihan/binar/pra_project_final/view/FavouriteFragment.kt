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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.adapter.FavouriteAdapter
import jihan.binar.pra_project_final.databinding.FragmentFavouriteBinding
import jihan.binar.pra_project_final.viewmodel.CartViewModel
import jihan.binar.pra_project_final.viewmodel.FavouriteViewModel
import jihan.binar.pra_project_final.viewmodel.LoginViewModel

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    lateinit var binding : FragmentFavouriteBinding
    private val loginViewModel:LoginViewModel by viewModels()
    private val favouriteViewModel:FavouriteViewModel by viewModels()
    private val cartViewModel:CartViewModel by viewModels()
    private lateinit var favAdapter:FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLogin()
        login()
    }

    private fun listFav(idUser:String) {
        favouriteViewModel.getFavouriteProducts(idUser)
        favouriteViewModel.listFavourite.observe(viewLifecycleOwner){
            binding.tvJumlahBarang.text = "Jumlah Barang : ${it.size}"
            binding.rvFavorite.apply {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                favAdapter = FavouriteAdapter(it){ favItem ->
                    val id = loginViewModel.getIdPreferences("id")
                    cartViewModel.addCart(id!!,favItem.name,favItem.productImage,favItem.price,favItem.description)
                    Toast.makeText(context, "Success Add to Cart", Toast.LENGTH_SHORT).show()
                }
                adapter = favAdapter
            }
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            view?.post {
                findNavController().navigate(R.id.action_favouriteFragment_to_loginFragment)
            }
        }
    }

    private fun checkLogin() {
        loginViewModel.getLoginState().observe(requireActivity()) {
            isLogin(it)
            Log.d("FavouriteFragment", "login $it")
        }
        val id  = loginViewModel.getIdPreferences("id")!!
        Log.d("FavouriteFragment", "id $id")
        listFav(id)
    }

    private fun isLogin(it: Boolean) {
        if (it) {
            binding.layoutFavorite.visibility = View.VISIBLE
            binding.layoutNoLoginFavorite.visibility = View.GONE
        } else {
            binding.layoutFavorite.visibility = View.GONE
            binding.layoutNoLoginFavorite.visibility = View.VISIBLE
        }
    }


}