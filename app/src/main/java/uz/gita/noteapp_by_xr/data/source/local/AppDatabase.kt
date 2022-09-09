package uz.gita.noteapp_by_xr.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        NoteEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {

        private lateinit var _instance: AppDatabase
        fun init(context: Context) {
            if (!Companion::_instance.isInitialized)
                _instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, "note_database").build()
        }

        val INSTANCE get() = _instance
    }
}