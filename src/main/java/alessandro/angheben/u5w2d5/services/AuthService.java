package alessandro.angheben.u5w2d5.services;

import alessandro.angheben.u5w2d5.entities.Employee;
import alessandro.angheben.u5w2d5.exceptions.UnauthorizedException;
import alessandro.angheben.u5w2d5.payloads.EmployeeLoginDTO;
import alessandro.angheben.u5w2d5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTTools jwtTools;

public String authenticateEmployeeGenerateToken(EmployeeLoginDTO payload){

    Employee employee= employeeService.findByEmail(payload.email());
    if (employee.getPassword().equals(payload.password())){
        return jwtTools.createToken(employee);

    }else {
        throw new UnauthorizedException("Email or password wrong !!!!!!!")
 ;   }

}


}
