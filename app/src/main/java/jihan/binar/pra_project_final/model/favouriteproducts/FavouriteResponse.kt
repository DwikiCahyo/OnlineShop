package jihan.binar.pra_project_final.model.favouriteproducts

import com.google.gson.annotations.SerializedName

data class FavouriteResponse(

	@field:SerializedName("FavouriteResponse")
	val favouriteResponse: List<FavouriteResponseItem>
)

data class FavouriteResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("product_image")
	val productImage: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id_fav")
	val idFav: String,

	@field:SerializedName("userId")
	val userId: String
)
