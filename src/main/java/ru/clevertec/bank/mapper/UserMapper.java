package ru.clevertec.bank.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.entity.User;

@Mapper
public interface UserMapper {

    /**
     * Маппит User в UserDTO
     *
     * @param user - DTO для маппинга
     * @return новый продукт
     */
    UserDto toUserDto(User user);

    /**
     * Маппит UserDTO в User
     *
     * @param userDto - DTO для маппинга
     * @return новый продукт
     */
    User toUser(UserDto userDto);

    /**
     * Маппит список пользователей в список DTO
     *
     * @param users - список пользователей
     * @return список DTO
     */
    List<UserDto> toListUserDto(List<User> users);

}
