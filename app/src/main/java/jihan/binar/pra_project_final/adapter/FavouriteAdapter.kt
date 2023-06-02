package jihan.binar.pra_project_final.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jihan.binar.pra_project_final.Util
import jihan.binar.pra_project_final.databinding.ItemFavoriteBinding
import jihan.binar.pra_project_final.model.favouriteproducts.FavouriteResponseItem

class FavouriteAdapter(
    private val listFavourite:List<FavouriteResponseItem>,
    private val onSelect:(FavouriteResponseItem) -> Unit
    ):RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    class ViewHolder(private val binding:ItemFavoriteBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite:FavouriteResponseItem, onSelect:(FavouriteResponseItem) -> Unit){
            Glide.with(itemView).load(favorite.productImage).into(binding.imgFavorite)
            binding.txtFavNamaProduk.text = favorite.name
            val price = Util.getPriceIdFormat(favorite.price.toString())
            binding.txtFavHarga.text = price
            binding.btnAddCart.setOnClickListener {
                onSelect(favorite)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteAdapter.ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.ViewHolder, position: Int) {
        holder.bind(listFavourite[position],onSelect)
    }

    override fun getItemCount(): Int  = listFavourite.size
}