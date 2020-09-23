package co.id.bismalabs.githubusersearch.helper

import android.database.Cursor
import co.id.bismalabs.githubusersearch.db.DatabaseContract.UserColumns
import co.id.bismalabs.githubusersearch.model.User

object MappingHelper {
    fun mapCursorToArrayList(noteCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        noteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(UserColumns._ID))
                val userName = getString(getColumnIndexOrThrow(UserColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(UserColumns.NAME))
                val avatarUrl = getString(getColumnIndexOrThrow(UserColumns.AVATAR))
                val repository = getInt(getColumnIndexOrThrow(UserColumns.PUBLIC_REPOS))
                val followers = getInt(getColumnIndexOrThrow(UserColumns.FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(UserColumns.FOLLOWING))

                userList.add(User(id, userName, name, avatarUrl, repository, followers, following))
            }
        }

        return userList
    }
}