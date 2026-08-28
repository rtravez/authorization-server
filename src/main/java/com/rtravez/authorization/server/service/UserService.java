package com.rtravez.authorization.server.service;

import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rtravez.authorization.server.entity.UserEntity;
import com.rtravez.authorization.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = findUserByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Username: " + username + " no existe en el sistema!"));

        var authorities = user.getRoleUsers().stream()
                .map(roleUser -> new SimpleGrantedAuthority(roleUser.getRole().getName())).toList();
        if (authorities.isEmpty()) {
            throw new UsernameNotFoundException("El usuario " + username + " no tiene roles asignados!");
        }
        return new User(user.getUsername(), user.getPassword(), user.getStatus(), true, true, true,
                authorities);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsernameAndStatusTrue(username);
    }
}
