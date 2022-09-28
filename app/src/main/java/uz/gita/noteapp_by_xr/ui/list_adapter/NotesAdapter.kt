package uz.gita.noteapp_by_xr.ui.list_adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chinalwb.are.AREditor
import com.chinalwb.are.render.AreTextView
import com.google.android.material.card.MaterialCardView
import uz.gita.noteapp_by_xr.R
import uz.gita.noteapp_by_xr.data.models.NoteData
import uz.gita.noteapp_by_xr.utils.getDrawables


class NotesAdapter: ListAdapter<NoteData, NotesAdapter.Holder>(CallBack) {

    private var onClickListener: ((NoteData) -> Unit)? = null

    private var onLongClickListener: ((NoteData) -> Unit)? = null

    fun setOnClickListener(block: ((NoteData) -> Unit)){
        onClickListener = block
    }
    fun setOnLongClickListener(block: ((NoteData) -> Unit)){
        onLongClickListener = block
    }


    inner class Holder(view: View) : RecyclerView.ViewHolder(view){

        val title: TextView = view.findViewById(R.id.note_title)
        val desc: AreTextView = view.findViewById(R.id.note_text)
        val date: TextView = view.findViewById(R.id.note_date)
        val card : MaterialCardView = view.findViewById(R.id.materialCardView)
        val topBar: ImageView = view.findViewById(R.id.item_color)
        val bottomBar: ImageView = view.findViewById(R.id.item_bottom)

        fun bind(position: Int) {
            title.text = getItem(position).title
            val html = getItem(position).description
            desc.fromHtml(html)
            date.text = getItem(position).date

            topBar.setImageResource(getItem(position).colorNumber.getDrawables())
            bottomBar.setImageResource(getItem(position).colorNumber.getDrawables())

            Log.d("TTT", "${getItem(position).colorNumber.getDrawables()}  ${R.drawable.color_pick_pink}")

            card.setOnClickListener{
                onClickListener?.invoke(getItem(position))
            }

            desc.setOnClickListener{
                onClickListener?.invoke(getItem(position))
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    object CallBack: DiffUtil.ItemCallback<NoteData>() {
        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id && oldItem.date == newItem.date && oldItem.description == newItem.description && oldItem.colorNumber == newItem.colorNumber
        }

    }
}