package uz.gita.noteapp_by_xr.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.data.source.local.AppDatabase
import uz.gita.noteapp_by_xr.repository.NoteRepository

class NoteRepositoryImpl : NoteRepository {

    private val noteDao = AppDatabase.INSTANCE.getNoteDao()

    override suspend fun addNote(noteData: NoteData) {
        noteDao.addNote(noteData.toNoteEntity())
    }

    override suspend fun updateNote(noteData: NoteData) {
        noteDao.updateNote(noteData.toNoteEntity())
    }

    override suspend fun deleteNote(noteData: NoteData) {
        noteDao.deleteNote(noteData.toNoteEntity())
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    override fun getAllNotes(): Flow<List<NoteData>> =
        noteDao.getNotes().map { flowElement ->
            flowElement.map { listElement ->
                listElement.toNoteData()
            }
        }.flowOn(Dispatchers.IO)

    override fun getByTag(filterData: FilterData): Flow<List<NoteData>> {
        return noteDao.getByTag(filterData.simple,filterData.medium,filterData.high).map { flowItem ->
            flowItem.map { listItem ->
                listItem.toNoteData()
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private lateinit var instance: NoteRepository

        fun getInstance(): NoteRepository {
            if (!Companion::instance.isInitialized) {
                instance = NoteRepositoryImpl()
            }
            return instance
        }
    }

}