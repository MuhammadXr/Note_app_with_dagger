package uz.gita.noteapp_by_xr.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.noteapp_by_xr.data.models.NoteData


interface GetNotesUseCase {

    fun getAllNotes(): Flow<List<NoteData>>

}