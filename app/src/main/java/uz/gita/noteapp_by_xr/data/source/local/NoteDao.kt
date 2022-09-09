package uz.gita.noteapp_by_xr.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(data: NoteEntity)

    @Delete
    suspend fun deleteNote(data: NoteEntity)

    @Query("DELETE  FROM NoteEntity")
    suspend fun deleteAllNotes()

    @Update
    suspend fun updateNote(data: NoteEntity)

    @Query("SELECT * FROM NoteEntity")
    fun getNotes(): Flow<List<NoteEntity>>

}