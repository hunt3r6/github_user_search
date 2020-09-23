package co.id.bismalabs.githubusersearch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.bismalabs.githubusersearch.api.ApiGithubService
import co.id.bismalabs.githubusersearch.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val detailUser = MutableLiveData<User>()
    private val followersMutable = MutableLiveData<ArrayList<DetailFollowersItem>>()
    private val followingMutable = MutableLiveData<ArrayList<DetailFollowingItem>>()

    fun setFollowing(username: String) {
        val followingItem =ArrayList<DetailFollowingItem>()
        ApiGithubService().getService().getFollowing(username)
            .enqueue(object : Callback<DetailFollowing> {
                override fun onResponse(
                    call: Call<DetailFollowing>,
                    response: Response<DetailFollowing>
                ) {
                    response.body()?.let {
                        followingMutable.postValue(it)
                    }

                }

                override fun onFailure(call: Call<DetailFollowing>, t: Throwable) {
                }

            })
    }

    fun setFollowers(username : String){
        val followers = ArrayList<DetailFollowersItem>()
        ApiGithubService().getService().getFollowers(username)
            .enqueue(object : Callback<DetailFollowers> {
                override fun onResponse(
                    call: Call<DetailFollowers>,
                    response: Response<DetailFollowers>
                ) {
                    response.body()?.let {
                        followers.addAll(it)
                        Log.d("OnViewModel", "Followers : $it")
                    }

                    followersMutable.postValue(followers)
                }

                override fun onFailure(call: Call<DetailFollowers>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

     fun loadDetail(username: String) {
        ApiGithubService().getService().getDetailUser(username)
            .enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    response.body().let {
                        detailUser.postValue(it)
                    }

                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                }

            })
    }

    private val listUsers = MutableLiveData<ArrayList<Item>>()

    fun setSearchUser(username: String) {
        val user = ArrayList<Item>()

        ApiGithubService().getService().fetchSearch(username)
            .enqueue(object : Callback<ResponseSearch> {
                override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {

                }
                override fun onResponse(
                    call: Call<ResponseSearch>,
                    response: Response<ResponseSearch>
                ) {
                    response.body()?.items?.let {
                        user.addAll(it)
                        Log.d("OnViewModel", "onResponse: $it")
                    }
                    listUsers.postValue(user)
                }
            })

    }



    fun getUserGithub(): LiveData<ArrayList<Item>> {
        return listUsers
    }

    fun getDetailUser():LiveData<User> {
        return detailUser
    }

    fun getFollowers(): LiveData<ArrayList<DetailFollowersItem>> {
        return followersMutable
    }

    fun getFollowing(): LiveData<ArrayList<DetailFollowingItem>> {
        return followingMutable
    }

}