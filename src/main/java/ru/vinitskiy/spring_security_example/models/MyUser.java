package ru.vinitskiy.spring_security_example.models;

import jakarta.persistence.*;
import lombok.Data;
/*
    ⦁	СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ ЧЕРЕЗ ОТДЕЛЬНЫЙ КОНТРОЛЛЕР, А НЕ НАПРЯМУЮ В UserDeatailsService
 */
@Data
@Entity
@Table(name="users")
public class MyUser {
    /*
        1. Создадим поле id, оно будет идентификатором в бд = @Id, автоинкрементироваться = @GeneratedValue(strategy = Generation.IDENTITY)
        2. Создадим поле name, оно будет уникальным = @Column(@Unique = true)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String password;
    private String roles;
}
/*
    1. Аннотация @Entity указывает Hibernate, что данный класс является сущностью (entity bean).
                    Такой класс должен иметь конструктор по умолчанию (пустой конструктор).
    2. Аннотация @Entity указывает, что эта модель считается сущностью, связанной с таблицей в базе данных Аннотация @Table задает имя таблицы
 */
