package uz.gita.noteapp_by_xr.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData

interface NoteRepository {

    suspend fun addNote(noteData: NoteData)

    suspend fun updateNote(noteData: NoteData)

    suspend fun deleteNote(noteData: NoteData)

    suspend fun deleteAllNotes()

    fun getAllNotes(): Flow<List<NoteData>>

    fun getByTag(filterData: FilterData): Flow<List<NoteData>>

}