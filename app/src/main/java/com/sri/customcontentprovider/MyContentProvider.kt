package com.sri.customcontentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class MyContentProvider : ContentProvider() {
    companion object {

        private const val AUTHORITY = "com.sri.customcontentprovider.MyContentProvider"
        private const val TABLE_NAME = "persons"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    private lateinit var database: MyDatabase

    override fun onCreate(): Boolean {
        context?.let {
            database = Room.databaseBuilder(it, MyDatabase::class.java, "my-database").allowMainThreadQueries().addCallback(object:RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {

                    println("database created")
                    super.onCreate(db)
                     db.execSQL("INSERT INTO persons (id, name) VALUES (1, 'John')")
                     db.execSQL("INSERT INTO persons (id, name) VALUES (2, 'Alice')")
                }
            }).build()
            println("below line")

        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val persons = database.personDao().getAllPersons()
        val cursor = MatrixCursor(projection)
        persons.forEach { person ->
            val rowBuilder = cursor.newRow()
            rowBuilder.add(person.id)
            rowBuilder.add(person.name)
        }
        return cursor
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }


}
