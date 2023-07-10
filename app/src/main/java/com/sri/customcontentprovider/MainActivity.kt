package com.sri.customcontentprovider

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private var handler:Handler=Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        handler.postDelayed({
            fetchDataFromContentProvider()
        },2000)
    }

    private fun fetchDataFromContentProvider() {
        val contentResolver = contentResolver
        val projection = arrayOf("id", "name")

        val cursor = contentResolver.query(
           MyContentProvider.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.let {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex("id")
                val nameIndex = it.getColumnIndex("name")
                val stringBuilder = StringBuilder()

                do {
                    val id = it.getInt(idIndex)
                    val name = it.getString(nameIndex)

                    stringBuilder.append("ID: $id, Name: $name\n")
                } while (it.moveToNext())

                textView.text = stringBuilder.toString()
            }

            it.close()
        }
    }
}
