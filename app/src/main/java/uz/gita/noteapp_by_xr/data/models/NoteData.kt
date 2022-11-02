package uz.gita.noteapp_by_xr.data.models

import uz.gita.noteapp_by_xr.data.source.local.NoteEntity
import java.io.Serializable

data class NoteData(
    val id: Long,
    var title: String,
    var description: String,
    var date: String,
    var colorNumber: Int,
    var high: Boolean = false,
    var medium: Boolean = false,
    var simple:Boolean = false
) : Serializable {
    fun toNoteEntity() = NoteEntity(id, title, description, date, colorNumber, high, medium, simple)
}