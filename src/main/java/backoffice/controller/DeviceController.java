package backoffice.controller;

import backoffice.model.Device;
import backoffice.model.User;
import backoffice.service.IDeviceService;
import backoffice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeviceController {

    private final IDeviceService deviceService;

    private final IUserService userService;

    public DeviceController(IDeviceService deviceService, IUserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @GetMapping("/devices")
    public ModelAndView devices(@CookieValue(value = "id_token", required = false) String id_token, Model model) {
        if(JWTHelper.isUserAuthenticated(id_token))
        {
            List<Device> devices = deviceService.findAll();

            model.addAttribute("devices", devices);

            return new ModelAndView("devices");
        }
        else
        {
            return new ModelAndView("redirect:/loginError");
        }
    }

    @GetMapping("/device")
    public ModelAndView device(@CookieValue(value = "id_token", required = false) String id_token, @RequestParam(value="deviceId", defaultValue="0") long deviceId, Model model) {
        if(JWTHelper.isUserAuthenticated(id_token))
        {
            Device device = deviceService.findDeviceById(deviceId);
            List<User> availableUsers = userService.findAll();
            availableUsers.removeAll(device.getUsers());

            model.addAttribute("device", device);
            model.addAttribute("availableUsers", availableUsers);

            return new ModelAndView("device");
        }

        else
        {
            return new ModelAndView("redirect:/loginError");
        }
    }

    @GetMapping("/addOrRemoveUser")
    public ModelAndView addOrRemoveUser(@CookieValue(value = "id_token", required = false) String id_token, @RequestParam(value="deviceId", defaultValue="0") long deviceId, @RequestParam(value="userId", defaultValue="0") long userId, Model model) {
        if(JWTHelper.isUserAuthenticated(id_token))
        {
            User user = userService.findUserById(userId);
            Device device = deviceService.findDeviceById(deviceId);
            List<User> availableUsers = userService.findAll();

            if(user.getUserDevices().contains(device))
            {
                device.getUsers().remove(user);
                user.getUserDevices().remove(device);
            }

            else
            {
                device.getUsers().add(user);
                availableUsers.remove(user);
                user.getUserDevices().add(device);
            }

            userService.saveOrUpdateUser(user);

            model.addAttribute("device", device);
            model.addAttribute("availableUsers", availableUsers);

            return new ModelAndView("redirect:/device?deviceId=" + device.getId());
        }
        else
        {
            return new ModelAndView("redirect:/loginError");
        }
    }
}
