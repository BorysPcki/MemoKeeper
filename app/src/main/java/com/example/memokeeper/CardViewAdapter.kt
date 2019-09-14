package com.example.memokeeper

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.view.*

class CardViewAdapter(val context: Context, val db: SQLiteDatabase, var notes: ArrayList<Note>): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardView_note = layoutInflater.inflate(R.layout.card_view, parent, false)
        return MyViewHolder(cardView_note)
    }

    override fun getItemCount(): Int {

        val cursor = db.query(TableInfo.TABLE_NAME, null,
            null, null,
            null, null, null)
        val rowNumber = cursor.count

        cursor.close()

        return rowNumber
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //-------------Elements of single note------------------
        val cardViewNote = holder.view.note_cardView
        val title = holder.view.title_cardView
        val message = holder.view.message_cardView
        val context: Context = holder.view.context
        //------------------------------------------------------

        title.text = notes[holder.adapterPosition].title
        message.text = notes[holder.adapterPosition].message


        /*
        //-------------Data loading-----------------------------
        val cursor = db.query(TableInfo.TABLE_NAME, null,
            BaseColumns._ID + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
            null, null, null)

        if(cursor.moveToFirst()) {
            if(!(cursor.getString(1).isNullOrEmpty() &&
                 cursor.getString(2).isNullOrEmpty())) {
                title.text = cursor.getString(1)
                message.text = cursor.getString(2)

            }
        }
        //-----------------------------------------------------
        */

        cardViewNote.setOnClickListener {
            val intentEdit = Intent(context, DetailsActivity::class.java)

            val titleEdit = notes[holder.adapterPosition].title
            val messageEdit = notes[holder.adapterPosition].message
            val idEdit = notes[holder.adapterPosition].id.toString()

            intentEdit.putExtra("title", titleEdit)
            intentEdit.putExtra("message", messageEdit)
            intentEdit.putExtra("ID", idEdit)

            context.startActivity(intentEdit)
        }

        cardViewNote.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                /* Copy function
                val copyInfo = Toast.makeText(context, "Copied", Toast.LENGTH_SHORT
                )
                val cm = context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                val clipdata = ClipData.newPlainText("CopyText",
                    "Title: " + title.text + "\n" + "Message: " + message.text)
                cm.primaryClip = clipdata
                copyInfo.show()
                */
                db.delete(TableInfo.TABLE_NAME,
                    BaseColumns._ID + "=?",
                    arrayOf(notes[holder.adapterPosition].id.toString()))
                notes.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)

                return true

            }

        })
    }
}

class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)