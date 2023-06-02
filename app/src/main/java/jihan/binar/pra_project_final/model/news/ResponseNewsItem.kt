package jihan.binar.pra_project_final.model.news


import com.google.gson.annotations.SerializedName

data class ResponseNewsItem(
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id_news")
    val idNews: String,
    @SerializedName("news_image")
    val newsImage: String,
    @SerializedName("title")
    val title: String
)