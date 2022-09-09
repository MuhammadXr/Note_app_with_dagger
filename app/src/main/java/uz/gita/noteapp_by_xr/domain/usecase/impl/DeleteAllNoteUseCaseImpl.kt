package uz.gita.noteapp_by_xr.domain.usecase.impl

import uz.gita.noteapp_by_xr.domain.usecase.DeleteAllNoteUseCase
import uz.gita.noteapp_by_xr.repository.NoteRepository
import uz.gita.noteapp_by_xr.repository.impl.NoteRepositoryImpl

class DeleteAllNoteUseCaseImpl : DeleteAllNoteUseCase {

    private val noteRepository: NoteRepository = NoteRepositoryImpl.getInstance()

    override suspend fun deleteAllNotes() {
        noteRepository.deleteAllNotes()
    }
}