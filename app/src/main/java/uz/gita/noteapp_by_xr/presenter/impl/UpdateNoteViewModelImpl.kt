package uz.gita.noteapp_by_xr.presenter.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.presenter.UpdateNoteViewModel
import uz.gita.noteapp_by_xr.repository.impl.NoteRepositoryImpl

class UpdateNoteViewModelImpl : UpdateNoteViewModel, ViewModel() {

    private val repository = NoteRepositoryImpl.getInstance()

    override fun update(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(noteData)
        }

    }

    override fun delete(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(noteData)
        }
    }
}