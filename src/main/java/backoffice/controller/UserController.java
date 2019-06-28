package backoffice.controller;

import backoffice.model.Device;
import backoffice.model.User;
import backoffice.service.IDeviceService;
import backoffice.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class UserController {

    private final IUserService userService;

    private final IJWTHelper jwthelper;

    private final IDeviceService deviceService;

    public UserController(IUserService userService, IJWTHelper jwthelper, IDeviceService deviceService) {
        this.userService = userService;
        this.jwthelper = jwthelper;
        this.deviceService = deviceService;
    }

    @GetMapping("/users")
    public ModelAndView users(@CookieValue(value = "id_token") String id_token, Model model) {
        if(jwthelper.isUserAuthenticated(id_token))
        {
            List<User> users = userService.findAll();
            model.addAttribute("users", users);

            return new ModelAndView("users");
        }

        else
        {
            return new ModelAndView("redirect:/loginError");
        }
    }

    @GetMapping("/user")
    public ModelAndView user(@CookieValue(value = "id_token") String id_token, @RequestParam(value="userId", defaultValue="0") long userId, Model model) {
        if(jwthelper.isUserAuthenticated(id_token))
        {
            User user = userService.findUserById(userId);
            List<Device> availableDevices = deviceService.findAll();
            availableDevices.removeAll(user.getUserDevices());

            model.addAttribute("user", user);
            model.addAttribute("availableDevices", availableDevices);

            return new ModelAndView("user");
        }

        else
        {
            return new ModelAndView("redirect:/loginError");
        }
    }

    @GetMapping("/addOrRemoveDevice")
    public ModelAndView addDevice(@CookieValue(value = "id_token") String id_token, @RequestParam(value="userId", defaultValue="0") long userId, @RequestParam(value="deviceId", defaultValue="0") long deviceId, Model model) {
        if(jwthelper.isUserAuthenticated(id_token))
        {
            User user = userService.findUserById(userId);
            Device device = deviceService.findDeviceById(deviceId);
            List<Device> availableDevices = deviceService.findAll();

            if(user.getUserDevices().contains(device))
                user.getUserDevices().remove(device);
            else
                user.getUserDevices().add(device);

            user = userService.saveOrUpdateUser(user);

            availableDevices.removeAll(user.getUserDevices());

            model.addAttribute("user", user);
            model.addAttribute("availableDevices", availableDevices);

            return new ModelAndView("redirect:/user?userId=" + user.getId());
        }
        else
        {
            return new ModelAndView("redirect:/loginError");
        }
    }
}
