package uz.gita.noteapp_by_xr.presenter

import androidx.lifecycle.LiveData
import uz.gita.noteapp_by_xr.data.models.NoteData

interface AddNoteViewModel {

    val noteDataLive: LiveData<NoteData>

    fun addToBase(noteData: NoteData)

}