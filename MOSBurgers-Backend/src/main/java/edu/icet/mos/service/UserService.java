package edu.icet.mos.service;

import edu.icet.mos.dto.User;

public interface UserService {
    void signUp(User user);
    boolean logIn(String email, String password);
}
