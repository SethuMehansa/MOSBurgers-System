package edu.icet.mos.controller;

import edu.icet.mos.dto.User;
import edu.icet.mos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Void> signUp(@RequestBody User user) {
    try {
      userService.signUp(user);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<Boolean> logIn(@RequestBody User user) {
    try {
      boolean isAuthenticated = userService.logIn(user.getEmail(), user.getPassword());
      return new ResponseEntity<>(isAuthenticated, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }
  }
}
