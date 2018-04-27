package com.sd.his.service;

import com.sd.his.model.Permission;
import com.sd.his.model.Role;
import com.sd.his.model.User;
import com.sd.his.repositiories.PermissionRepository;
import com.sd.his.repositiories.RoleRepository;
import com.sd.his.repositiories.UserRepository;
import com.sd.his.wrapper.AdminWrapper;
import com.sd.his.wrapper.PermissionWrapper;
import com.sd.his.wrapper.UserWrapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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
                user.isActive(), true, true, true, getAuthorities(user));

    }

    private List<SimpleGrantedAuthority> getRoles(List<Role> authList) {
        return authList.stream()
                .map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {

        List<String> perm = getPrivileges(user);
        return getGrantedAuthorities(perm);
    }

    private List<String> getPrivileges(User user) {
        List<String> privileges = user.getPermissions().stream().map(object -> Objects.toString(object.getPermission().getName(), null)).
                collect(Collectors.toList());
        List<Permission> rolePermissions = permissionRepo.findAllUserAndUserRolePermissions(user.getId());
        for (Permission per : rolePermissions) {
            privileges.add(per.getName());
        }
        Set<String> identicalPermissions = new HashSet<>(privileges);
        return identicalPermissions.stream().collect(Collectors.toList());
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

    public UserWrapper buildUserWrapper(User dbUser) {
        UserWrapper user = new UserWrapper(dbUser);
        List<PermissionWrapper> permissionWrappers = new ArrayList<>();
        List<Permission> userPermissions = getIdenticalUserPermissions(dbUser);

        for (Permission per : userPermissions) {
            PermissionWrapper permissionWrapper = new PermissionWrapper(per);
            permissionWrappers.add(permissionWrapper);
        }
        user.setPermissions(permissionWrappers);

        return user;
    }

    private List<Permission> getIdenticalUserPermissions(User user) {
        List<Permission> userPermissions = permissionRepo.findAllUserAndUserRolePermissions(user.getId());
        Set<Permission> identicalPermissions = new HashSet<>(userPermissions);
        userPermissions = new ArrayList<>(identicalPermissions);

        return userPermissions;
    }

}
