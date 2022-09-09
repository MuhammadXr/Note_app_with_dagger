package uz.gita.noteapp_by_xr.ui.list_adapter


import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chinalwb.are.AREditor
import com.chinalwb.are.render.AreTextView
import com.google.android.material.card.MaterialCardView
import uz.gita.noteapp_by_xr.R
import uz.gita.noteapp_by_xr.data.models.NoteData


class NotesAdapter: ListAdapter<NoteData, NotesAdapter.Holder>(CallBack) {

    private var onClickListener: ((NoteData) -> Unit)? = null

    fun setOnClickListener(block: ((NoteData) -> Unit)){
        onClickListener = block
    }


    inner class Holder(view: View) : RecyclerView.ViewHolder(view){

        val title: TextView = view.findViewById(R.id.note_title)
        val desc: AreTextView = view.findViewById(R.id.note_text)
        val date: TextView = view.findViewById(R.id.note_date)
        val card : MaterialCardView = view.findViewById(R.id.materialCardView)
        val topBar: View = view.findViewById(R.id.item_color)
        val bottomBar: View = view.findViewById(R.id.item_bottom)

        fun bind(position: Int) {
            title.text = getItem(position).title
            val html = getItem(position).description
            desc.fromHtml(html)
            date.text = getItem(position).date

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
            return oldItem.id == newItem.id && oldItem.date == newItem.date && oldItem.description == newItem.description
        }

    }
}