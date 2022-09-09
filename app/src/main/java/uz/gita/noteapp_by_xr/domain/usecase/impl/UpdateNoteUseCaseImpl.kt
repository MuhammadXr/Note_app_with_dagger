package uz.gita.noteapp_by_xr.domain.usecase.impl

import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.domain.usecase.UpdateNoteUseCase
import uz.gita.noteapp_by_xr.repository.NoteRepository
import uz.gita.noteapp_by_xr.repository.impl.NoteRepositoryImpl

class UpdateNoteUseCaseImpl : UpdateNoteUseCase {

    private val noteRepository: NoteRepository = NoteRepositoryImpl.getInstance()

    override suspend fun updateNote(noteData: NoteData) {
        noteRepository.updateNote(noteData)
    }

}