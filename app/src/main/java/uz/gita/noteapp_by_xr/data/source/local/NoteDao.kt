package uz.gita.noteapp_by_xr.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.noteapp_by_xr.data.models.FilterData

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(data: NoteEntity)

    @Delete
    suspend fun deleteNote(data: NoteEntity)

    @Query("DELETE FROM NoteEntity")
    suspend fun deleteAllNotes()

    @Update
    suspend fun updateNote(data: NoteEntity)

    @Query("SELECT * FROM NoteEntity")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHere high=:high and simple=:simple and medium=:medium")
    fun getByTag(simple: Boolean, medium:Boolean, high: Boolean): Flow<List<NoteEntity>>
}