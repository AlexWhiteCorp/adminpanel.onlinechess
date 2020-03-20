package com.alexcorp.oc.adminpanel.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"nickname"})
@Entity
@Table(name = "USR")
@JsonIgnoreProperties(value = {"secureCode"})
public class User implements UserDetails, Serializable  {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(unique = true)
    private String nickname;

    @JsonIgnore
    private String secureCode;

    @Column(length = 9)
    @Enumerated(EnumType.STRING)
    private Active active;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Date lastLogin;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Account account;

    public User(String nickname, String secureCode, Role role, Active active) {
        this.nickname = nickname;
        this.secureCode = secureCode;
        this.active = active;
        this.role = role;
        this.account = new Account(this);
    }

    public User(long id, String nickname, String secureCode, Active active, Role role, Account account) {
        this.id = id;
        this.nickname = nickname;
        this.secureCode = secureCode;
        this.active = active;
        this.role = role;
        this.account = account;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return secureCode;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return nickname;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }

    public enum Active{
        ACTIVE, NONACTIVE, BANNED;
    }

    public enum Role implements GrantedAuthority{

        PLAYER ("PLAYER"),
        ADMIN ("ADMIN"),
        BOT ("BOT"),
        BOT_MANAGER ("BOT_MAN");

        private final String name;

        private Role(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }

        @Override
        public String getAuthority() {
            return name;
        }
    }
}
