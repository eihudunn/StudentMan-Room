package vn.edu.hust.studentman

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey val studentId: String,
    val studentName: String
)