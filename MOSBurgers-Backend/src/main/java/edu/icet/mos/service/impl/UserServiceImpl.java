package edu.icet.mos.service.impl;

import edu.icet.mos.dto.User;
import edu.icet.mos.entity.UserEntity;
import edu.icet.mos.repository.UserRepository;
import edu.icet.mos.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;

    @Override
    public void signUp(User user) {
        repository.save(mapper.map(user, UserEntity.class));
    }

    @Override
    public boolean logIn(String email, String password) {
        List<UserEntity> byEmail=repository.findByEmail(email);
        for (UserEntity user:byEmail){
            if (user.getPassword().equals(password)){
                return true;
            }
        }
    return false;
    }
}
