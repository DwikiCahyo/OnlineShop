package jihan.binar.pra_project_final.model.products

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("id_product")
	val idProduct: String,

	@field:SerializedName("product_image")
	val productImage: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("category_productId")
	val categoryProductId: String
)
