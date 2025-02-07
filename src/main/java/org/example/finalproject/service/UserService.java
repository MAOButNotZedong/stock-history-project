package org.example.finalproject.service.user;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.dao.UserDao;
import org.example.finalproject.dto.user.UserRegistrationDto;
import org.example.finalproject.exception.UserWithEmailAlreadyExistsException;
import org.example.finalproject.model.UserPrincipal;
import org.example.finalproject.model.entity.User;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCrypt;

    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new UserPrincipal(user);
    }

    public void saveUser(UserRegistrationDto userDto) {
        if (getUserByEmail(userDto.getEmail()).isPresent()) {
            throw new UserWithEmailAlreadyExistsException(userDto.getEmail());
        }

        try {
            User newUser = User.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .password(bCrypt.encode(userDto.getPassword()))
                    .build();
            userDao.save(newUser);

        } catch (OptimisticLockingFailureException e) {
            if (getUserByEmail(userDto.getEmail()).isPresent()) {
                throw new UserWithEmailAlreadyExistsException(userDto.getEmail());
            } else throw e;
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = ((UserPrincipal) authentication.getPrincipal()).getUsername();
        return userDao.getUserByEmail(userEmail).orElseThrow(() -> new UserWithEmailAlreadyExistsException(userEmail));
    }
}


