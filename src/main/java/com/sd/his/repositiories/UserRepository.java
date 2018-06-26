package com.sd.his.repositiories;


import com.sd.his.model.User;
import com.sd.his.request.PatientRequest;
import com.sd.his.wrapper.PatientWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    User findByUsernameOrEmail(String username, String email);

    User findById(long id);

    User findAllById(long id);

    User findByUsernameOrEmailAndActiveTrueAndDeletedFalse(String username, String email);

    User findByUsername(String name);

    List<User> findAllByActiveTrueAndDeletedFalseOrderByUsernameAsc(Pageable pageable);


    List<User> findAllByUsernameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrRoles_role_nameIgnoreCaseContaining(String name,
                                                                                                                     String email, String role, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.branches ub JOIN ub.branch WHERE u.active = TRUE AND u.deleted = FALSE ORDER BY u.username ")
    List<User> findAllUsers(Pageable pageable);

    List<User> findAllByRoles_role_name(String role);

    int countAllByActiveTrueAndDeletedFalse();

    List<User> findAllByIdIn(List<Long> ids);

    @Query("SELECT NEW  com.sd.his.wrapper.PatientWrapper(u,u.profile,u.insurance) from User u where u.userType=:uType AND u.deleted=FALSE")
    List<PatientWrapper> findAllByDeletedFalse(Pageable pageable, @Param("uType") String uType);

    @Query("SELECT NEW  com.sd.his.wrapper.PatientWrapper(u,u.profile,u.insurance) from User u where u.userType=:uType AND u.deleted=FALSE")
    List<PatientWrapper> findAllByDeletedFalse(@Param("uType") String uType);

    List<User> findAllByUsernameAndEmailAndDeletedFalse(String userName, String email);

    List<User> findAllByIdNotAndUsernameAndEmailAndDeletedFalse(long id, String userName, String email);

    @Query("SELECT NEW  com.sd.his.request.PatientRequest(u,u.profile,u.insurance) from User u where u.id=:id")
    PatientRequest findUserById(@Param("id") long id);

    @Query("SELECT NEW  com.sd.his.wrapper.PatientWrapper(u,u.profile,u.insurance) from User u where u.userType=:uType AND u.deleted=FALSE AND u.username like CONCAT('%',:userName,'%') ")
    List<PatientWrapper> findAllByDeletedFalse(Pageable pageable, @Param("uType") String uType, @Param("userName") String userName);

    @Query("SELECT NEW  com.sd.his.wrapper.PatientWrapper(u,u.profile,u.insurance) from User u where u.userType=:uType AND u.deleted=FALSE AND u.username like CONCAT('%',:userName,'%') ")
    List<PatientWrapper> findAllByDeletedFalse(@Param("uType") String uType, @Param("userName") String userName);

}
