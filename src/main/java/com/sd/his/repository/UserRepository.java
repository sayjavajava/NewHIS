package com.sd.his.repository;


import com.sd.his.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {


    User findById(Long id);

    User findByUsernameAndActiveTrue(String name);

}
