package jihan.binar.pra_project_final.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.Util
import jihan.binar.pra_project_final.databinding.ItemNewProductBinding
import jihan.binar.pra_project_final.model.products.ProductResponseItem
import java.text.NumberFormat
import java.util.*

class ProductAdapter (private val listProduct:List<ProductResponseItem>):RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder (private val binding:ItemNewProductBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(product:ProductResponseItem){
            var navController: NavController? = null
            Glide.with(itemView).load(product.productImage).into(binding.imgNewProduk)
            binding.txtNewNamaProduk.text = product.name
            val price = Util.getPriceIdFormat(product.price)
            binding.txtNewHargaProduk.text = price

            binding.cvProduct.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("productId",product.idProduct)
                bundle.putString("categoryId",product.categoryProductId)
                navController = Navigation.findNavController(itemView)
                navController!!.navigate(R.id.action_homeFragment_to_detailFragment,bundle)
//                .navigate(R.id.action_homeFragment_to_detailFragment,bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val binding = ItemNewProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
       holder.bind(listProduct[position])
    }

    override fun getItemCount(): Int = listProduct.size

}