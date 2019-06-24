package hello;

import hello.model.Device;
import hello.model.User;
import hello.service.IDeviceService;
import hello.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeviceService deviceService;

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.findAll();

        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/user")
    public String user(@RequestParam(value="id", defaultValue="0") long id, Model model) {
        User user = userService.findUserById(id);
        List<Device> availableDevices = deviceService.findAll();
        availableDevices.removeAll(user.getUserDevices());

        model.addAttribute("user", user);
        model.addAttribute("availableDevices", availableDevices);

        return "user";
    }

    @GetMapping("/addOrRemoveDevice")
    public ModelAndView addDevice(@RequestParam(value="userId", defaultValue="0") long userId, @RequestParam(value="deviceId", defaultValue="0") long deviceId, Model model) {
        User user = userService.findUserById(userId);
        Device device = deviceService.findDeviceById(deviceId);
        if(user.getUserDevices().contains(device))
        {
            user.getUserDevices().remove(device);
        }
        else
        {
            user.getUserDevices().add(device);
        }

        user = userService.updateUser(user);

        List<Device> availableDevices = deviceService.findAll();
        availableDevices.removeAll(user.getUserDevices());

        model.addAttribute("user", user);
        model.addAttribute("availableDevices", availableDevices);

        return new ModelAndView("redirect:/user?id=" + user.getId());
    }
}
