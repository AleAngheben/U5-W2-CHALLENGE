package alessandro.angheben.u5w2d5.services;

import alessandro.angheben.u5w2d5.dao.EmployeeDAO;
import alessandro.angheben.u5w2d5.entities.Employee;
import alessandro.angheben.u5w2d5.exceptions.BadRequestException;
import alessandro.angheben.u5w2d5.exceptions.NotFoundException;
import alessandro.angheben.u5w2d5.exceptions.UnauthorizedException;
import alessandro.angheben.u5w2d5.payloads.NewEmployeeDTO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    private Cloudinary cloudinary;

    public Page<Employee> getEmployees(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return employeeDAO.findAll(pageable);
    }

    public Employee save(NewEmployeeDTO employee) throws IOException{

        employeeDAO.findByEmail(employee.email()).ifPresent(employee1 -> {
            throw new BadRequestException("L'email " + employee.email() + " è già in uso");
        });

        employeeDAO.findByUsername(employee.username()).ifPresent(employee1 -> {
            throw new BadRequestException("L'username " + employee.username() + " è gia in uso");
        });
        Employee newEmployee = new Employee();
        newEmployee.setUsername(employee.username());
        newEmployee.setFirstName(employee.firstName());
        newEmployee.setLastName(employee.lastName());
        newEmployee.setEmail(employee.username());

        return employeeDAO.save(newEmployee);
    }

    public Employee findById(UUID id) {
        return employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Employee findByIdAndUpdate(UUID id, NewEmployeeDTO body) {

        Employee found = this.findById(id);
        found.setUsername(body.username());
        found.setFirstName(body.firstName());
        found.setLastName(body.lastName());

        return employeeDAO.save(found);
    }

    public void deleteById(UUID id) {
        Employee found = this.findById(id);
        employeeDAO.delete(found);
    }


//PER CARICARE L'IMMAGINE AD UN DETERMINATO EMPLOYEE
    public Employee uploadProfilePicture(UUID id, MultipartFile file) throws IOException{
        Employee found = this.findById(id);
        String profilePicURL = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setEmployeeImg(profilePicURL);
        return employeeDAO.save(found);
    }

    public Employee findByEmail(String email) {
        return employeeDAO.findByEmail(email).orElseThrow(()-> new UnauthorizedException("not found sium"));
    }


}
