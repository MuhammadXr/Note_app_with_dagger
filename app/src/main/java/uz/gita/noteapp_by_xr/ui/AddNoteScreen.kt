package uz.gita.noteapp_by_xr.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chinalwb.are.AREditText
import com.chinalwb.are.AREditor
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import com.chinalwb.are.styles.toolbar.IARE_Toolbar
import com.chinalwb.are.styles.toolitems.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.noteapp_by_xr.R
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.presenter.AddNoteViewModel
import uz.gita.noteapp_by_xr.presenter.impl.AddNoteViewModelImpl
import java.text.SimpleDateFormat
import java.util.*


class AddNoteScreen : Fragment(R.layout.fragment_add_note_screen) {
    private lateinit var mToolbar: IARE_Toolbar
    private lateinit var mEditText: AREditText
    private var scrollerAtEnd = false

    private lateinit var btnSave: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var titleInput: TextInputEditText

    private val viewModel: AddNoteViewModel by viewModels<AddNoteViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {

            btnBack = findViewById(R.id.btnBackFromAdd)
            btnSave = findViewById(R.id.btnSave)
            titleInput = findViewById(R.id.text_input)

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
            val image: IARE_ToolItem = ARE_ToolItem_Image()
            val video: IARE_ToolItem = ARE_ToolItem_Video()
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
            mToolbar.addToolbarItem(image)
            mToolbar.addToolbarItem(video)
            mToolbar.addToolbarItem(at)
            mEditText = findViewById(R.id.arEditText)
            mEditText.setToolbar(mToolbar)


//            val html = """
//            <p style="text-align: center;"><strong>New Feature in 0.1.2</strong></p>
//            <p style="text-align: center;">&nbsp;</p>
//            <p style="text-align: left;"><span style="color: #3366ff;">In this release, you have a new usage with ARE.</span></p>
//            <p style="text-align: left;">&nbsp;</p>
//            <p style="text-align: left;"><span style="color: #3366ff;">AREditText + ARE_Toolbar, you are now able to control the position of the input area and where to put the toolbar at and, what ToolItems you'd like to have in the toolbar. </span></p>
//            <p style="text-align: left;">&nbsp;</p>
//            <p style="text-align: left;"><span style="color: #3366ff;">You can not only define the Toolbar (and it's style), you can also add your own ARE_ToolItem with your style into ARE.</span></p>
//            <p style="text-align: left;">&nbsp;</p>
//            <p style="text-align: left;"><span style="color: #ff00ff;"><em><strong>Why not give it a try now?</strong></em></span></p>
//            """.trimIndent()
//            mEditText.fromHtml(html)

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
                .onEach {
                    btnSave.isEnabled = it
                    if (it) btnSave.setColorFilter(Color.GREEN) else btnSave.setColorFilter(
                        Color.GRAY
                    )
                }
                .flowWithLifecycle(lifecycle)
                .launchIn(lifecycleScope)

            btnSave.setOnClickListener {

                val title = titleInput.text.toString()
                val desc = mEditText.html
                val date = SimpleDateFormat("yyyy.mm.dd hh:mm", Locale.CANADA).format(Date())

                val note = NoteData(
                    0,
                    title,
                    desc,
                    date
                )

                viewModel.addToBase(note)
                Toast.makeText(requireContext(), "ADDED", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }
}