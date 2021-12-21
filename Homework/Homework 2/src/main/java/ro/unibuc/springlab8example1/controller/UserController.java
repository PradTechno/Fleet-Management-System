package ro.unibuc.springlab8example1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/student")
    public ResponseEntity<UserDto> createStudent(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto, UserType.STUDENT));
    }

    @PostMapping("/professor")
    public ResponseEntity<UserDto> createProfessor(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto, UserType.PROFESSOR));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> get(@PathVariable String username) {
        return ResponseEntity
                .ok()
                .body(userService.getOne(username));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<UserDto>> getFilteredUsers(@RequestParam String name,
                                                          @RequestParam String userType) {
        return ResponseEntity
                .ok()
                .body(userService.getFilteredUsers(name, UserType.valueOf(userType)));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username){
        userService.delete(username);
        return ResponseEntity
                .ok()
                .build();
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserDto> put(@PathVariable String username,@RequestBody UserDto userDto){
        return ResponseEntity
                .ok()
                .body(userService.update(username, userDto));
    }

    @GetMapping("/type/{userType}")
    public ResponseEntity<List<UserDto>> getByType(@PathVariable String userType){
        return ResponseEntity
                .ok()
                .body(userService.getUsersByUserType(UserType.valueOf(userType)));
    }
    // TODO: homework: endpoints for updating a user, deleting one, get all users filtered by tupe
}
