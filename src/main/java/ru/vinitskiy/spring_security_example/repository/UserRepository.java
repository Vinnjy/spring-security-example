package ru.vinitskiy.spring_security_example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.vinitskiy.spring_security_example.models.MyUser;

import java.util.Optional;

/*
    Нужен репозиторий, который вытаскивал бы данные из бд это буден интерфейс,
                                наследующийся от JpaRepository и в дженериках принимает класс MyUser и т.к. идентификатор Long, то 2-ой в дженериках Long
 */
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByName(String username);
    /*
        Нужен свой метод для поиска/load пользователя по имени, будет return пользователя в обёртке Optinal = нужно для того, чтобы ненулевой контракт
     */
}
