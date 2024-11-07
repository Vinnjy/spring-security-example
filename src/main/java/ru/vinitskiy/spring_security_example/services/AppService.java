package ru.vinitskiy.spring_security_example.services;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vinitskiy.spring_security_example.models.Application;
import ru.vinitskiy.spring_security_example.models.MyUser;
import ru.vinitskiy.spring_security_example.repository.UserRepository;

import java.util.List;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class AppService {
    private List<Application> applications;
    private UserRepository repository;
    private PasswordEncoder encoder;

    @PostConstruct
    public void loadAppInById(){
        Faker faker = new Faker();
        applications = IntStream.rangeClosed(1,100).mapToObj(i -> Application.builder()
                .id(i).name(faker.app().name()).author(faker.app().author()).version(faker.app().version()).build()).toList();
    }
    public List<Application> allApplication(){
        return applications;
    }

    public Application applicationById(int id){
        return applications.stream().filter(app -> app.getId() == id).findFirst().orElse(null);
    }

    public void addUser(MyUser user){
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }
    /*
        1. Перейдём в сервис и внедрим PasswordEncoder и используем в методе по добавлению пользователя ПРЕЖДЕ ЧЕМ СОХРАНИТЬ В РЕПОЗИТОРИИИ
        ⦁	добавляет его в репозиторий
        ⦁	вызовим метод в контрольной точке
        2. Добавим @AllArgsConstructor на класс AppService, добавим поле UserRepository
     */
}

