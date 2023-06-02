package jihan.binar.pra_project_final.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.Util
import jihan.binar.pra_project_final.databinding.FragmentDetailBinding
import jihan.binar.pra_project_final.model.products.DetailProductResponse
import jihan.binar.pra_project_final.viewmodel.CartViewModel
import jihan.binar.pra_project_final.viewmodel.DetailViewModel
import jihan.binar.pra_project_final.viewmodel.FavouriteViewModel
import jihan.binar.pra_project_final.viewmodel.LoginViewModel

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding : FragmentDetailBinding
    private val detailViewModel:DetailViewModel by viewModels()
    private val loginViewModel:LoginViewModel by viewModels()
    private val favouriteViewModel:FavouriteViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private var isProductFavorite  = false
    private var idFavorite:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getString("productId")
        val categoryId = arguments?.getString("categoryId")

        if (categoryId == "1") detailNewProduct(productId!!) else detailSecondProduct(productId!!)
        checkLogin()

    }

    private fun checkLogin() {
        binding.toggleFavorit.setOnClickListener {
            loginViewModel.getLoginState().observe(viewLifecycleOwner) {
                if (it) {
                    isProductFavorite = !isProductFavorite
                    if (isProductFavorite){
                        addFavoritemNew()
                        addFavoriteSecond()
                    } else {
                        removeFavourite()
                    }
                } else {
                    binding.toggleFavorit.setBackgroundResource(R.drawable.favorite)
                    Toast.makeText(requireContext(), "User Belum Login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addFavoritemNew() {
        detailViewModel.itemNewDetail.observe(viewLifecycleOwner) {
            val name = it.name
            val productImage = it.productImage
            val price = it.price.toInt()
            val desc = it.description
            val id = loginViewModel.getIdPreferences("id")
            addFavorite(id!!, name, productImage, price, desc)
            Toast.makeText(requireContext(), "on clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFavourite(){
        val id = loginViewModel.getIdPreferences("id")
        favouriteViewModel.deleteFavouriteProducts(id!!,idFavorite!!)
        Toast.makeText(requireContext(), "Success delete from favorite", Toast.LENGTH_SHORT).show()

    }

    private fun addFavoriteSecond() {
        detailViewModel.itemSecondDetail.observe(viewLifecycleOwner) {
            val name = it.name
            val productImage = it.productImage
            val price = it.price.toInt()
            val desc = it.description
            val id = loginViewModel.getIdPreferences("id")
            addFavorite(id!!, name, productImage, price, desc)
            Toast.makeText(requireContext(), "on clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addFavorite(id:String,name:String,productImage:String,price:Int,desc:String) {

        favouriteViewModel.postFavouriteProducts(id,name,productImage,price,desc)
        favouriteViewModel.itemFavourite.observe(requireActivity()){
            if (it != null){
                Toast.makeText(requireContext(), "Berhasil menambahkan ke favorit", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun detailSecondProduct(productId: String) {
        detailViewModel.getDetailSecondProduct(productId)
        detailViewModel.itemSecondDetail.observe(requireActivity()){ detailproduct ->
            binding.apply {

                txtDetailDeskripsi.text = detailproduct.description
                txtDetailNamaProduk.text = detailproduct.name
                val priceIdr = Util.getPriceIdFormat(detailproduct.price)
                txtDetailHargaProduk.text = priceIdr
                Glide.with(requireContext()).load(detailproduct.productImage).into(imgDetailProduk)
            }

            val id = loginViewModel.getIdPreferences("id")
            //check is prodcut in favorite
            favouriteViewModel.getFavouriteProducts(id!!)
            favouriteViewModel.listFavourite.observe(viewLifecycleOwner){
                for (i in it.indices){
                    if (it[i].name == detailproduct.name){
                        isProductFavorite = true
                        binding.toggleFavorit.isChecked = true
                        idFavorite = it[i].idFav
                        Log.d("DetailFragment","idFavorite : $idFavorite")
                        break
                    } else {
                        isProductFavorite = false
                        binding.toggleFavorit.isChecked = false
                    }
                }
            }
            // add product to cart
            loginViewModel.getLoginState().observe(viewLifecycleOwner){ isLogin ->
                binding.btnAddToCart.setOnClickListener {
                    if (isLogin){
                        addCart(id, detailproduct)
                    } else {
                        Toast.makeText(context, "User Belum Login", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun addCart(
        id: String?,
        detailproduct: DetailProductResponse
    ) {
        Toast.makeText(requireContext(), "Clickkked", Toast.LENGTH_SHORT).show()
        cartViewModel.addCart(
            id!!,
            detailproduct.name,
            detailproduct.productImage,
            detailproduct.price.toInt(),
            detailproduct.description
        )
        cartViewModel.itemCart.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Berhasil menambahkan ke cart", Toast.LENGTH_SHORT)
                    .show()
            } else  {
                Toast.makeText(requireContext(), "Gagal menambahkan ke cart", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun detailNewProduct(productId: String) {
        detailViewModel.getDetailNewProduct(productId)
        detailViewModel.itemNewDetail.observe(requireActivity()){detailProduct ->
            binding.apply {
                txtDetailDeskripsi.text = detailProduct.description
                txtDetailNamaProduk.text = detailProduct.name
                val price = Util.getPriceIdFormat(detailProduct.price)
                txtDetailHargaProduk.text = price
                Glide.with(requireContext()).load(detailProduct.productImage).into(imgDetailProduk)
            }
            //check is prodcut in favorite
            val id = loginViewModel.getIdPreferences("id")
            favouriteViewModel.getFavouriteProducts(id!!)
            favouriteViewModel.listFavourite.observe(viewLifecycleOwner){
                for (i in it.indices){
                    if (it[i].name == detailProduct.name){
                        isProductFavorite = true
                        binding.toggleFavorit.isChecked = true
                        idFavorite = it[i].idFav
                        Log.d("DetailFragment","idFavorite : $idFavorite")
                        break
                    } else {
                        isProductFavorite = false
                        binding.toggleFavorit.isChecked = false
                    }
                }
            }
                 loginViewModel.getLoginState().observe(viewLifecycleOwner){ isLogin ->
                 binding.btnAddToCart.setOnClickListener {
                    if (isLogin){
                        addCart(id, detailProduct)
                    } else {
                        Toast.makeText(context, "User Belum Login", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}