package backoffice.controller;

import backoffice.model.User;
import backoffice.service.IUserService;
import com.auth0.jwt.interfaces.Claim;
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

    private static final String FAMILY_NAME = "family_name";
    private static final String GIVEN_NAME = "given_name";
    private static final String ID_TOKEN = "id_token";

    private final IJWTHelper jwthelper;

    public AuthController(IUserService userService, IJWTHelper jwthelper) {
        this.userService = userService;
        this.jwthelper = jwthelper;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = ID_TOKEN) String id_token, HttpServletResponse response)
    {
        if(jwthelper.isUserAuthenticated(id_token) && jwthelper.ParseJWT(id_token).getClaims().get(FAMILY_NAME).asString().equals("Heck"))
        {
            Cookie cookie = new Cookie(ID_TOKEN, id_token);
            cookie.setHttpOnly(true); // Compliant
            System.out.println(id_token);
            response.addCookie(cookie);

            return new ModelAndView("login");
        }
        if(jwthelper.isUserAuthenticated(id_token))
        {
            Cookie cookie = new Cookie(ID_TOKEN, id_token);
            cookie.setHttpOnly(true); // Compliant
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
        String redirectURL = "http://localhost:8090/login";
        String clientID = "27fb84fe-4baf-4b6b-bfe7-f2d0638f2790";

        return new RedirectView("https://atlantisproject.b2clogin.com/" +
                tenant +
                "/oauth2/v2.0/authorize?" +
                "client_id=" + clientID +
                "&response_type=code%20id_token" +
                "&redirect_uri=" + redirectURL +
                "&response_mode=query" +
                "&scope=openid" +
                "&p=B2C_1_SignUporSignIn");
    }

    @GetMapping("/")
    public ModelAndView home(@CookieValue(value = ID_TOKEN, required = false) String id_token, Model model)
    {
        model.addAttribute("userConnected", false);

        if(jwthelper.isUserAuthenticated(id_token))
        {
            model.addAttribute("userConnected", true);

            Map<String, Claim> claims = Objects.requireNonNull(jwthelper.ParseJWT(id_token)).getClaims();

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
        String redirectURL = "http://localhost:8090/";
        String clientID = "27fb84fe-4baf-4b6b-bfe7-f2d0638f2790";

        Cookie cookie = new Cookie(ID_TOKEN, null);
        cookie.setHttpOnly(true); // Compliant
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new RedirectView("https://atlantisproject.b2clogin.com/" +
                tenant +
                "/oauth2/v2.0/logout?" +
                "client_id=" + clientID +
                "&response_type=code%20id_token" +
                "&redirect_uri=" + redirectURL +
                "&response_mode=query" +
                "&scope=openid" +
                "&p=B2C_1_SignUporSignIn");
    }

    private User CreateUser(Map<String, Claim> claims)
    {
        User userToInsert = new User(
                claims.get("oid").asString(),
                claims.get(GIVEN_NAME).asString().substring(0, 1).toUpperCase() + claims.get(GIVEN_NAME).asString().substring(1),
                claims.get(FAMILY_NAME).asString().substring(0, 1).toUpperCase() + claims.get(FAMILY_NAME).asString().substring(1)
        );

        return userService.saveOrUpdateUser(userToInsert);
    }
}
