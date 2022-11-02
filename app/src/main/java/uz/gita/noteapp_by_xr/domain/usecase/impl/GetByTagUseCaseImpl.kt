package uz.gita.noteapp_by_xr.domain.usecase.impl

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.domain.usecase.GetByTagUseCase
import uz.gita.noteapp_by_xr.repository.NoteRepository
import uz.gita.noteapp_by_xr.repository.impl.NoteRepositoryImpl

class GetByTagUseCaseImpl : GetByTagUseCase {

    private val repository: NoteRepository = NoteRepositoryImpl()

    override suspend fun getByTag(filterData: FilterData): Flow<List<NoteData>> {
        return repository.getByTag(filterData)
    }
}