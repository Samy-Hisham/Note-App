package com.android.noteapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.noteapp.R
import com.android.noteapp.data.models.ModelNote
import com.android.noteapp.databinding.NoteItemBinding
import javax.inject.Inject
import kotlin.random.Random

class NoteAdapter @Inject constructor() :
    RecyclerView.Adapter<NoteAdapter.Holder>() {

    val list = ArrayList<ModelNote>()
    val fullList = ArrayList<ModelNote>()


    var onClick: OnClick? = null
    private var context: Context? = null
    var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list[position]

        holder.binding.apply {
            model.apply {

                edNote.text = noteDescription
                edDate.text = timeStamp

            }

//            noteLayout.setCardBackgroundColor(holder.binding.root.resources.getColor(randomColor(),
//                null))

            noteLayout.setOnClickListener() {
                //change
                onClick?.onClick(list[position])
            }

            noteLayout.setOnTouchListener { p0, p1 ->
                when (p1?.action) {

                    MotionEvent.ACTION_DOWN ->
                        noteLayout.setCardBackgroundColor(holder.binding.root.resources.getColor(
                            R.color.snow1,
                            null))

                    MotionEvent.ACTION_CANCEL ->
                        noteLayout.setCardBackgroundColor(holder.binding.root.resources.getColor(
                            R.color.white,
                            null))

                    MotionEvent.ACTION_UP ->
                        noteLayout.setCardBackgroundColor(holder.binding.root.resources.getColor(
                            R.color.white,
                            null))
                }
                p0?.onTouchEvent(p1) ?: true
            }

            noteLayout.setOnLongClickListener() {
                onClick?.onLongClick(list[position], noteLayout)
                true
            }
        }
    }

    //if (list == null) 0 else list!!.size
    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ModelNote>) {

        fullList.clear()
        fullList.addAll(newList)

        list.clear()
        list.addAll(fullList)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(search: String) {

        list.clear()

        for (item in fullList) {
            if (item.noteDescription.lowercase()
                    .contains(search.lowercase())
            ) {
                list.add(item)
            }
        }
        notifyDataSetChanged()
    }

//    private fun randomColor(): Int {
//        val list = ArrayList<Int>()
//
//        list.add(R.color.white_Spectrum)
//        list.add(R.color.red_Spectrum)
//        list.add(R.color.betrolly_Spectrum)
//        list.add(R.color.fayruz_Spectrum)
//        list.add(R.color.move_Spectrum)
//        list.add(R.color.yellow_Spectrum)
//
//        val seed = System.currentTimeMillis().toInt()
//        val randomIndex = Random(seed).nextInt(list.size)
//        return list[randomIndex]
//
//    }

    @SuppressLint("NotifyDataSetChanged")
    inner class Holder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            context = binding.root.context

            itemView.setOnClickListener() {

                selectedItem = layoutPosition

                onClick?.onClick(list[layoutPosition])

                notifyDataSetChanged()
            }
        }
    }

    interface OnClick {
        fun onClick(note: ModelNote)
        fun onLongClick(note: ModelNote, cardView: CardView)
    }
}