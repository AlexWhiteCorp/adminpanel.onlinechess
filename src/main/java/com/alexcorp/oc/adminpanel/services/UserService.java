package com.alexcorp.oc.adminpanel.services;

import com.alexcorp.oc.adminpanel.domains.User;
import com.alexcorp.oc.adminpanel.repositories.UserRepository;
import com.alexcorp.oc.adminpanel.repositories.filters.UsersFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page findAllByFilters(String json, Pageable pageable){
        if(json.isEmpty()) return userRepository.findAll(pageable);

        return userRepository.findAll(new UsersFilter(json), pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return userRepository.findByNickname(nickname);
    }

    public Optional<User> loadUserById(long id) {
        return userRepository.findById(id);
    }

    public User registerNewUser(String nickname, String secureCode, User.Role role, User.Active active){
        User user = new User(nickname, passwordEncoder.encode(secureCode), role, active);
        return userRepository.save(user);
    }

    public void updateInfo(User newUserInfo){
        User oldUser = userRepository.findById(newUserInfo.getId()).get();

        oldUser.setNickname(newUserInfo.getNickname());
        if(!newUserInfo.getSecureCode().contains("•")) oldUser.setSecureCode(passwordEncoder.encode(newUserInfo.getSecureCode()));
        oldUser.setActive(newUserInfo.getActive());
        oldUser.setRole(newUserInfo.getRole());

        userRepository.save(oldUser);

        /*Authentication authentication = new UsernamePasswordAuthenticationToken(oldUser.getUsername(), oldUser.getPassword(), oldUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);*/
    }
}
