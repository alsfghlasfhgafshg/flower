package sales.salesmen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sales.salesmen.entity.Authority;
import sales.salesmen.entity.User;
import sales.salesmen.service.AuthorityService;
import sales.salesmen.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @PostMapping("/register")
    public String registerUser(@RequestParam(value = "customer",required = false,defaultValue = "none")String customer,
                               User user){
        List<Authority> authorities = new ArrayList<>();
        if (customer.equals("3")) {
            authorities.add(authorityService.getAuthorityById(3L).get());
        }else{
            authorities.add(authorityService.getAuthorityById(2L).get());
        }
        user.setAuthorities(authorities);
        for (GrantedAuthority authority:user.getAuthorities()
             ) {
            System.out.println(authority);
        }
        user.setEncodePassword(user.getPassword());
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){return "login";}

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        model.addAttribute("errorMsg","用户名或密码错误");
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
