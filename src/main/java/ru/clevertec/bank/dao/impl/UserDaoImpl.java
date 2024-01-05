package ru.clevertec.bank.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.bank.dao.UserDao;
import ru.clevertec.bank.dao.UtilDB;
import ru.clevertec.bank.entity.User;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UUID createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UtilDB.CREATE_USER,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getMail());
            ps.setInt(4, user.getAge());
            return ps;
        }, keyHolder);

        return (UUID) keyHolder.getKeys().get("id");
    }

    public Optional<User> getUserById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            UtilDB.GET_USER_BY_ID,
            new Object[]{id},
            new UserRowMapper()));
    }

    public void updateUser(User user) {
        jdbcTemplate.update(UtilDB.UPDATE_USER,
            user.getFirstName(),
            user.getLastName(),
            user.getMail(),
            user.getAge(),
            user.getId());
    }

    @Override
    public List<User> findAll(int pageSize, int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return jdbcTemplate.query(
            UtilDB.FIND_ALL,
            new Object[]{pageSize, offset},
            new UserRowMapper()
        );
    }

    public void deleteUser(UUID id) {
        jdbcTemplate.update(UtilDB.DELETE_USER, id);
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(UUID.fromString(resultSet.getString("id")));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setMail(resultSet.getString("mail"));
            user.setAge(resultSet.getInt("age"));
            return user;
        }

    }

}
