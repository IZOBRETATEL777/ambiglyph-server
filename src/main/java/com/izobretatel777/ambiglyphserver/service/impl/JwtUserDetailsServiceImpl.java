package com.izobretatel777.ambiglyphserver.service.impl;

import com.izobretatel777.ambiglyphserver.dao.repo.UserRepo;
import com.izobretatel777.ambiglyphserver.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.izobretatel777.ambiglyphserver.dao.entity.User user = userRepo.findByLogin(username);
        if (user != null){
            return new User(user.getLogin(),user.getPassword(),new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found with username " + username);
        }
    }
}