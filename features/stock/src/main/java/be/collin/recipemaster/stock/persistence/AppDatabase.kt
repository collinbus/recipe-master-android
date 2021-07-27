package be.collin.recipemaster.stock.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.collin.recipemaster.stock.persistence.dao.RefrigeratorDao
import be.collin.recipemaster.stock.persistence.entities.StockItemEntity

@Database(entities = [StockItemEntity::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun refrigeratorDao(): RefrigeratorDao

    companion object {
        private var instance: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "RecipeMasterDB"
                    ).addCallback(DatabaseInitializer())
                        .build()
                }
            }
            return instance!!
        }
    }
}