package ru.vinitskiy.spring_security_example.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import ru.vinitskiy.spring_security_example.models.Application;
import ru.vinitskiy.spring_security_example.models.MyUser;
import ru.vinitskiy.spring_security_example.services.AppService;

import java.util.List;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class AppController {
    private AppService service;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to page";
    }
    /*
            Но что делать, если надо дать доступ к каким-то конкретным точкам людям с определёнными правами:

            ⦁	@PreAuthorize() = с помощью неё описываем правила, разрешающие получать доступ к определённой точке
	            @PreAuthorize("hasAuthority('ROLE_USER')")
            ⦁	можно и больше проверок авторитета использовать
            ⦁	можно и для контрольной точки, которая получает приложение по идентификатору
     */
    @GetMapping("/all-app")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Application> allApplication() {
        return service.allApplication();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Application applicationById(@PathVariable int id){
        return service.applicationById(id);
    }
    /*
        Но мы ещё не создали ENDPOINT для создания нового пользователя

        1. Перейдём в контроллер, нужен PostMapping("/new-user"), принимает моего пользователя из тела запроса
     */
    @PostMapping("/new-user")
    public String addUser(@RequestBody MyUser user){
        service.addUser(user);
        return "User is save";
    }
}
