package uz.gita.noteapp_by_xr.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.noteapp_by_xr.data.models.NoteData

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val date: String
) {
    fun toNoteData() = NoteData(id, title, description, date)
}