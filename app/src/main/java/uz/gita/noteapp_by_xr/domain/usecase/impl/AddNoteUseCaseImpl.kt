package uz.gita.noteapp_by_xr.domain.usecase.impl

import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.domain.usecase.AddNoteUseCase
import uz.gita.noteapp_by_xr.repository.NoteRepository
import uz.gita.noteapp_by_xr.repository.impl.NoteRepositoryImpl

class AddNoteUseCaseImpl : AddNoteUseCase {

    private val noteRepository: NoteRepository = NoteRepositoryImpl.getInstance()

    override suspend fun addNote(noteData: NoteData) {
        noteRepository.addNote(noteData)
    }

}