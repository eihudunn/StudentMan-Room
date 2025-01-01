package vn.edu.hust.studentman

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val studentDao = StudentDatabase.getDatabase(application).studentDao()
    val students: LiveData<List<Student>> = studentDao.getAllStudents()

    suspend fun getStudentById(studentId: String): Student? {
        return withContext(Dispatchers.IO) {
            studentDao.getStudentById(studentId)
        }
    }

    fun addStudent(student: Student): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            studentDao.addStudent(student)
        }
        return true
    }

    fun updateStudent(student: Student) = viewModelScope.async(Dispatchers.IO) {
        studentDao.updateStudent(student) > 0
    }

    fun deleteStudent(student: Student): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            studentDao.deleteStudent(student)
        }
        return true
    }
}