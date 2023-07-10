package com.sri.customcontentprovider

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)


@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person)

    @Query("SELECT * FROM persons")
    fun getAllPersons(): List<Person>
}


@Database(entities = [Person::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
