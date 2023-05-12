package com.emre.mynotebook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emre.mynotebook.databinding.ActivityDetailBinding
import com.emre.mynotebook.databinding.RecyclerRowBinding
import com.emre.mynotebook.model.Notes
import com.emre.mynotebook.view.DetailActivity

class NoteAdapter(val noteList: List<Notes>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    class NoteHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.binding.recyclerTextView.text = noteList.get(position).title

        holder.itemView.setOnClickListener {
            val intentToDetail = Intent(holder.itemView.context,DetailActivity::class.java)
            intentToDetail.putExtra("info", "old")
            intentToDetail.putExtra("note", noteList.get(position))
            intentToDetail.putExtra("id",noteList.get(position).id)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }
}