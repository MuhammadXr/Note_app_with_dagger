package uz.gita.noteapp_by_xr.domain.usecase

import uz.gita.noteapp_by_xr.data.models.NoteData

interface AddNoteUseCase {

    suspend fun addNote(noteData: NoteData)

}