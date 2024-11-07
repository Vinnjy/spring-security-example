package ru.vinitskiy.spring_security_example.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vinitskiy.spring_security_example.models.MyUser;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/*
    Настройка собстенного класса по предоставлению основной информации о пользователе
 */
public class MyUserDetails implements UserDetails {

    private MyUser user;
    /*
    Чтобы получить все эти данные для переопределённых методов, самый простой способ это в конструктооре принимать пользователя
    */
    public MyUserDetails(MyUser user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRoles().split(", "))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    /*
        1. Роли храняться в строковом экземпляре и return должны в какой-то коллекции, поэтому используем Arrays.stream.
        2. Тип данных, который должен храниться в коллекции обязан наследоваться от GrantedAuthority и тогда, к примеру,
                                возьмём SimpleGrantedAuthority = сериализуется и у него есть поле роль, создаём объект этого класса
        3. Благодаря этой записи:
                                    ⦁	сплитим строку в роли на отдельные кусочки
                                    ⦁	преобразуем строковое значение в нужный класс
                                    ⦁	собираем все роли в лист
                                    ⦁	то есть return все полномочия пользователя
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    /*
        Пароль возьмём у user с помощью геттера пароль и имя пользователя
    */

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /*
        isAccountNotExpired() = указывает истёк ли срок действия учётной записи пользователя (то есть истёкшая не может быть аутентифицирована)
                                ⦁	true = если действительна (иначе ...)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /*
        isAccountNotLocked() = указывает заблочена ли учётная запись
                                ⦁	true = если не заблочен (иначе ...)
    */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /*
        isCredentialNotExpired() = указывает истёк ди срок действия пароля (просроченные аутентификации не...)
                                    ⦁	true = если действительны (иначе ...)
    */
    @Override
    public boolean isEnabled() {
        return true;
    }
    /*
        isEnabled() = включён ли пользователь или нет
                    ⦁	true = если ВКЛ (иначе ...)
     */
}
