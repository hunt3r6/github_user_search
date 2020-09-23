package co.id.bismalabs.githubusersearch.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favoriteUser"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val AVATAR = "avatar"
            const val NAME = "name"
            const val PUBLIC_REPOS = "publicRepos"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
        }
    }
}