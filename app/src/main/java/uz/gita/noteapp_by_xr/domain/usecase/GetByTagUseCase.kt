package uz.gita.noteapp_by_xr.domain.usecase

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData

interface GetByTagUseCase {
    suspend fun getByTag(filterData: FilterData): Flow<List<NoteData>>
}