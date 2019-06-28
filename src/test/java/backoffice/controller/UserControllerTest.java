package backoffice.controller;

import backoffice.controller.IJWTHelper;
import backoffice.controller.UserController;
import backoffice.service.IDeviceService;
import backoffice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private IDeviceService deviceService;

    @MockBean
    private IJWTHelper jwthelper;

    @Test
    public void testUserController_LoginError() {
        UserController userController = new UserController(userService, jwthelper, deviceService);
        Cookie cookie = new Cookie("id_token", "");
        ModelAndView result = userController.users(cookie.toString(), null);

        assertViewName(result, "redirect:/loginError");
    }
}
