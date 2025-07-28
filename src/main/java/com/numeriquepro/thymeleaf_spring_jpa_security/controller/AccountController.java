package com.numeriquepro.thymeleaf_spring_jpa_security.controller;
import com.numeriquepro.thymeleaf_spring_jpa_security.dto.LoginDto;
import com.numeriquepro.thymeleaf_spring_jpa_security.dto.UserDto;

import com.numeriquepro.thymeleaf_spring_jpa_security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AccountController {
    private final UserService userService;
    public AccountController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/register")
    public String register(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        model.addAttribute("success", false);
        model.addAttribute("availableRoles", List.of("ADMIN", "CLIENT", "ANIMATEUR"));
        return "register";
    }


    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute UserDto userDto,
            BindingResult result
    ) {


        // Vérifie que les mots de passe correspondent
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.addError(
                    new FieldError("userDto", "confirmPassword", "Password and Confirm Password do not match")
            );
        }

        // Vérifie si un utilisateur avec le même email existe déjà
        UserDto existUserDto = userService.findByEmail(userDto.getEmail());
        if (existUserDto != null) {
            result.addError(
                    new FieldError("userDto", "email", "Email address is already used")
            );
        }

        // S’il y a des erreurs, on retourne à la page d’inscription
        if (result.hasErrors()) {
            return "register"; // vue HTML de retour avec erreurs
        }

        try {
            // create new account

           userService.save(userDto);
            // Redirige vers la page de connexion ou une page de succès
            model.addAttribute("success", true);
            model.addAttribute("userDto", new UserDto()); // Réinitialise le formulaire
        } catch (Exception ex) {
            result.addError(
                    new FieldError("userDto", "firstName", ex.getMessage())
            );
        }


        return "register"; // ou page de succès
    }



    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }




}
