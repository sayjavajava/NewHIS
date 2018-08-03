package com.sd.his.repository;


import com.sd.his.model.Permission;
import com.sd.his.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Transactional(rollbackOn = Throwable.class)
    void deleteAllByRole_Id(Long id);

    //@Query("SELECT DISTINCT p FROM Permission p INNER JOIN p.rolePermissions rp INNER JOIN rp.role r INNER JOIN r.userRoles ur INNER JOIN ur.user u WHERE u.id=:userId")

    @Query("SELECT rp FROM RolePermission rp INNER JOIN rp.permission p INNER JOIN rp.role r INNER JOIN r.userRoles ur INNER JOIN ur.user u WHERE u.id=:userId")
    List<RolePermission> findUserRolePermissionByUserId(@Param("userId") Long userId);

}
