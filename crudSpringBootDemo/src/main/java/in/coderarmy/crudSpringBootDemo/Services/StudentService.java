package in.coderarmy.crudSpringBootDemo.Services;

import in.coderarmy.crudSpringBootDemo.Entity.Student;
import in.coderarmy.crudSpringBootDemo.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        student.setDeleted(false);
        Student studentResp = studentRepository.save(student);
        return studentResp;
    }

    public Student getStudent(Long id) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResp.isPresent()) {
            return studentResp.get();
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> studentsResp = studentRepository.findByDeletedIsFalse();
        return studentsResp;
    }

    public Student updateStudent(Long id, Student student) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResp.isEmpty()) {
            return null;
        }
        Student studentToSave = studentResp.get();
        studentToSave.setEmail(student.getEmail());
        studentToSave.setName(student.getName());
        studentToSave.setSubject(student.getSubject());
        studentToSave.setRollNo(student.getRollNo());
        studentToSave.setAge(student.getAge());
        studentToSave.setDeleted(false);
        return studentRepository.save(studentToSave);
    }
    public Boolean deleteStudent(Long id){
        boolean isStudent = studentRepository.existsById(id);
        if(!isStudent){
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

public Boolean deleteStudentSoftly(Long id){
    Optional<Student> existingStudent  = studentRepository.findByIdAndDeletedIsFalse(id);
    if(existingStudent .isEmpty()) {
        return false;
    }
    Student studentToSave = existingStudent.get();
    studentToSave.setDeleted(true);
    studentRepository.save(studentToSave);
    return true;
}
}
