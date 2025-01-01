package vn.edu.hust.studentman

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM students WHERE studentId = :studentId")
    fun getStudentById(studentId: String): Student?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student): Int

    @Delete
    suspend fun deleteStudent(student: Student): Int
}