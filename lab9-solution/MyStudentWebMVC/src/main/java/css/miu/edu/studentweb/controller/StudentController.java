package css.miu.edu.studentweb.controller;

import css.miu.edu.studentweb.model.Classroom;
import css.miu.edu.studentweb.model.Student;
import css.miu.edu.studentweb.model.Transcript;
import css.miu.edu.studentweb.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller

public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping(value = "/secured/student/list")
    public ModelAndView displayStudent(){
        var modelAndView = new ModelAndView();
        var student =studentService.getAllStudent();
        modelAndView.addObject("students",student);
        modelAndView.setViewName("secured/student/list");
        return modelAndView;
    }
    @GetMapping(value = {"/new"})
    public String displayNewStudentForm(Model model) {
        model.addAttribute("student", new Student(null,null,null,null,null,null, null,null,new Transcript(),new Classroom()));
        return "secured/student/new";
    }

    @PostMapping(value = {"/new"})
    public String registerNewStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "secured/student/new";
        }
//        if(publisher.getPrimaryAddress() != null) {
//            var primaryAddr = publisher.getPrimaryAddress();
//            if(primaryAddr.getStreet().equals("")
//                    && primaryAddr.getCity().equals("")
//                    && primaryAddr.getState().equals("")
//                    && primaryAddr.getZipCode().equals("")
//            ) {
//                publisher.setPrimaryAddress(null);
//            }
//        }
       // publisherService.savePublisher(publisher);
        studentService.addNewStudent(student);
        studentService.addNewStudent(student);
        return "redirect:/secured/student/list";
    }
    @GetMapping(value = {"/edit/{studentId}"})
    public String editStudent(@PathVariable Integer studentId, Model model) {

        var student=studentService.getStudentById(studentId);
        if(student != null) {
            model.addAttribute("student", student);
            return "secured/student/edit";
        }
        return "redirect:/secured/student/list";
    }

    @PostMapping(value = {"/update"})
    public String updateStudent(@Valid @ModelAttribute("student") Student student,
                                  BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "secured/student/edit";
        }

        studentService.updateStudent(student);
        return "redirect:/secured/student/list";
    }

    @GetMapping(value = {"/delete/{studentId}"})
    public String deleteStudent(@PathVariable Integer studentId) {
studentService.deleteStudentById(studentId);

        return "redirect:/secured/student/list";
    }

}
