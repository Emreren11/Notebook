package com.emre.mynotebook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.emre.mynotebook.R
import com.emre.mynotebook.adapter.NoteAdapter
import com.emre.mynotebook.databinding.ActivityMainBinding
import com.emre.mynotebook.model.Notes
import com.emre.mynotebook.roomdb.NoteDao
import com.emre.mynotebook.roomdb.NoteDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var compositeDisposable = CompositeDisposable()
    private lateinit var db: NoteDatabase
    private lateinit var noteDao: NoteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        println("create")
        // Database Tanımlamaları
        db = Room.databaseBuilder(applicationContext,NoteDatabase::class.java,"Notes").build()
        noteDao = db.noteDao()

        // Verileri Çağırma
        compositeDisposable.add(
            noteDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(noteList: List<Notes>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = NoteAdapter(noteList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addNote) {
            val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
            intentToDetail.putExtra("info", "new")
            startActivity(intentToDetail)
        }
        return super.onOptionsItemSelected(item)
    }
}