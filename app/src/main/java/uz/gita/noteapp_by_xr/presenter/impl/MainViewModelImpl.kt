package uz.gita.noteapp_by_xr.presenter.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.domain.usecase.*
import uz.gita.noteapp_by_xr.domain.usecase.impl.DeleteAllNoteUseCaseImpl
import uz.gita.noteapp_by_xr.domain.usecase.impl.DeleteNoteUseCaseImpl
import uz.gita.noteapp_by_xr.domain.usecase.impl.GetByTagUseCaseImpl
import uz.gita.noteapp_by_xr.domain.usecase.impl.GetNotesUseCaseImpl
import uz.gita.noteapp_by_xr.presenter.MainViewModel
import uz.gita.noteapp_by_xr.utils.eventLiveData
import uz.gita.noteapp_by_xr.utils.eventValueLiveData

class MainViewModelImpl() : MainViewModel, ViewModel() {
    override val openAddNoteLiveData = eventLiveData()
    override val openEditNoteLiveData = eventValueLiveData<NoteData>()
    override val showDeleteDialogLiveData = eventValueLiveData<NoteData>()
    override val notesListLivedata = eventValueLiveData<List<NoteData>>()

    private val getNotesUseCase: GetNotesUseCase = GetNotesUseCaseImpl()
    private val deleteAllNoteUseCase: DeleteAllNoteUseCase = DeleteAllNoteUseCaseImpl()
    private val deleteNoteUseCase: DeleteNoteUseCase = DeleteNoteUseCaseImpl()
    private val getFilterList: GetByTagUseCase = GetByTagUseCaseImpl()

    override fun openAddNoteScreen() {
        openAddNoteLiveData.value = Unit
    }

    override fun openEditNoteScreen(data: NoteData) {
        openEditNoteLiveData.value = data
    }

    override fun showDeleteDialog(data: NoteData) {
        showDeleteDialogLiveData.value = data
    }

    override fun deleteNote(data: NoteData) {
        viewModelScope.launch {
            deleteNoteUseCase.deleteNote(data)
        }
    }

    override fun deleteAllNotes() {
        viewModelScope.launch {
            deleteAllNoteUseCase.deleteAllNotes()
        }
    }

    override fun filterData(filterData: FilterData) {
        viewModelScope.launch {
            getFilterList.getByTag(filterData).collectLatest {
                notesListLivedata.value = it
            }
        }
    }

    init {
        viewModelScope.launch {
            getNotesUseCase.getAllNotes().collectLatest {
                notesListLivedata.value = it
            }
        }
    }
}