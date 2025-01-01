package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch

class EditFragment : Fragment() {
    private val args: EditFragmentArgs by navArgs()
    private val studentViewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studentNameEditText = view.findViewById<EditText>(R.id.et_student_name)
        val studentIdEditText = view.findViewById<EditText>(R.id.et_student_id)
        val btnSaveChanges = view.findViewById<Button>(R.id.btn_save_changes)
        val studentId = args.studentId

        viewLifecycleOwner.lifecycleScope.launch {
            val student = studentViewModel.getStudentById(studentId)
            student?.let {
                studentNameEditText.setText(it.studentName)
                studentIdEditText.setText(it.studentId)
            }
        }

        btnSaveChanges.setOnClickListener {
            val name = studentNameEditText.text.toString()
            val id = studentIdEditText.text.toString()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val success = studentViewModel.updateStudent(Student(id, name)).await()
                    if (success) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
                    } else {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}