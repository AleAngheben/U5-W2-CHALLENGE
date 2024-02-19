package alessandro.angheben.u5w2d5.controllers;

import alessandro.angheben.u5w2d5.entities.Employee;
import alessandro.angheben.u5w2d5.payloads.EmployeeLoginDTO;
import alessandro.angheben.u5w2d5.payloads.LoginResponseDTO;
import alessandro.angheben.u5w2d5.payloads.NewEmployeeDTO;
import alessandro.angheben.u5w2d5.services.AuthService;
import alessandro.angheben.u5w2d5.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private Autowired autowired;


    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody EmployeeLoginDTO payload)
    {
        return new LoginResponseDTO(authService.authenticateEmployeeGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody NewEmployeeDTO newEmployee) throws IOException {
        return this.employeeService.save(newEmployee);
    }

}
