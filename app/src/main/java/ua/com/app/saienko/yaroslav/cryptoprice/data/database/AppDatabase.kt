package ua.com.app.saienko.yaroslav.cryptoprice.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Сінглтон не можна зробити через обджект, так як базу створюємо не ми
@Database(entities = [CoinInfoDbModel::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"

        // об'єкт на якому синхронізуємось,
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration() // не зберігати дані в БД при міграції
                        .build()
                db = instance
                return instance
            }
        }
    }

    // абстрактний метод який повертає CoinPriceInfoDao
    abstract fun coinPriceInfoDao(): CoinInfoDao

}