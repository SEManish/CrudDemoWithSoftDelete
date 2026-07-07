package in.coderarmy.crudSpringBootDemo.Controller;

import in.coderarmy.crudSpringBootDemo.Entity.Student;
import in.coderarmy.crudSpringBootDemo.Services.StudentService;
import org.apache.coyote.Response;
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
 public ResponseEntity<Student> createStudent(@RequestBody Student student){
Student createStudent=studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createStudent);
    }

    @GetMapping("/get")
    public ResponseEntity<Student> getStudent(@RequestParam Long id){
       Student studentResp= studentService.getStudent(id);

       if(studentResp==null){
           return ResponseEntity.notFound().build();
       }

       return ResponseEntity.ok(studentResp);
    }
@GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudents(){
List<Student> studentResp=studentService.getAllStudents();
    return ResponseEntity.ok(studentResp);
}

@PutMapping("update/{id}")
public ResponseEntity<Student> updateStudent(@PathVariable Long id,@RequestBody Student student){
     Student studentResp=studentService.updateStudent(id,student);
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
//@PatchMapping("s/{id}")
//public ResponseEntity<String> softDelete(@PathVariable Long id){
//    Boolean isDeleted = studentService.deleteStudentSoftly(id);
//    if(!isDeleted){
//        return ResponseEntity.notFound().build();
//    }
//    return ResponseEntity.ok("Record deleted");
//}
}
