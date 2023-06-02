package jihan.binar.pra_project_final.model.user


import com.google.gson.annotations.SerializedName

data class ResponseUserItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id_users")
    val idUsers: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)