package co.id.bismalabs.githubusersearch.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("login")
    val username: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,


    @SerializedName("public_repos")
    val repository: Int? = null,

    @SerializedName("followers")

    val followers: Int? = null,

    @SerializedName("following")
    val following: Int? = null

)