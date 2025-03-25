package edu.icet.mos.controller;

import edu.icet.mos.dto.User;
import edu.icet.mos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
  private final UserService userService;

  @PostMapping("/signup")
    public void signUp(@RequestBody User user){
      userService.signUp(user);
  }

  @PostMapping("/login")
    public boolean logIn(@RequestBody User user){
      return userService.logIn(user.getEmail(),user.getPassword());
  }
}
