package co.id.bismalabs.githubusersearch.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.id.bismalabs.githubusersearch.db.DatabaseContract.UserColumns
import co.id.bismalabs.githubusersearch.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbusergithub"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${UserColumns._ID} INTEGER PRIMARY KEY," +
                " ${UserColumns.USERNAME} TEXT NOT NULL," +
                " ${UserColumns.NAME} TEXT NOT NULL," +
                " ${UserColumns.AVATAR} TEXT NOT NULL," +
                " ${UserColumns.PUBLIC_REPOS} INTEGER," +
                " ${UserColumns.FOLLOWERS} INTEGER," +
                " ${UserColumns.FOLLOWING} INTEGER)"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}