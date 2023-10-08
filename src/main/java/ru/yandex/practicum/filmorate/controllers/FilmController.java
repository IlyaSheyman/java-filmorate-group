package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
@RestController
public class FilmController {

    private final FilmService filmService;
    private final UserService userService;

    @ResponseBody
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма.");
        return filmService.addFilm(film);
    }

    @ResponseBody
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на изменение фильма.");
        return filmService.updateFilm(film);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable String id) {
        log.info("Получен запрос на получения фильма с id - {}", id);
        return filmService.getFilm(Integer.parseInt(id));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public void deleteFilmById(@PathVariable String id) {
        log.info("Получен запрос на удаление фильма с id - {}", id);
        filmService.deleteFilm(Integer.parseInt(id));
    }

    @ResponseBody
    @GetMapping()
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @ResponseBody
    @PutMapping("/{id}/like/{userId}")
    public Entity addLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Запрос на добавление лайка от пользователя с ID - {}.", userId);
        filmService.addLike(Integer.parseInt(id), Integer.parseInt(userId));
        return filmService.getFilm(Integer.parseInt(id));
    }

    @ResponseBody
    @DeleteMapping("/{id}/like/{userId}")
    public Entity deleteLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Запрос на удаление лайка от пользователя с ID - {}.", userId);
        filmService.deleteLike(Integer.parseInt(id), Integer.parseInt(userId));
        userService.getUser(Integer.parseInt(userId));
        return filmService.getFilm(Integer.parseInt(id));
    }

    /**
     * Возвращает список самых популярных фильмов указанного жанра за нужный год.

     * @param count   Лимит фильмов (по умолчанию 10)
     * @param genreId идентификатор жанра (только положительное число)
     * @param year    год релиза (только положительное число)
     * @return список самых популярных фильмов
     */
    @GetMapping("/popular")
    @Validated
    public List<Film> getTopFilm(
            @Positive @RequestParam(defaultValue = "10") Integer count,
            @Positive @RequestParam(required = false) Integer genreId,
            @RequestParam(required = false) Integer year) {

        return filmService.getTopFilms(count, genreId, year);
    }

    /**
     * Возвращает список фильмов режиссера
     * отсортированных по количеству лайков или году выпуска.
     *
     * @param sortBy sortBy=[year,likes,none]
     * @return список фильмов режиссера
     */
    @RequestMapping("/director/{directorId}")
    @GetMapping
    public List<Film> getDirectorFilms(@PathVariable int directorId, @RequestParam(name = "sortBy", defaultValue = "none") String sortBy) {
        return filmService.getDirectorFilms(directorId, sortBy.toUpperCase());
    }
}