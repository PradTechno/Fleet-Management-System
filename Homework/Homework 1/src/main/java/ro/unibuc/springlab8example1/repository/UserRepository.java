package ro.unibuc.springlab8example1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserDetails;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User save(User user) {
        String saveUserSql = "INSERT INTO users (username, full_name, user_type, account_created) VALUES (?,?,?,?)";
        jdbcTemplate.update(saveUserSql, user.getUsername(), user.getFullName(), user.getUserType().name(), LocalDateTime.now());

        User savedUser = getUserWith(user.getUsername());
        UserDetails userDetails = user.getUserDetails();

        if (userDetails != null) {
            String saveUserDetailsSql = "INSERT INTO user_details (cnp, age, other_information) VALUES (?, ?, ?)";
            jdbcTemplate.update(saveUserDetailsSql, userDetails.getCnp(), userDetails.getAge(), userDetails.getOtherInformation());

            UserDetails savedUserDetails = getUserDetailsWith(userDetails.getCnp());
            savedUser.setUserDetails(savedUserDetails);

            String saveUsersUserDetails = "INSERT INTO users_user_details (users, user_details) VALUES (?, ?)";
            jdbcTemplate.update(saveUsersUserDetails, savedUser.getId(), savedUserDetails.getId());
        }

        return savedUser;
    }

    public User get(String username) {
        // TODO : homework: use JOIN to fetch all details about the user
        String selectSql = "SELECT * FROM users inner join users_user_details on users_user_details.users = users.id inner join user_details on users_user_details.user_details = user_details.id where users.username = ?";
        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("users.id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .userDetails(new UserDetails(resultSet.getLong("user_details.id"), resultSet.getString("user_details.cnp"), resultSet.getInt("user_details.age"), resultSet.getString("user_details.other_information")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, username);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        throw new UserNotFoundException("User not found");
    }

    public User put(User user){
        UserDetails userDetails = user.getUserDetails();
        String updateUserSql = "UPDATE users inner join users_user_details on users_user_details.users = users.id inner join user_details on users_user_details.user_details = user_details.id set users.username = ?, users.full_name = ?, user_details.cnp = ?, user_details.age = ?, user_details.other_information = ? where users.id = ?";
        jdbcTemplate.update(updateUserSql, user.getUsername(), user.getFullName(), userDetails.getCnp(), userDetails.getAge(), userDetails.getOtherInformation(), user.getId());
        return get(user.getUsername());
    }

    public Boolean delete(String username){
        User userToBeDeleted = getUserWith(username);

        String selectSql = "SELECT * from users_user_details WHERE users = ?";
        RowMapper<Long> rowMapper = (resultSet, rowNo) -> resultSet.getLong("user_details");

        List<Long> userDetailsIds = jdbcTemplate.query(selectSql, rowMapper, userToBeDeleted.getId());

        String deleteUsersUserDetailsSql = "DELETE FROM users_user_details where users = ?";
        jdbcTemplate.update(deleteUsersUserDetailsSql, userToBeDeleted.getId());

        String inSql = String.join(",", Collections.nCopies(userDetailsIds.size(), "?"));
        String deleteUserDetailsSql = String.format("DELETE FROM user_details where id in (%s)", inSql);
        jdbcTemplate.update(deleteUserDetailsSql, userDetailsIds.toArray());

        String deleteUserSql = "DELETE FROM users where id = ?";
        jdbcTemplate.update(deleteUserSql, userToBeDeleted.getId());
        return true;
    }

    private User getUserWith(String username) {
        String selectSql = "SELECT * from users WHERE users.username = ?";
        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, username);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        throw new UserNotFoundException("User not found");
    }

    private UserDetails getUserDetailsWith(String cnp) {
        String selectSql = "SELECT * from user_details WHERE user_details.cnp = ?";
        RowMapper<UserDetails> rowMapper = (resultSet, rowNo) -> UserDetails.builder()
                .id(resultSet.getLong("id"))
                .cnp(resultSet.getString("cnp"))
                .age(resultSet.getInt("age"))
                .otherInformation(resultSet.getString("other_information"))
                .build();

        List<UserDetails> details = jdbcTemplate.query(selectSql, rowMapper, cnp);

        if (!details.isEmpty()) {
            return details.get(0);
        }

        throw new UserNotFoundException("User details not found");
    }

    public List<User> getUsersByType(String type){
        String selectSql = "SELECT * from users WHERE user_type = ?";
        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("users.id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, type);

        if (!users.isEmpty()) {
            return users;
        }

        throw new UserNotFoundException("User not found");
    }
}
