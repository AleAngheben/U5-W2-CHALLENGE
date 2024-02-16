package alessandro.angheben.u5w2d5.controllers;

import alessandro.angheben.u5w2d5.entities.Employee;
import alessandro.angheben.u5w2d5.exceptions.BadRequestException;
import alessandro.angheben.u5w2d5.payloads.NewEmployeRespDTO;
import alessandro.angheben.u5w2d5.payloads.NewEmployeeDTO;
import alessandro.angheben.u5w2d5.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

//    @GetMapping
//    public Page<Employee> getEmployees( @RequestParam(required = false) String firstName,
//                                        @RequestParam(required = false) String lastName,
//                                        @RequestParam(defaultValue = "0") int page,
//                                        @RequestParam(defaultValue = "10") int size,
//                                        @RequestParam(defaultValue = "id") String id){
//        return employeeService.getEmployees(firstName,lastName,page,size,id);
//    }

    @GetMapping
    public Page<Employee> getAllEmployess(@RequestParam(defaultValue = "0")int page,
                                          @RequestParam(defaultValue = "10")int size,
                                          @RequestParam(defaultValue = "id") String orderBy){
        return this.employeeService.getEmployees(page,size,orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody @Validated NewEmployeeDTO employee, BindingResult validation) throws Exception{
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }

        return this.employeeService.save(employee);
    }

    @GetMapping("/{id}")
   public Employee findById(@PathVariable UUID id){return this.employeeService.findById(id);}

    @PutMapping("/{id}")
   public Employee findAndUpdateById(@PathVariable UUID id, @RequestBody NewEmployeeDTO updated){
        return this.employeeService.findByIdAndUpdate(id,updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id){this.employeeService.deleteById(id);}


    //immagine
    @PatchMapping("/{id}/profile_picture")
    public Employee uploadProfilePic(@RequestParam("profile_picture") MultipartFile file, @PathVariable UUID id){
        try{
            return employeeService.uploadProfilePicture(id, file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
