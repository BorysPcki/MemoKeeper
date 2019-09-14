package com.example.memokeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClickTakeNotes(v: View) {
        val intent = Intent(applicationContext, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val cursor = db.query(TableInfo.TABLE_NAME, null,
            null, null,
            null, null, null)

        val notes = ArrayList<Note>()

        if(cursor.count >0){
            cursor.moveToFirst()
            while(!cursor.isAfterLast) {
                val note = Note()
                note.id = cursor.getInt(0)
                note.title = cursor.getString(1)
                note.message = cursor.getString(2)
                notes.add(note)
                cursor.moveToNext()
            }
        }
        cursor.close()

        recyler_view.layoutManager = GridLayoutManager(applicationContext, 2)
        recyler_view.adapter = CardViewAdapter(applicationContext, db, notes)
    }
}
