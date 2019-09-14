package com.example.memokeeper

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val saveInfoToast = Toast.makeText(applicationContext, "Note saved, you could exit", Toast.LENGTH_SHORT)

        if(intent.hasExtra("title"))
            title_details.setText(intent.getStringExtra("title"))
        if(intent.hasExtra("message"))
            message_details.setText(intent.getStringExtra("message"))

        /*

        //----------Note Saving------------
        save_BT_details.setOnClickListener{

            val title = title_details.text.toString()
            val message = message_details.text.toString()

            val value = ContentValues()

            value.put(TableInfo.TABLE_COLUMN_TITLE, title)
            value.put(TableInfo.TABLE_COLUMN_MESSAGE, message)


            if(intent.hasExtra("ID")) {

                db.update(TableInfo.TABLE_NAME, value, BaseColumns._ID + "=?",
                    arrayOf(intent.getStringExtra("ID")))
            }
            else {
                if(!title.isNullOrEmpty() || !message.isNullOrEmpty()) {

                    db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                    saveInfoToast.show()
                }
                else {
                    Toast.makeText(applicationContext, "Nothing to save", Toast.LENGTH_SHORT)
                        .show()
                }

            }



        }
        //--------------------------------

        */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.save_button) {
            val dbHelper = DataBaseHelper(applicationContext)
            val db = dbHelper.writableDatabase

            val title = title_details.text.toString()
            val message = message_details.text.toString()

            val value = ContentValues()

            value.put(TableInfo.TABLE_COLUMN_TITLE, title)
            value.put(TableInfo.TABLE_COLUMN_MESSAGE, message)


            if(intent.hasExtra("ID")) {

                db.update(TableInfo.TABLE_NAME, value, BaseColumns._ID + "=?",
                    arrayOf(intent.getStringExtra("ID")))
            }
            else {
                if(!title.isNullOrEmpty() || !message.isNullOrEmpty()) {

                    db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                }
                else {
                    Toast.makeText(applicationContext, "Nothing to save", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            db.close()
            onBackPressed()

        }

        return super.onOptionsItemSelected(item)
    }
}
