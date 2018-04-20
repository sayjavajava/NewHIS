package com.sd.his.service;

import com.sd.his.model.Permission;
import com.sd.his.model.Role;
import com.sd.his.model.User;
import com.sd.his.repositiories.PermissionRepository;
import com.sd.his.repositiories.RoleRepository;
import com.sd.his.repositiories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "userService")
@Transactional
public class HISUserService implements UserDetailsService {

    private UserRepository userRepository;
    private PermissionRepository permissionRepo;
    private RoleRepository roleRepo;

    HISUserService(UserRepository userRepository, PermissionRepository permissionRepo, RoleRepository roleRepo) {
        this.userRepository = userRepository;
        this.permissionRepo = permissionRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isActive(), true, true, true, getAuthorities(user.getRole()));

    }

    private List<SimpleGrantedAuthority> getRoles(List<Role> authlist) {
        return authlist.stream()
                .map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            List<Role> roles) {

        List<String> perm = getPrivileges(roles);
        return getGrantedAuthorities(perm);
    }

    private List<String> getPrivileges(List<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            privileges.add(item.getName());

        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public User findByUsernameOrEmailAndActiveTrueAndDeletedFalse(String userName, String email) {
        return userRepository.findByUsernameOrEmailAndActiveTrueAndDeletedFalse(userName, email);
    }

    public User findByUserName(String name) {
        return userRepository.findByUsername(name);
    }

}
