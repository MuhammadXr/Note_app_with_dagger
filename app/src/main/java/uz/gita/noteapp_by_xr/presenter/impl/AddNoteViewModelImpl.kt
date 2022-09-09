package uz.gita.noteapp_by_xr.presenter.impl

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.presenter.AddNoteViewModel
import uz.gita.noteapp_by_xr.repository.impl.NoteRepositoryImpl

class AddNoteViewModelImpl : AddNoteViewModel, ViewModel() {

    private val repository = NoteRepositoryImpl.getInstance()

    override val noteDataLive: LiveData<NoteData> = MutableLiveData()

    override fun addToBase(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(noteData)
        }

    }
}