package uz.gita.noteapp_by_xr.data.models

import uz.gita.noteapp_by_xr.data.source.local.NoteEntity
import java.io.Serializable

data class NoteData(
    val id: Long,
    var title: String,
    var description: String,
    var date: String
) : Serializable {
    fun toNoteEntity() = NoteEntity(id, title, description, date)
}