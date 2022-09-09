package uz.gita.noteapp_by_xr.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.domain.usecase.GetNotesUseCase
import uz.gita.noteapp_by_xr.repository.NoteRepository
import uz.gita.noteapp_by_xr.repository.impl.NoteRepositoryImpl

class GetNotesUseCaseImpl : GetNotesUseCase {

    private val noteRepository: NoteRepository = NoteRepositoryImpl.getInstance()

    override fun getAllNotes(): Flow<List<NoteData>> = noteRepository.getAllNotes()

}