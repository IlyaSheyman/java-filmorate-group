package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FilmController.class)
class FilmControllerTest {
    private final FilmController controller = new FilmController();

    @Test
    void addFilm() {
        Film film1 = new Film(1, "фильм", "des",
                LocalDate.of(1995, 2, 9), 100L);

        Film film2 = new Film(2, "фильм", "des",
                LocalDate.of(1895, 12, 27), 100L);

        Film film3 = new Film(3, "", "des",
                LocalDate.of(1995, 2, 9), 100L);

        Film film4 = new Film(1, "фильм", "des",
                LocalDate.of(1995, 2, 9), -100L);

        controller.addEntity(film1);
        assertEquals(film1, controller.getFilms().get(0), "Возвращаемый фильм не совпадает с сохраняемым.");

        final RuntimeException exception = assertThrows(
                RuntimeException.class, () -> controller.addEntity(film2));
        assertEquals("Дата премьеры фильма не может быть раньше 28 декабря 1985г.",
                exception.getMessage());

        /*final RuntimeException exception1 = assertThrows(
                RuntimeException.class, () -> controller.addEntity(film3));
        assertEquals("Название фильма не может быть пустым.",
                exception1.getMessage());*/

        final RuntimeException exception2 = assertThrows(
                RuntimeException.class,
                () -> controller.addEntity(film4));
        assertEquals("Продолжительность фильма не может быть отрицательной.",
                exception2.getMessage());
    }
}