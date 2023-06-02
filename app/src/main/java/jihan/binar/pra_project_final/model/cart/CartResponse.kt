package jihan.binar.pra_project_final.model.cart

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("CartResponse")
	val cartResponse: List<CartResponseItem>
)

data class CartResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("amount")
	val amount: String,

	@field:SerializedName("total")
	val total: String,

	@field:SerializedName("product_image")
	val productImage: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id_cart")
	val idCart: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id_product")
	val idProduct: String,

	@field:SerializedName("category_productId")
	val categoryProductId: String
)
