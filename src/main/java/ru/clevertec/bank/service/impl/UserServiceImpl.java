package ru.clevertec.bank.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.bank.dao.UserDao;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.entity.User;
import ru.clevertec.bank.mapper.UserMapper;
import ru.clevertec.bank.service.UserService;
import ru.clevertec.bank.validator.UserDtoValidator;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserDao userDao;
    private final UserDtoValidator validator;


    /**
     * Создает нового пользователя на основе данных из объекта UserDto.
     *
     * @param userDto данные нового пользователя
     * @return id созданного пользователя, завернутый в Optional, или пустое значение Optional, если
     * данные пользователя некорректны.
     */
    @Override
    public Optional<UUID> createUser(UserDto userDto) {
        try {
            validator.validateUserDto(userDto);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
        User user = mapper.toUser(userDto);
        UUID id = userDao.createUser(user);
        return Optional.ofNullable(id);
    }

    /**
     * Возвращает объект пользователя с указанным идентификатором.
     *
     * @param id идентификатор пользователя
     * @return объект UserDto для пользователя с указанным идентификатором
     * @throws NoSuchElementException если пользователя с указанным идентификатором не найден
     */
    @Override
    public UserDto getUserById(UUID id) {
        return mapper.toUserDto(userDao.getUserById(id)
            .orElseThrow(() -> new NoSuchElementException("No user with id " + id)));
    }

    /**
     * Обновляет данные пользователя на основе объекта UserDto.
     *
     * @param updatedUser обновленные данные пользователя
     */
    @Override
    public void updateUser(UserDto updatedUser) {
        try {
            validator.validateUserDto(updatedUser);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return;
        }
        userDao.updateUser(mapper.toUser(updatedUser));
    }

    /**
     * Удаляет пользователя с указанным идентификатором.
     *
     * @param id идентификатор пользователя
     */
    @Override
    public void deleteUser(UUID id) {
        userDao.deleteUser(id);
    }

    /**
     * Возвращает список пользователей.
     *
     * @param pageSize   количество пользователей на странице
     * @param pageNumber номер страницы
     * @return список пользователей
     */
    @Override
    public List<UserDto> findAll(int pageSize, int pageNumber) {
        pageSize = (pageSize <= 0) ? 5 : pageSize;
        return mapper.toListUserDto(userDao.findAll(pageSize, pageNumber));
    }

}
