package com.sd.his.repositiories;


import com.sd.his.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Transactional(rollbackOn = Throwable.class)
    void deleteAllByRole_Id(Long id);
}
