package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = UserController.class)
class UserControllerTest {
    private UserController controller = new UserController();

    @Test
    void addUser() {
        User user = new User(1, "qwerty@mail.ru", "Иван", "login",
                    LocalDate.of(2000, 02, 12));

        User user2 = new User(2, "qwerty", "Иван", "login",
                    LocalDate.of(2000, 02, 12));

        User user3 = new User(3, "qwerty@mail.ru", "иван", "log in",
                LocalDate.of(2000, 02, 12));

        User user4 = new User(3, "qwerty@mail.ru", "Иван", "login",
                LocalDate.of(2040,  10, 12));

        User user5 = new User(1, "qwerty@mail.ru", "", "login",
                LocalDate.of(2000, 02, 12));

        controller.addEntity(user);
        assertEquals(user, controller.getUsers().get(0));

        final RuntimeException exception = assertThrows(
                RuntimeException.class, () -> controller.addEntity(user2));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.",
                exception.getMessage());

        final RuntimeException exception1 = assertThrows(
                RuntimeException.class, () -> controller.addEntity(user3));
        assertEquals("Логин не может быть пустым и содержать пробелы.",
                exception1.getMessage());

        final RuntimeException exception2 = assertThrows(
                RuntimeException.class, () -> controller.addEntity(user4));
        assertEquals("Дата рождения не может быть в будущем.",
                exception2.getMessage());

        controller.addEntity(user5);
        assertEquals(user5.getName(), user5.getLogin(), "Имя не равно логину.");
    }
}