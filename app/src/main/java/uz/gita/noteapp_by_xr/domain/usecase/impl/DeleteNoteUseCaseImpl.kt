package uz.gita.noteapp_by_xr.domain.usecase.impl

import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.domain.usecase.DeleteNoteUseCase
import uz.gita.noteapp_by_xr.repository.NoteRepository
import uz.gita.noteapp_by_xr.repository.impl.NoteRepositoryImpl

class DeleteNoteUseCaseImpl : DeleteNoteUseCase {

    private val noteRepository: NoteRepository = NoteRepositoryImpl.getInstance()

    override suspend fun deleteNote(noteData: NoteData) {
        noteRepository.deleteNote(noteData)
    }

}