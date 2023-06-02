package jihan.binar.pra_project_final.model.slider

import com.google.gson.annotations.SerializedName

data class SliderResponse(

	@field:SerializedName("SliderResponse")
	val sliderResponse: List<SliderResponseItem>
)

data class SliderResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id")
	val id: String
)
