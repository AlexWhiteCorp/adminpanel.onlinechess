package com.alexcorp.oc.adminpanel.repositories;

import com.alexcorp.oc.adminpanel.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByNickname(String nickname);

    public List<User> findAll();
    public List<User> findAllByNicknameContains(String nickname);
    public List<User> findAllByActive(User.Active active);
    public List<User> findAllByRole(User.Role role);
}
