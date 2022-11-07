package uz.gita.noteapp_by_xr.presenter.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.domain.usecase.DeleteAllNoteUseCase
import uz.gita.noteapp_by_xr.domain.usecase.DeleteNoteUseCase
import uz.gita.noteapp_by_xr.domain.usecase.GetByTagUseCase
import uz.gita.noteapp_by_xr.domain.usecase.GetNotesUseCase
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
        viewModelScope.launch(Dispatchers.IO) {

            getNotesUseCase.getAllNotes().onEach{
                    list ->
                val filteredList = ArrayList<NoteData>()

                list.forEach {
                    if (filterData.high && it.high)
                        filteredList.add(it)
                    else if (filterData.medium && it.medium)
                        filteredList.add(it)
                    else if (filterData.simple && it.simple)
                        filteredList.add(it)
                }

                notesListLivedata.postValue(filteredList)
            }.collect()
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