package in.coderarmy.crudSpringBootDemo.Controller;
import in.coderarmy.crudSpringBootDemo.DTO.UpdateStudentRequestDto;
import in.coderarmy.crudSpringBootDemo.DTO.UpdateStudentResponseDto;
import in.coderarmy.crudSpringBootDemo.DTO.CreateStudentRequestDto;
import in.coderarmy.crudSpringBootDemo.DTO.CreateStudentResponseDto;
import in.coderarmy.crudSpringBootDemo.Entity.Student;
import in.coderarmy.crudSpringBootDemo.Services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }



    @PostMapping("/create")
 public ResponseEntity<CreateStudentResponseDto> createStudent(@Valid @RequestBody CreateStudentRequestDto studentRequestDto){
        CreateStudentResponseDto createStudent=studentService.createStudent(studentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createStudent);
    }

    @GetMapping("/get")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@RequestParam Long id){
        CreateStudentResponseDto studentResps = studentService.getStudent(id);
        if(studentResps==null){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(studentResps);
    }


@GetMapping("/getAll")
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudents(){
List<CreateStudentResponseDto> studentResp=studentService.getAllStudents();
    return ResponseEntity.ok(studentResp);
}

@PutMapping("update/{id}")
public ResponseEntity<UpdateStudentResponseDto> updateStudent(@PathVariable Long id, @RequestBody UpdateStudentRequestDto updateStudentRequestDto){
    UpdateStudentResponseDto studentResp=studentService.updateStudent(id,updateStudentRequestDto);
     if(studentResp==null){
         return ResponseEntity.notFound().build();
     }
     return ResponseEntity.ok(studentResp);
}


@DeleteMapping("delete/{id}")
public ResponseEntity<String> deleteStudent(@PathVariable Long id){

      Boolean studentResp=  studentService.deleteStudent(id);

    if(!studentResp){
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok("Deleted Successfully");
}
@PatchMapping("/soft-delete")
public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id){
Boolean isdeleted=studentService.deleteStudentSoftly(id);

    if(!isdeleted){
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok("Deleted Successfully");
}

}
