import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.hust.studentman.Student
import vn.edu.hust.studentman.StudentDatabase

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val studentDao = StudentDatabase.getDatabase(application).studentDao()
    val students: LiveData<List<Student>> = studentDao.getAllStudents()

    fun getStudentById(studentId: String): Student? {
        return studentDao.getStudentById(studentId)
    }

    fun addStudent(student: Student): Boolean {
        var success = false
        viewModelScope.launch {
            success = studentDao.addStudent(student) > 0
        }
        return success
    }

    fun updateStudent(student: Student): Boolean {
        var success = false
        viewModelScope.launch {
            success = studentDao.updateStudent(student) > 0
        }
        return success
    }

    fun deleteStudent(student: Student): Boolean {
        var success = false
        viewModelScope.launch {
            success = studentDao.deleteStudent(student) > 0
        }
        return success
    }
}