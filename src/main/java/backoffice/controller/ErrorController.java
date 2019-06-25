package backoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/loginError")
    public String loginError(@CookieValue(value = "id_token", required = false) String id_token, Model model)
    {
        if(JWTHelper.isUserAuthenticated(id_token))
        {
            model.addAttribute("userConnected", true);
        }

        else
        {
            model.addAttribute("userConnected", false);
        }

        return "loginError";
    }

    @GetMapping("/error")
    public String error(@CookieValue(value = "id_token", required = false) String id_token, Model model)
    {
        if(JWTHelper.isUserAuthenticated(id_token))
        {
            model.addAttribute("userConnected", true);
        }

        else
        {
            model.addAttribute("userConnected", false);
        }
        return "error";
    }
}
