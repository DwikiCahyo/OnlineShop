package jihan.binar.pra_project_final.network

import jihan.binar.pra_project_final.model.cart.CartResponse
import jihan.binar.pra_project_final.model.cart.CartResponseItem
import jihan.binar.pra_project_final.model.favouriteproducts.FavouriteResponse
import jihan.binar.pra_project_final.model.favouriteproducts.FavouriteResponseItem
import jihan.binar.pra_project_final.model.news.ResponseNewsItem
import jihan.binar.pra_project_final.model.products.DetailProductResponse
import jihan.binar.pra_project_final.model.products.ProductResponse
import jihan.binar.pra_project_final.model.products.ProductResponseItem
import jihan.binar.pra_project_final.model.slider.SliderResponseItem
import jihan.binar.pra_project_final.model.user.ResponseUser
import jihan.binar.pra_project_final.model.user.ResponseUserItem
import jihan.binar.pra_project_final.model.user.UserResponse
import jihan.binar.pra_project_final.model.user.UserResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET ("users")
    fun getUser() : Call<List<UserResponseItem>>

    @GET("users/{id}")
    fun getUserItem(
        @Path("id") id:String
    ):Call<UserResponseItem>

    @POST("users") // Endpoint untuk registrasi user
    fun registerUser(
        @Body user: UserResponseItem
    ): Call<UserResponseItem>

//    @POST ("users")
//    fun getRegist() : Call<List<UserResponseItem>>

    @GET("sliders")
    fun getSliders():Call<List<SliderResponseItem>>

    @GET("news_update")
    fun getNews(
    ) : Call<List<ResponseNewsItem>>

    @GET("news_update/{id}")
    fun getNewsUpdateDetail(
        @Path("id") id:String
    ):Call<ResponseNewsItem>

    @GET("category_product/{id}/products")
    fun getProducts(
        @Path("id") id:String
    ):Call<List<ProductResponseItem>>

    @GET("category_product/1/products/{id}")
    fun getNewProductsDetail(
        @Path("id") id:String
    ):Call<DetailProductResponse>

    @GET("category_product/2/products/{id}")
    fun getSecondProductsDetail(
        @Path("id") id:String
    ):Call<DetailProductResponse>

    @GET("users/{id}/favourite")
    fun getFavouriteProducts(
        @Path("id") id:String
    ):Call<List<FavouriteResponseItem>>

    @FormUrlEncoded
    @POST("users/{id}/favourite")
    fun addFavouriteProduct(
        @Path("id") id:String,
        @Field("name") name:String,
        @Field("product_image") productImage:String,
        @Field("price") price:Int,
        @Field("description") description:String,
    ):Call<FavouriteResponseItem>

    @DELETE("users/{userId}/favourite/{id}")
    fun deleteFavouriteProduct(
        @Path("userId") userId:String,
        @Path("id") id:String
    ):Call<FavouriteResponseItem>

    @GET("users/{id}/cart")
    fun getCart(
        @Path("id") id:String
    ):Call<List<CartResponseItem>>

    @FormUrlEncoded
    @POST("users/{id}/cart")
    fun addCart(
        @Path("id") id:String,
        @Field("name") name:String,
        @Field("product_image") productImage:String,
        @Field("price") price:Int,
        @Field("description") description:String,
    ):Call<CartResponseItem>


}