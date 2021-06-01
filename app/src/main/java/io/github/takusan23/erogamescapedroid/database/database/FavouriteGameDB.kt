package io.github.takusan23.erogamescapedroid.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.takusan23.erogamescapedroid.database.dao.FavouriteGameDao
import io.github.takusan23.erogamescapedroid.database.entity.FavouriteGameEntity

/**
 * データベース
 * */
@Database(entities = [FavouriteGameEntity::class], version = 1)
abstract class FavouriteGameDB : RoomDatabase() {
    abstract fun gameDao(): FavouriteGameDao

    companion object {

        private val favouriteGameDB: FavouriteGameDB? = null

        /** データベースのインスタンスを返す。シングルトン */
        fun getInstance(context: Context): FavouriteGameDB {
            return favouriteGameDB
                ?: Room.databaseBuilder(context, FavouriteGameDB::class.java, "favourite_db.db").build()
        }

    }
}

