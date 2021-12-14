package ro.unibuc.springlab8example1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.exception.TypeNotFundException;
import ro.unibuc.springlab8example1.mapper.UserMapper;
import ro.unibuc.springlab8example1.repository.UserRepository;

import java.util.Arrays;
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
        User savedUser = userRepository.save(user);

        return userMapper.mapToDto(savedUser);
    }

    public UserDto getOne(String username) {
        return userMapper.mapToDto(userRepository.get(username));
    }

    public UserDto update(UserDto userDto, Long userId){
        User user = userMapper.mapToEntity(userDto);
        User updatedUser = userRepository.put(user);

        return userMapper.mapToDto(updatedUser);
    }

    public Boolean delete(String username){
        return userRepository.delete(username);
    }

    public List<UserDto> getByType(String type){
        Boolean isValid = Arrays.stream(UserType.class.getEnumConstants()).anyMatch(e -> e.name().equals(type));
        if(isValid == false){
            throw new TypeNotFundException("Type not found");
        }
        return userRepository.getUsersByType(type).stream().map(u -> userMapper.mapToDto(u)).collect(Collectors.toList());
    }
}
