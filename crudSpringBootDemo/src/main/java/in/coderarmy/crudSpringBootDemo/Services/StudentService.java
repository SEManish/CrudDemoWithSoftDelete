package in.coderarmy.crudSpringBootDemo.Services;

import in.coderarmy.crudSpringBootDemo.DTO.UpdateStudentRequestDto;
import in.coderarmy.crudSpringBootDemo.DTO.UpdateStudentResponseDto;
import in.coderarmy.crudSpringBootDemo.DTO.CreateStudentRequestDto;
import in.coderarmy.crudSpringBootDemo.DTO.CreateStudentResponseDto;
import in.coderarmy.crudSpringBootDemo.Entity.Student;
import in.coderarmy.crudSpringBootDemo.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentRequestDto) {
       Student student=mapToEntity(studentRequestDto);
        Student studentResp = studentRepository.save(student);
        return mapToDto(studentResp);
    }


    public CreateStudentResponseDto getStudent(Long id) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResp.isPresent()) {
            return mapToDto(studentResp.get());
        }
        return null;
    }

    public List<CreateStudentResponseDto> getAllStudents() {
        List<Student> studentsList = studentRepository.findByDeletedIsFalse();
        return  studentsList.stream().map(this::mapToDto).toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto updateStudentRequestDto) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResp.isEmpty()) {
            return null;
        }
        Student studentToSave = studentResp.get();
        studentToSave.setName(updateStudentRequestDto.getName());
        studentToSave.setSubject(updateStudentRequestDto.getSubject());
        studentToSave.setRollNo(updateStudentRequestDto.getRollNo());
        studentToSave.setAge(updateStudentRequestDto.getAge());
        studentToSave.setDeleted(false);
        studentToSave.setUpdatedDate(LocalDateTime.now());
        Student studentRes = studentRepository.save(studentToSave);
        return mapToUpdateDto(studentRes);
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

private Student mapToEntity(CreateStudentRequestDto studentRequestDto){
       Student student =new Student();

       student.setName(studentRequestDto.getName());
    student.setAge(studentRequestDto.getAge());
    student.setEmail(studentRequestDto.getEmail());
    student.setRollNo(studentRequestDto.getRollNo());
    student.setSubject(studentRequestDto.getSubject());
    student.setDeleted(false);
    student.setCreatedDate(LocalDateTime.now());
    student.setUpdatedDate(LocalDateTime.now());
    return student;
}

private CreateStudentResponseDto mapToDto(Student student){
    CreateStudentResponseDto resposeDto=new CreateStudentResponseDto();
    resposeDto.setId(student.getId());
    resposeDto.setName(student.getName());
    resposeDto.setAge(student.getAge());
    resposeDto.setEmail(student.getEmail());
    resposeDto.setRollNo(student.getRollNo());
    resposeDto.setSubject(student.getSubject());
    resposeDto.setMessage("Student Saved Successfully");
    resposeDto.setCreatedDate(student.getCreatedDate());
    resposeDto.setUpdatedDate(student.getUpdatedDate());
    return resposeDto;
}

private UpdateStudentResponseDto mapToUpdateDto(Student student){
    UpdateStudentResponseDto resposeDto=new UpdateStudentResponseDto();
    resposeDto.setId(student.getId());
    resposeDto.setName(student.getName());
    resposeDto.setAge(student.getAge());
    resposeDto.setEmail(student.getEmail());
    resposeDto.setRollNo(student.getRollNo());
    resposeDto.setSubject(student.getSubject());
    resposeDto.setMessage("Student Updated Successfully");
    resposeDto.setUpdatedDate(student.getUpdatedDate());
    return resposeDto;
}
}


