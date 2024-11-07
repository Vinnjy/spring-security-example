package ru.vinitskiy.spring_security_example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.vinitskiy.spring_security_example.services.MyUserDetailsService;

/*
        1. Но у пользователей в памяти нет никаких ролей, поэтому добавим их в билдере
        2. Но чтобы моделирование авторизации было на уровне метода работало, нужно включить ещё 1 аннотацию на классе @EnableMethodSecurity
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
       /*UserDetails admin = User.builder().username("admin").password(encoder.encode("admin")).roles("ADMIN").build();
        UserDetails user = User.builder().username("user").password(encoder.encode("user")).roles("USER").build();
        UserDetails anton = User.builder().username("anton").password(encoder.encode("password")).roles("ADMIN", "USER").build();
        return new InMemoryUserDetailsManager(admin, user, anton);*/
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*
            Сделаем так, что ДОСТУП БУДЕТ У ВСЕХ
                ⦁	ФИЛЬТР в SecurityConfig
                ⦁	через запятую укажем ещё 1 путь

     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers("/welcome", "/new-user").permitAll()
                .requestMatchers("/**").authenticated()).formLogin(AbstractAuthenticationFilterConfigurer::permitAll).build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        /*
              1. Создадим экземпляр DAO этого провайдера, то есть создадим экземпляр класса DaoAuthenticationProvider
                ⦁	DaoAuthenticationProvider = реализация провайдера, которая реализует
                                                        UserDetailsService и PasswordEncoder для аутентификации имени пользователя и пароля
                ⦁	Теперь для этого провайдера устанавливаем UserDetailsService и PasswordEncoder через сеттеры
                ⦁	а это значит, что PasswordEncoder, который принимается в аргументе UserDetailsService нам уже не нужен
                ⦁	в конце вернём этот провайдер
         */
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
/*
        ⦁	АУТЕНТИФИКАЦИЯ = механизм, с помощью которого вызызвающие абоненты доказывают, что они дейтсвиуют от имени конкретных пользователей или системы
        ⦁	АУТЕНТИФИКАЦИЯ отвечает на вопрос: КТО ВЫ, используя УЧЁТНЫЕ ДАННЫЕ (как имя пользоватея, пароль)
        ⦁	АУТЕНТИФИКАЦИОННЫЙ ПРОВАЙДЕР используется для подтверждения ЛИЧНОСТИ ПОЛЬЗОВАТЕЛЯ
 */
