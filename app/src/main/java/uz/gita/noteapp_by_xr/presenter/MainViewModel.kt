package uz.gita.noteapp_by_xr.presenter

import androidx.lifecycle.LiveData
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData

interface MainViewModel {

    val openAddNoteLiveData: LiveData<Unit>
    val openEditNoteLiveData: LiveData<NoteData>
    val showDeleteDialogLiveData: LiveData<NoteData>

    val notesListLivedata: LiveData<List<NoteData>>

    fun openAddNoteScreen()

    fun openEditNoteScreen(data: NoteData)

    fun showDeleteDialog(data: NoteData)

    fun deleteNote(data: NoteData)

    fun deleteAllNotes()

    fun filterData(filterData: FilterData)

}