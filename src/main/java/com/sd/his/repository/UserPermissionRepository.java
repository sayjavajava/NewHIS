package com.sd.his.repository;

import com.sd.his.model.Permission;
import com.sd.his.model.User;
import com.sd.his.model.UserPermission;
import com.sd.his.wrapper.PermissionWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    List<UserPermission> findAllByUser_id (Long userId);
    void deleteAllByUser_Id(Long id);
}
