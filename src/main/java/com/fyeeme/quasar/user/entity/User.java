package com.fyeeme.quasar.user.entity;

import com.fyeeme.quasar.base.entity.Auditable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Schema(name = "User", description = "User Object")
public class User extends Auditable<Long> implements UserDetails {

    private static final long serialVersionUID = 1317843457585810749L;
    @Column(unique = true)

    @Schema(name = "username", description = "用户名")
    private String username;
    @Schema(title = "密码",description = "密码必填", required = true, maxLength = 80)
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String avatar;
    private Boolean locked;
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var privileges = roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .map(privilege -> "ROLE_" + privilege.getCode())
                .collect(Collectors.toSet());
        return privileges.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
