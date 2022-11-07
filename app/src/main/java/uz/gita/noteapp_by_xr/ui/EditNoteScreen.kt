package uz.gita.noteapp_by_xr.ui

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chinalwb.are.AREditText
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import com.chinalwb.are.styles.toolbar.IARE_Toolbar
import com.chinalwb.are.styles.toolitems.*
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.noteapp_by_xr.R
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.databinding.FragmentEditNoteScreenBinding
import uz.gita.noteapp_by_xr.presenter.AddNoteViewModel
import uz.gita.noteapp_by_xr.presenter.UpdateNoteViewModel
import uz.gita.noteapp_by_xr.presenter.impl.AddNoteViewModelImpl
import uz.gita.noteapp_by_xr.presenter.impl.UpdateNoteViewModelImpl
import uz.gita.noteapp_by_xr.ui.dialogs.ColorPickDialog
import uz.gita.noteapp_by_xr.utils.getDrawables
import java.text.SimpleDateFormat
import java.util.*


class EditNoteScreen : Fragment(R.layout.fragment_edit_note_screen) {
    private lateinit var mToolbar: IARE_Toolbar
    private lateinit var mEditText: AREditText
    private var scrollerAtEnd = false

    private lateinit var btnSave: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnDelete: ImageButton
    private lateinit var titleInput: EditText
    private lateinit var colorPicker: ShapeableImageView
    private var colorNumber = R.drawable.color_pick_yellow

    private val viewModel: UpdateNoteViewModel by viewModels<UpdateNoteViewModelImpl>()
    private val viewBinding by viewBinding(FragmentEditNoteScreenBinding::bind)


    private lateinit var noteData: NoteData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteData = requireArguments().getSerializable("data") as NoteData

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf(
            "High",
            "Medium",
            "Default")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (viewBinding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)



        view.apply {

            btnBack = findViewById(R.id.btnBackFromAdd)
            btnSave = findViewById(R.id.btnSave)
            btnDelete = findViewById(R.id.btnDelete)
            titleInput = findViewById(R.id.text_input)

            colorPicker = findViewById(R.id.colorPick)
            colorPicker.setImageResource(noteData.colorNumber.getDrawables())

            mToolbar = findViewById(R.id.areToolbar)
            val bold: IARE_ToolItem = ARE_ToolItem_Bold()
            val italic: IARE_ToolItem = ARE_ToolItem_Italic()
            val underline: IARE_ToolItem = ARE_ToolItem_Underline()
            val strikethrough: IARE_ToolItem = ARE_ToolItem_Strikethrough()
            val quote: IARE_ToolItem = ARE_ToolItem_Quote()
            val listNumber: IARE_ToolItem = ARE_ToolItem_ListNumber()
            val listBullet: IARE_ToolItem = ARE_ToolItem_ListBullet()
            val hr: IARE_ToolItem = ARE_ToolItem_Hr()
            val link: IARE_ToolItem = ARE_ToolItem_Link()
            val subscript: IARE_ToolItem = ARE_ToolItem_Subscript()
            val superscript: IARE_ToolItem = ARE_ToolItem_Superscript()
            val left: IARE_ToolItem = ARE_ToolItem_AlignmentLeft()
            val center: IARE_ToolItem = ARE_ToolItem_AlignmentCenter()
            val right: IARE_ToolItem = ARE_ToolItem_AlignmentRight()
            val at: IARE_ToolItem = ARE_ToolItem_At()
            mToolbar.addToolbarItem(bold)
            mToolbar.addToolbarItem(italic)
            mToolbar.addToolbarItem(underline)
            mToolbar.addToolbarItem(strikethrough)
            mToolbar.addToolbarItem(quote)
            mToolbar.addToolbarItem(listNumber)
            mToolbar.addToolbarItem(listBullet)
            mToolbar.addToolbarItem(hr)
            mToolbar.addToolbarItem(link)
            mToolbar.addToolbarItem(subscript)
            mToolbar.addToolbarItem(superscript)
            mToolbar.addToolbarItem(left)
            mToolbar.addToolbarItem(center)
            mToolbar.addToolbarItem(right)
            mToolbar.addToolbarItem(at)
            mEditText = findViewById(R.id.arEditText)
            mEditText.setToolbar(mToolbar)



            mEditText.fromHtml(noteData.description)
            titleInput.text = Editable.Factory().newEditable(noteData.title)


            val imageView: ImageView = findViewById(R.id.arrow)
            if (mToolbar is ARE_ToolbarDefault) {
                (mToolbar as ARE_ToolbarDefault).viewTreeObserver.addOnScrollChangedListener {
                    val scrollX = (mToolbar as ARE_ToolbarDefault).scrollX
                    val scrollWidth = (mToolbar as ARE_ToolbarDefault).width
                    val fullWidth = (mToolbar as ARE_ToolbarDefault).getChildAt(0).width
                    scrollerAtEnd = if (scrollX + scrollWidth < fullWidth) {
                        imageView.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
                        false
                    } else {
                        imageView.setImageResource(R.drawable.ic_baseline_arrow_back_24)
                        true
                    }
                }
            }
            imageView.setOnClickListener {
                scrollerAtEnd = if (scrollerAtEnd) {
                    (mToolbar as ARE_ToolbarDefault?)!!.smoothScrollBy(-Int.MAX_VALUE, 0)
                    false
                } else {
                    val hsWidth = (mToolbar as ARE_ToolbarDefault?)!!.getChildAt(0).width
                    (mToolbar as ARE_ToolbarDefault?)!!.smoothScrollBy(hsWidth, 0)
                    true
                }
            }

            combine(
                titleInput.textChanges()
                    .map { it.isNotEmpty() },

                mEditText.textChanges()
                    .map { it.isNotEmpty() },

                transform = { title, desc -> title && desc }
            )
                .onEach { btnSave.isEnabled = it; if(it) btnSave.setColorFilter(Color.GREEN) else btnSave.setColorFilter(Color.GRAY) }
                .flowWithLifecycle(lifecycle)
                .launchIn(lifecycleScope) // lifecycle-runtime-ktx

            btnSave.setOnClickListener {

                val title = titleInput.text.toString()
                val desc = mEditText.html
                val date = SimpleDateFormat("yyyy.mm.dd hh:mm", Locale.CANADA).format(Date())

                noteData.title = title
                noteData.description = desc
                noteData.date = date
                noteData.colorNumber = colorNumber

                val priority = viewBinding.menu.editText?.text.toString()
                when(priority){
                    "High" -> {
                        noteData.high = true
                        noteData.medium = false
                        noteData.simple = false
                    }
                    "Medium" -> {
                        noteData.high = false
                        noteData.medium = true
                        noteData.simple = false
                    }
                    "Default" -> {
                        noteData.high = false
                        noteData.medium = false
                        noteData.simple = true
                    }
                }

                viewModel.update(noteData)
                Toast.makeText(requireContext(), "SAVED", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }

            btnDelete.setOnClickListener {

                val dialog = AlertDialog.Builder(requireContext())

                dialog.setMessage("Do you want to delete Note: ${noteData.title} ?")
                    .setNegativeButton("NO"
                    ) { p0, p1 -> }
                    .setPositiveButton("YES"){ p0, p1 ->
                        viewModel.delete(noteData)
                        Toast.makeText(requireContext(), "DELETED", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                dialog.show()


            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            colorPicker.setOnClickListener {
                val dialog = ColorPickDialog(requireContext())

                dialog.setOnColorClickListener {
                    colorNumber = it
                    colorPicker.setImageResource(it.getDrawables())
                }

                dialog.show()
            }
        }

    }
}