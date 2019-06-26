package backoffice.controller;

import backoffice.model.User;
import backoffice.service.IUserService;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Controller
public class AuthController {

    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(name = "id_token") String id_token, HttpServletResponse response)
    {
        if(JWTHelper.isUserAuthenticated(id_token) && JWTHelper.ParseJWT(id_token).getClaims().get("family_name").asString().equals("Heck"))
        {
            Cookie cookie = new Cookie("id_token", id_token);
            System.out.println(id_token);
            response.addCookie(cookie);

            return new ModelAndView("login");
        }
        if(JWTHelper.isUserAuthenticated(id_token))
        {
            Cookie cookie = new Cookie("id_token", id_token);
            System.out.println(id_token);
            response.addCookie(cookie);

            return new ModelAndView("redirect:/");
        }
        else
        {
            return new ModelAndView("redirect:/loginError");
        }
    }

    @GetMapping("/loginUser")
    public RedirectView loginUser()
    {
        String tenant = "atlantisproject.onmicrosoft.com";
        String redirect_url = "http://localhost:8090/login";
        String client_id = "27fb84fe-4baf-4b6b-bfe7-f2d0638f2790";

        return new RedirectView("https://atlantisproject.b2clogin.com/" +
                tenant +
                "/oauth2/v2.0/authorize?" +
                "client_id=" + client_id +
                "&response_type=code%20id_token" +
                "&redirect_uri=" + redirect_url +
                "&response_mode=query" +
                "&scope=openid" +
                "&p=B2C_1_SignUporSignIn");
    }

    @GetMapping("/")
    public ModelAndView home(@CookieValue(value = "id_token", required = false) String id_token, Model model)
    {
        model.addAttribute("userConnected", false);

        if(JWTHelper.isUserAuthenticated(id_token))
        {
            model.addAttribute("userConnected", true);

            Map<String, Claim> claims = Objects.requireNonNull(JWTHelper.ParseJWT(id_token)).getClaims();

            User user = userService.findUserByADID(claims.get("oid").asString());

            if(user == null)
                user = CreateUser(claims);

            model.addAttribute("user", user);
        }

        return new ModelAndView("home");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletResponse response)
    {
        String tenant = "atlantisproject.onmicrosoft.com";
        String redirect_url = "http://localhost:8090/";
        String client_id = "27fb84fe-4baf-4b6b-bfe7-f2d0638f2790";

        Cookie cookie = new Cookie("id_token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new RedirectView("https://atlantisproject.b2clogin.com/" +
                tenant +
                "/oauth2/v2.0/logout?" +
                "client_id=" + client_id +
                "&response_type=code%20id_token" +
                "&redirect_uri=" + redirect_url +
                "&response_mode=query" +
                "&scope=openid" +
                "&p=B2C_1_SignUporSignIn");
    }

    private User CreateUser(Map<String, Claim> claims)
    {
        User userToInsert = new User(
                claims.get("oid").asString(),
                claims.get("given_name").asString().substring(0, 1).toUpperCase() + claims.get("given_name").asString().substring(1),
                claims.get("family_name").asString().substring(0, 1).toUpperCase() + claims.get("family_name").asString().substring(1)
        );

        return userService.saveOrUpdateUser(userToInsert);
    }
}
