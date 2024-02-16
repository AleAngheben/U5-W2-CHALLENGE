package alessandro.angheben.u5w2d5.services;

import alessandro.angheben.u5w2d5.dao.DeviceDAO;
import alessandro.angheben.u5w2d5.dao.EmployeeDAO;
import alessandro.angheben.u5w2d5.entities.Device;
import alessandro.angheben.u5w2d5.entities.Employee;
import alessandro.angheben.u5w2d5.enums.DeviceStatus;
import alessandro.angheben.u5w2d5.exceptions.NotFoundException;
import alessandro.angheben.u5w2d5.payloads.IdEmployeeDTO;
import alessandro.angheben.u5w2d5.payloads.NewDeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private EmployeeService employeeService;

    public Device saveDevice(NewDeviceDTO payload) {

        Device newDevice = new Device();
        newDevice.setDeviceType(payload.type());
        newDevice.setStatus(DeviceStatus.AVAIABLE);
        return deviceDAO.save(newDevice);

    }

    public Device findById(UUID id) {
        return deviceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    //GLI PASSO UN DTO CHE CHE SI RIFERISCE A UUID PER EMPLOYEE (SERVE UNE PROPR UNIVOCA IN MODO DA PASSARE UN DET EMPLYEE AL DEVICE)
    public Device setEmployeeOnDevice(UUID deviceId, IdEmployeeDTO employeeId) {
        Employee employee = employeeService.findById(employeeId.id());
        Device found = this.findById(deviceId);
        found.setEmployee(employee);
        found.setStatus(DeviceStatus.ASSIGNED);
        return deviceDAO.save(found);
    }

    public Page<Device> getAllDevices(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return deviceDAO.findAll(pageable);
    }

    public void findAndDelete(UUID id) {
        Device found = this.findById(id);
        deviceDAO.delete(found);
    }


    public Device findAndUpdate(UUID id, NewDeviceDTO device) {
        Device found = this.findById(id);
        found.setDeviceType(device.type());
        return deviceDAO.save(found);
    }


}
