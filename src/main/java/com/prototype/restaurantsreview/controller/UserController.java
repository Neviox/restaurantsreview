package com.prototype.restaurantsreview.controller;

import com.prototype.restaurantsreview.dto.UserRegistrationRequest;
import com.prototype.restaurantsreview.service.ConfirmationTokenService;
import com.prototype.restaurantsreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("signup",new UserRegistrationRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(Model model,UserRegistrationRequest request){
        userService.registerAndValidateUser(request);
        model.addAttribute("signup",request);
        return "usersaved";
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        confirmationTokenService.confirmToken(token);
        return "userconfirmed";
    }

    @GetMapping("/login")
    public String login() {return "login";}


    @Transactional
    @RequestMapping(value = "/setadmin/{id}", method = RequestMethod.GET)
    public String setAdmin(@PathVariable int id, Model model)
    {
        userService.setUserAdmin(id);
        model.addAttribute("user",userService.getUsers());
        return "panel";
    }

    @Transactional
    @RequestMapping(value = "/setuser/{id}", method = RequestMethod.GET)
    public String setUser(@PathVariable int id, Model model)
    {
        userService.setUserUser(id);
        model.addAttribute("user",userService.getUsers());
        return "panel";
    }

    @Transactional
    @RequestMapping(value = "/lock/{id}", method = RequestMethod.GET)
    public String setLocked(@PathVariable int id, Model model)
    {
        userService.lockUser(id);
        model.addAttribute("user",userService.getUsers());
        return "panel";
    }

    @Transactional
    @RequestMapping(value = "/unlock/{id}", method = RequestMethod.GET)
    public String setUnlocked(@PathVariable int id, Model model)
    {
        userService.unlockUser(id);
        model.addAttribute("user",userService.getUsers());
        return "panel";
    }

    @Transactional
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public String setEnabled(@PathVariable int id, Model model)
    {
        userService.enableUser(id);
        model.addAttribute("user",userService.getUsers());
        return "panel";
    }
    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id, Model model)
    {
        userService.deleteUserById(id);
        model.addAttribute("user",userService.getUsers());
        return "panel";
    }
}
