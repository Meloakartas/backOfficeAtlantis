package backoffice.controller;

import backoffice.model.Device;
import backoffice.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DeviceController {

    @Autowired
    private IDeviceService deviceService;

    @GetMapping("/devices")
    public String users(Model model) {
        List<Device> devices = deviceService.findAll();

        model.addAttribute("devices", devices);

        return "devices";
    }
}
