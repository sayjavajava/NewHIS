package com.sd.his.repositiories;

import com.sd.his.model.Permission;
import com.sd.his.model.Role;
import com.sd.his.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findById(Integer id);

    Role findByName(String name);
    List<Role> findAllByActiveTrueAndDeletedFalse();


}
