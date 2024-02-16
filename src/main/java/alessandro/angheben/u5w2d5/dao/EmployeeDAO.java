package alessandro.angheben.u5w2d5.dao;

import alessandro.angheben.u5w2d5.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsername(String username);

    Page<Employee> findByFirstName(String firstName, Pageable pageable);
    Page<Employee> findByLastName(String lastName, Pageable pageable);
    Page<Employee> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

}
