package co.id.bismalabs.githubusersearch.model


import com.google.gson.annotations.SerializedName

data class Item(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val url: String,
    @SerializedName("gists_url")
    val gistsUrl: String,
    @SerializedName("starred_url")
    val starredUrl: String,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerializedName("organizations_url")
    val organizationsUrl: String,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    val type: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    val score: Int
)