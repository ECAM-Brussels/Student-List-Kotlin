package be.ecam.lur.studentlist.db

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context


/**
 * Created by qlurk on 26-02-18.
 */

@Database(entities = [(Student::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        private const val DB_NAME = "StudentListDB"

        fun getDatabase(context: Context): AppDatabase {
            val i = INSTANCE
            if(i != null) {
                return i
            }

            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}