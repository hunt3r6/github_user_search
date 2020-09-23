package co.id.bismalabs.githubusersearch.api

import co.id.bismalabs.githubusersearch.model.DetailFollowers
import co.id.bismalabs.githubusersearch.model.DetailFollowing
import co.id.bismalabs.githubusersearch.model.User
import co.id.bismalabs.githubusersearch.model.ResponseSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    fun fetchSearch(
        @Query("q") query: String
    ): Call<ResponseSearch>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<User>


    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<DetailFollowers>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<DetailFollowing>


}