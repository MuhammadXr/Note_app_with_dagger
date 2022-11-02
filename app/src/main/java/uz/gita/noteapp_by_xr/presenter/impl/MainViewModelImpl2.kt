package uz.gita.noteapp_by_xr.presenter.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.presenter.MainViewModel

class MainViewModelImpl2 : MainViewModel, ViewModel() {
    override val openAddNoteLiveData: LiveData<Unit>
        get() = TODO("Not yet implemented")
    override val openEditNoteLiveData: LiveData<NoteData>
        get() = TODO("Not yet implemented")
    override val showDeleteDialogLiveData: LiveData<NoteData>
        get() = TODO("Not yet implemented")
    override val notesListLivedata: LiveData<List<NoteData>>
        get() = TODO("Not yet implemented")

    override fun openAddNoteScreen() {
        TODO("Not yet implemented")
    }

    override fun openEditNoteScreen(data: NoteData) {
        TODO("Not yet implemented")
    }

    override fun showDeleteDialog(data: NoteData) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(data: NoteData) {
        TODO("Not yet implemented")
    }

    override fun deleteAllNotes() {
        TODO("Not yet implemented")
    }

    override fun filterData(filterData: FilterData) {
        TODO("Not yet implemented")
    }
}