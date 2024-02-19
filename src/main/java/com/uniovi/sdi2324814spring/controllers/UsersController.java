package com.uniovi.sdi2324814spring.controllers;

import com.uniovi.sdi2324814spring.entities.Mark;
import com.uniovi.sdi2324814spring.entities.User;
import com.uniovi.sdi2324814spring.services.MarksService;
import com.uniovi.sdi2324814spring.services.RolesService;
import com.uniovi.sdi2324814spring.services.SecurityService;
import com.uniovi.sdi2324814spring.services.UsersService;
import com.uniovi.sdi2324814spring.validators.SignUpFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UsersController {
    private final SignUpFormValidator signUpFormValidator;
    private final UsersService usersService;
    private final SecurityService securityService;
    private final RolesService rolesService;
    private final MarksService marksService;

    public UsersController(UsersService usersService, SecurityService securityService, SignUpFormValidator
            signUpFormValidator, RolesService rolesService, MarksService marksService) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.signUpFormValidator = signUpFormValidator;
        this.rolesService= rolesService;
        this.marksService = marksService;
    }

    @RequestMapping("/user/list")
    public String getList(Model model, Pageable pageable,
                          @RequestParam(value="", required = false) String searchText) {
        Page<User> users;
        if (searchText != null && !searchText.isEmpty()) {
            users = usersService.searchUsersByName(pageable, searchText);
        } else {
            users = usersService.getUsers(pageable);
        }
        model.addAttribute("usersList", users.getContent());
        model.addAttribute("page", users);
        return "user/list";
    }

    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        return "user/add";
    }
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }
    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }
    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }
    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute User user, @PathVariable Long id){
        usersService.updateUser(id, user);
        return "redirect:/user/details/"+id;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        Page<Mark> marks = marksService.getMarksForUser(pageable, activeUser);
        model.addAttribute("marksList", marks.getContent());
        model.addAttribute("page", marks);
        return "home";
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model, Pageable pageable){
        Page<User> users = usersService.getUsers(pageable);
        model.addAttribute("usersList", users.getContent());
        return "user/list :: usersTable";
    }
}