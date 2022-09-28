package uz.gita.noteapp_by_xr.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.gita.noteapp_by_xr.R
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.presenter.MainViewModel
import uz.gita.noteapp_by_xr.presenter.impl.MainViewModelImpl
import uz.gita.noteapp_by_xr.presenter.impl.MainViewModelImpl2
import uz.gita.noteapp_by_xr.ui.list_adapter.NotesAdapter
import uz.gita.noteapp_by_xr.ui.viewpager_adapter.ScreenAdapter

class MainScreen : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    //private lateinit var viewPager: ViewPager2
    private lateinit var noteList: RecyclerView
    private lateinit var btnDeleteAll :ImageButton
    private val adapter: NotesAdapter by lazy { NotesAdapter() }

    private lateinit var btnAdd:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeOneShotObservers()

        viewModel.showDeleteDialogLiveData.observe(this){

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.apply {
//            viewPager = findViewById(R.id.viewPager_main)
//            viewPager.adapter = ScreenAdapter(requireParentFragment())

            noteList = findViewById(R.id.notes_list)
            noteList.adapter = adapter
            noteList.isNestedScrollingEnabled = true



            btnDeleteAll = findViewById(R.id.btnDeleteAll)
            btnAdd = findViewById(R.id.btn_openAdd)
        }

        btnAdd.setOnClickListener {
            viewModel.openAddNoteScreen()
        }

        btnDeleteAll.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())

            dialog.setMessage("Hammasi O'chirilsinmi?")
                .setNegativeButton("NO"
                ) { p0, p1 -> }
                .setPositiveButton("YES"){ p0, p1 ->
                    viewModel.deleteAllNotes()
                    Toast.makeText(requireContext(), "ALL DELETED", Toast.LENGTH_SHORT).show()
                }
            dialog.show()
        }

        adapter.setOnClickListener {
            viewModel.openEditNoteScreen(it)
        }

        subscribeUiDataObservers()
    }

    private fun subscribeUiDataObservers() {
        viewModel.notesListLivedata.observe(viewLifecycleOwner, notesListObserver)
    }

    private fun subscribeOneShotObservers() {
        viewModel.openAddNoteLiveData.observe(this, openAddNoteObserver)
        viewModel.openEditNoteLiveData.observe(this, openEditNoteObserver)
    }

    private val notesListObserver = Observer<List<NoteData>> {
        adapter.submitList(it)
    }

    private val openAddNoteObserver = Observer<Unit> {

        findNavController().navigate(R.id.action_mainScreen_to_addNoteScreen)
    }

    private val openEditNoteObserver = Observer<NoteData> {
        val bundle = Bundle().apply {
            putSerializable("data", it)
        }

        findNavController().navigate(R.id.action_mainScreen_to_editNoteScreen, bundle)
    }


}