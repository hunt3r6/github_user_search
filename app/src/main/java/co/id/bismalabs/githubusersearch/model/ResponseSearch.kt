package co.id.bismalabs.githubusersearch.model


import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<Item>
)