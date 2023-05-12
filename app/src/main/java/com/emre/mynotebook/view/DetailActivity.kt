package com.emre.mynotebook.view

import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.room.Room
import com.emre.mynotebook.R
import com.emre.mynotebook.databinding.ActivityDetailBinding
import com.emre.mynotebook.model.Notes
import com.emre.mynotebook.roomdb.NoteDao
import com.emre.mynotebook.roomdb.NoteDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val compositeDisposable = CompositeDisposable()
    private lateinit var db: NoteDatabase
    private lateinit var noteDao: NoteDao
    var info: String? = null
    var id: Int? = null
    var control: Boolean = false
    lateinit var noteFromMain: Notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "Notes").build()
        noteDao = db.noteDao()

        val intent = intent
        info = intent.getStringExtra("info")

        if (info.equals("new")) {
            binding.mainText.setText("")
            binding.titleText.setText("")
            binding.imageView.visibility = View.GONE
        } else {

            binding.imageView.visibility = View.VISIBLE
            noteFromMain = intent.getSerializableExtra("note") as Notes

            binding.mainText.setText(noteFromMain.mainText)
            binding.titleText.setText(noteFromMain.title)
            id = noteFromMain.id
        }

    }

    override fun onPause() {
        if (info!!.equals("new") && control) {
            save()
            println("save")
        } else if (info!!.equals("old")) {
            println("update")
            update()
        }
        super.onPause()
    }

    lateinit var title: String
    lateinit var mainText: String
    lateinit var note: Notes

    private fun save() {
        title = binding.titleText.text.toString()
        mainText = binding.mainText.text.toString()

        if (!title.equals("") && !mainText.equals("")) {
            control = true
        }
        note = Notes(title, mainText)
        compositeDisposable.add(
            noteDao.insert(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

    private fun update() {
        title = binding.titleText.text.toString()
        mainText = binding.mainText.text.toString()

        note = Notes(title, mainText)
        note.id = id!!
        compositeDisposable.add(
            noteDao.update(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse() {
        val intent = Intent(this@DetailActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }


    fun deleteNote(view: View) {

        compositeDisposable.add(
            noteDao.delete(noteFromMain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )

    }


}