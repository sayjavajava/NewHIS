package com.sd.his.repositiories;

import com.sd.his.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findById(Integer id);

    List<Permission> findAllByActiveTrueAndDeletedFalse();

    @Query("SELECT DISTINCT p FROM Permission p INNER JOIN p.roles rp INNER JOIN rp.role r INNER JOIN r.users ur INNER JOIN ur.user u WHERE u.id=1")
    List<Permission> findAllUserRolePermissions();

}
