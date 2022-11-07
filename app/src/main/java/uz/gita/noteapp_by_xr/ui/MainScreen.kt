package uz.gita.noteapp_by_xr.ui

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.gita.noteapp_by_xr.MainActivity
import uz.gita.noteapp_by_xr.R
import uz.gita.noteapp_by_xr.data.models.FilterData
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.databinding.FragmentMainBinding
import uz.gita.noteapp_by_xr.presenter.MainViewModel
import uz.gita.noteapp_by_xr.presenter.impl.MainViewModelImpl
import uz.gita.noteapp_by_xr.ui.list_adapter.NotesAdapter
import uz.gita.noteapp_by_xr.utils.Utils


class MainScreen : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    //private lateinit var viewPager: ViewPager2
    private lateinit var noteList: RecyclerView
    private lateinit var btnDeleteAll: ImageButton
    private val adapter: NotesAdapter by lazy { NotesAdapter() }

    private lateinit var btnAdd: FloatingActionButton

    private val tags = mutableListOf(true, true, true)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeOneShotObservers()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewBinding.priorityHigh.setOnClickListener {
            tags[0] = !tags[0]
            viewBinding.priorityHigh.setCardBackgroundColor(
                if (tags[0])
                    Color.parseColor("#FFB7B7")
                else
                    Color.parseColor("#FFffff")
            )
            viewModel.filterData(
                FilterData(
                    tags[0],
                    tags[1],
                    tags[2],
                )
            )
        }

        viewBinding.priorityMedium.setOnClickListener {
            tags[1] = !tags[1]
            viewBinding.priorityMedium.setCardBackgroundColor(
                if (tags[1])
                    Color.parseColor("#FFB7B7")
                else
                    Color.parseColor("#FFffff")
            )
            viewModel.filterData(
                FilterData(
                    tags[0],
                    tags[1],
                    tags[2],
                )
            )
        }

        viewBinding.prioritySimple.setOnClickListener {
            tags[2] = !tags[2]
            viewBinding.prioritySimple.setCardBackgroundColor(
                if (tags[2])
                    Color.parseColor("#FFB7B7")
                else
                    Color.parseColor("#FFffff")
            )
            viewModel.filterData(
                FilterData(
                    tags[0],
                    tags[1],
                    tags[2],
                )
            )
        }

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

            val popupMenu = PopupMenu(
                requireContext(), viewBinding.btnDeleteAll
            )

            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.deleteAll -> {

                        if (viewModel.notesListLivedata.value?.size != 0) {
                            val dialog = AlertDialog.Builder(requireContext())
                            dialog.setMessage("Do you want to delete all?")
                                .setNegativeButton(
                                    "NO"
                                ) { p0, p1 -> }
                                .setPositiveButton("YES") { p0, p1 ->

                                    viewModel.deleteAllNotes()
                                    Toast.makeText(
                                        requireContext(),
                                        "ALL DELETED",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                }
                            dialog.show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "THERE NOTHING FOR DELETE",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    R.id.share -> {
                        Utils.share(requireContext())
                    }
                    R.id.rate -> {
                        Utils.goToPlayMarket(requireContext() as MainActivity)
                    }
                    R.id.about -> {
                        val dialog = AlertDialog.Builder(requireContext())
                        dialog.setMessage("This app is made by RuziMukhammad\nThis program is made as homework\nGITA academy of programmers 2022")
                            .setTitle("Note App")
                            .setPositiveButton("Ok!") { p0, p1 ->

                            }
                        dialog.show()
                    }
                }
                true
            }
            // Showing the popup menu
            // Showing the popup menu
            popupMenu.show()

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
        if (it != null && it.isNotEmpty()) {
            adapter.submitList(it)
            viewBinding.placeHolder.visibility = TextView.GONE
        } else {
            adapter.submitList(emptyList())
            viewBinding.placeHolder.visibility = TextView.VISIBLE
        }
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