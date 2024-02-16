package alessandro.angheben.u5w2d5.controllers;

import alessandro.angheben.u5w2d5.entities.Device;
import alessandro.angheben.u5w2d5.exceptions.BadRequestException;
import alessandro.angheben.u5w2d5.payloads.IdEmployeeDTO;
import alessandro.angheben.u5w2d5.payloads.NewDeviceDTO;
import alessandro.angheben.u5w2d5.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public Page<Device> getAllDevices(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String orderBy) {
        return this.deviceService.getAllDevices(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Device findById(@PathVariable UUID id) {
        return this.deviceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Device save(@RequestBody @Validated NewDeviceDTO newDeviceDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.saveDevice(newDeviceDTO);
    }

    @PatchMapping("/{id}/setEmployee")
    @ResponseStatus(HttpStatus.OK)
    public Device setUser(@PathVariable UUID id, @RequestBody IdEmployeeDTO employeeId, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.setEmployeeOnDevice(id, employeeId);
    }


    @PutMapping("/{id}")
    public Device findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated NewDeviceDTO updatedDevice, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.findAndUpdate(id, updatedDevice);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        this.deviceService.findAndDelete(id);
    }
}
