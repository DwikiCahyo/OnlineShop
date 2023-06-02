package jihan.binar.pra_project_final.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jihan.binar.pra_project_final.Util
import jihan.binar.pra_project_final.databinding.ItemCartBinding
import jihan.binar.pra_project_final.model.cart.CartResponseItem

class CartAdapter(private  val listCart:List<CartResponseItem>):RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(private val binding:ItemCartBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(cart:CartResponseItem){
            binding.txtCartNamaProduk.text = cart.name
            val price = Util.getPriceIdFormat(cart.price)
            binding.txtCartHarga.text = price
            Glide.with(itemView).load(cart.productImage).into(binding.imgCart)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        holder.bind(listCart[position])
    }

    override fun getItemCount(): Int = listCart.size


}