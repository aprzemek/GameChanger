package pl.sdacademy.gamechanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.sdacademy.gamechanger.model.AppUser;
import pl.sdacademy.gamechanger.model.ContactDetails;
import pl.sdacademy.gamechanger.model.dto.AuthoritiesUserDTO;
import pl.sdacademy.gamechanger.model.dto.NewUserDTO;
import pl.sdacademy.gamechanger.service.AppUserService;
import pl.sdacademy.gamechanger.service.AuctionService;
import pl.sdacademy.gamechanger.service.interfaces.EmailSender;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/user")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping(path = "/register")
    public String getAddUser(Model model){
        model.addAttribute("newUser",new NewUserDTO());
        return "/register";
    }
    @PostMapping(path = "/register")
    public String addUser(Model model, NewUserDTO newUserDto) {

        if (!newUserDto.getPassword().equals(newUserDto.getConfirmPassword())) {
            model.addAttribute("newUser", new NewUserDTO());
            model.addAttribute("failMsg", "Hasła są różne!");
            return "/register";
        }

        boolean isNew = appUserService.addUser(newUserDto);

        if (!isNew) {
            model.addAttribute("newUser", new NewUserDTO());
            model.addAttribute("failMsg", "Nazwa użytkownika zajęta!");
            return "/register";
        }
        Context context = new Context();
        context.setVariable("user", appUserService.findByUsername(newUserDto.getUsername()));

        String welcomeMail = templateEngine.process("welcomeMail", context);
        emailSender.sendEmail(newUserDto.getEmail(), "Witamy w Barterowni!", welcomeMail);


        return "redirect:/home?log=true";
    }

    @GetMapping(path = "/login")
    public String getLogin() {
        return "redirect:/home?log=true";
    }


    @GetMapping(path = "/panel")
    public String userPanel(Model model, Principal principal) {
        Optional<AppUser> user = appUserService.findOptionalByUsername(principal.getName());

        user.ifPresent(appUser -> {
            model.addAttribute("user", appUser);
            if (appUser.getPrivilege() > 1) {
                model.addAttribute("notAcceptedAuctions", auctionService.findAllNotAccepted());
            }
        });

        return "/userPanel";
    }

    @GetMapping(path = "/findUser")
    public String getFindUser(Model model) {
        model.addAttribute("user", new AuthoritiesUserDTO());
        return "/findUser";
    }

    @PostMapping(path = "/findUser")
    public String findUser(Model model, AuthoritiesUserDTO userDTO) {

        model.addAttribute("user", new AuthoritiesUserDTO());

        List<AppUser> users = appUserService.findAllByUsernameContaining(userDTO.getUsername());
        users.forEach(appUser -> System.out.println(appUser.getUsername()));
        model.addAttribute("userList", users);
        return "/findUser";
    }

    @GetMapping(path = "/makeExpert/{id}")
    public String makeExpert(@PathVariable(name = "id") long id, Principal principal) {
        AppUser admin = appUserService.findByUsername(principal.getName());

        if (admin.getPrivilege() > 2) {
            appUserService.makeExpert(id);
        }

        return "redirect:/user/findUser";
    }


    @GetMapping(path = "/makeAdmin/{id}")
    public String makeAdmin(@PathVariable(name = "id") long id, Principal principal) {
        AppUser admin = appUserService.findByUsername(principal.getName());

        if (admin.getPrivilege() > 2) {
            appUserService.makeAdmin(id);
        }

        return "redirect:/user/findUser";
    }


    @GetMapping(path = "/makeUser/{id}")
    public String makeUser(@PathVariable(name = "id") long id, Principal principal) {
        AppUser admin = appUserService.findByUsername(principal.getName());

        if (admin.getPrivilege() > 2) {
            appUserService.makeUser(id);
        }

        return "redirect:/user/findUser";
    }


    @GetMapping(path = "/details")
    public String getDetails(Principal principal, Model model) {

        AppUser user = appUserService.findByUsername(principal.getName());
        if (user.getContactDetails() != null) {
            model.addAttribute("details", user.getContactDetails());
        } else {
            model.addAttribute("details", new ContactDetails());
        }
        return "/userDetails";
    }

    @PostMapping(path = "changeDetails")
    public String changeDetails(Principal principal, ContactDetails contactDetails) {
        AppUser user = appUserService.findByUsername(principal.getName());

        appUserService.changeDetails(user, contactDetails);

        return "redirect:/user/details";
    }


    @GetMapping(path = "/activation")
    public String activateUser(@RequestParam(name = "code") String code) {
        Optional<AppUser> userOptional = appUserService.findByPassword(code);
        System.out.println(code);
        if (userOptional.isPresent()) {
            System.out.println("found user");
            AppUser appUser = userOptional.get();
            appUserService.makeUser(appUser.getId());
        }

        return "redirect:/home";
    }
}



