package ru.vinitskiy.spring_security_example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vinitskiy.spring_security_example.config.MyUserDetails;
import ru.vinitskiy.spring_security_example.models.MyUser;
import ru.vinitskiy.spring_security_example.repository.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    /*
        Брать пользователя будем из только что созданного репозитория = поле этого репозитория в классе
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = repository.findByName(username);
        return user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + "Not Found"));
    }
    /*
        1. Видим, что return данный метод UserDetails, подходяшего класса нет, поэтому надо сделать собственную реализацию этого интерфейса
        2. Return к сервису MyUserDetailsService найдём пользователя из репозитория, return его,но прежде за кастим его до MyUserDetails,иначе выбросит исключение
        3. Сделаем этот класс сервисом и внедрим его репозиторий в поле @Autowired
     */
}
