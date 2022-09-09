package uz.gita.noteapp_by_xr.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.noteapp_by_xr.data.models.NoteData

interface NoteRepository {

    suspend fun addNote(noteData: NoteData)

    suspend fun updateNote(noteData: NoteData)

    suspend fun deleteNote(noteData: NoteData)

    suspend fun deleteAllNotes()

    fun getAllNotes(): Flow<List<NoteData>>

}