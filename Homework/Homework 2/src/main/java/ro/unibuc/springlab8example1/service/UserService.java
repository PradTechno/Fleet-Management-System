package ro.unibuc.springlab8example1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserDetails;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;
import ro.unibuc.springlab8example1.mapper.UserMapper;
import ro.unibuc.springlab8example1.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto create(UserDto userDto, UserType type) {
        User user = userMapper.mapToEntity(userDto);
        user.setUserType(type);
        user.setAccountCreated(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return userMapper.mapToDto(savedUser);
    }

    @Transactional
    public void delete(String username){
        userRepository.deleteByUsername(username);
    }

    public UserDto update(String username, UserDto userDto){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setUserDetails(new UserDetails(user.getUserDetails().getId(), userDto.getCnp(), userDto.getAge(), userDto.getOtherInformation()));
        userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    public UserDto getOne(String username) {
        return userMapper.mapToDto(userRepository.findByUsername(username));
    }

    public List<UserDto> getFilteredUsers(String name, UserType userType) {
        List<User> users = userRepository.filter(userType, name);

        return users.stream().map(u -> userMapper.mapToDto(u)).collect(Collectors.toList());
    }

    public List<UserDto> getUsersByUserType(UserType userType){
        List<User> users = userRepository.findAllByUserType(userType);
        return users.stream().map(u -> userMapper.mapToDto(u)).collect(Collectors.toList());
    }
}
