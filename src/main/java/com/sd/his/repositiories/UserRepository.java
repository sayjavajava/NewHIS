package com.sd.his.repositiories;


import com.sd.his.model.Role;
import com.sd.his.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> ,PagingAndSortingRepository<User,Long>{

    User findByUsernameOrEmail(String username, String email);

    User findById(long id);

    User findAllById(long id);

    User findByUsernameOrEmailAndActiveTrueAndDeletedFalse(String username, String email);

    User findByUsername(String name);

    List<User> findAllByActiveTrueAndDeletedFalseOrderByUsernameAsc(Pageable pageable);


    List<User> findAllByUsernameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrRoles_role_nameIgnoreCaseContaining(String name,
                                                                          String email,String role, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.branches ub JOIN ub.branch WHERE u.active = TRUE AND u.deleted = FALSE ORDER BY u.username ")
    List<User> findAllUsers(Pageable pageable);

    List<User> findAllByRoles_role_name(String role);

}
