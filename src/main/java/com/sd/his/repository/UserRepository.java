package com.sd.his.repository;


import com.sd.his.model.Doctor;
import com.sd.his.model.User;
import com.sd.his.wrapper.response.StaffWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    User findById(Long id);
    List<User> findAllByActiveTrue(Pageable pageable);
    User findByUsernameAndActiveTrue(String name);
    List<User> findAllByUserRoles_role_name(String role);
    List<User> findAllByIdIn(List<Long> ids);
    int countAllByActiveTrue();
    User findByUsername(String userName);
}
