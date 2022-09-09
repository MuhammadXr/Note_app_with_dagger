package uz.gita.noteapp_by_xr.presenter

import uz.gita.noteapp_by_xr.data.models.NoteData

interface UpdateNoteViewModel {

    fun update(noteData: NoteData)

    fun delete(noteData: NoteData)

}